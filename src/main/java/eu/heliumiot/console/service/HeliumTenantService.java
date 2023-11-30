/*
 * Copyright (c) - Paul Pinault (aka disk91) - 2020.
 *
 *    Permission is hereby granted, free of charge, to any person obtaining a copy of this software
 *    and associated documentation files (the "Software"), to deal in the Software without restriction,
 *    including without limitation the rights to use, copy, modify, merge, publish, distribute,
 *    sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is
 *    furnished to do so, subject to the following conditions:
 *
 *    The above copyright notice and this permission notice shall be included in all copies or
 *    substantial portions of the Software.
 *
 *    THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 *    IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS
 *    FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS
 *    OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY,
 *    WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR
 *    IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */
package eu.heliumiot.console.service;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.google.protobuf.InvalidProtocolBufferException;
import eu.heliumiot.console.ConsoleConfig;
import eu.heliumiot.console.api.interfaces.*;
import eu.heliumiot.console.chirpstack.ChirpstackApiAccess;
import eu.heliumiot.console.jpa.db.*;
import eu.heliumiot.console.jpa.db.UserTenant;
import eu.heliumiot.console.jpa.repository.*;
import eu.heliumiot.console.mqtt.MqttSender;
import eu.heliumiot.console.mqtt.api.HeliumDeviceStatItf;
import eu.heliumiot.console.mqtt.api.HeliumTenantActDeactItf;
import eu.heliumiot.console.tools.EncryptionHelper;
import fr.ingeniousthings.tools.ITNotFoundException;
import fr.ingeniousthings.tools.ITParseException;
import fr.ingeniousthings.tools.ITRightException;
import fr.ingeniousthings.tools.Now;
import io.chirpstack.restapi.*;
import io.chirpstack.restapi.Tenant;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import com.helium.grpc.route_v1;

import javax.annotation.PostConstruct;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

@Service
public class HeliumTenantService {
    private final Logger log = LoggerFactory.getLogger(this.getClass());

    public final static String HELIUM_TENANT_SETUP_DEFAULT = "default";

    @Autowired
    protected ConsoleConfig consoleConfig;

    @Autowired
    protected HeliumTenantSetupService heliumTenantSetupService;

    protected int runningJobs;
    protected boolean serviceEnable; // false to stop the services

    protected ObjectMapper mapper;

    @PostConstruct
    private void initHeliumTenantService() {
        log.info("initHeliumTenantService initialization");

        mapper = new ObjectMapper();
        mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        mapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
        mapper.configure(SerializationFeature.FAIL_ON_SELF_REFERENCES, false);

        runningJobs=0;
        serviceEnable=true;
    }

    // request to stop the service properly
    public void stopService() {
        this.serviceEnable = false;
    }

    // return true when the service has stopped all the running jobs
    public boolean hasStopped() {
        return (this.serviceEnable == false && this.runningJobs == 0);
    }

    public void stopHeliumTenantServiceCache() {
        log.info("Stopping HeliumTenantService");
    }


    @Autowired
    private HeliumTenantRepository heliumTenantRepository;


    /**
     * Get a Helium Tenant and create one if not yet existing
     * @param tenantUUID
     * @return
     */
    protected HeliumTenant getHeliumTenant(String tenantUUID, boolean autoCreate){
        // @TODO - on peut optimiser en cachant le resultat tant qu'il n'a pas changé
        //         de cette facon on ne fait pas un appel a chaque fois
        HeliumTenant t = heliumTenantRepository.findOneHeliumTenantByTenantUUID(tenantUUID);
        if ( t == null && autoCreate ) {
            // create it
            HeliumTenantSetup ts = heliumTenantSetupService.getHeliumTenantSetup(tenantUUID,true);
            return this.createNewHeliumTenant(tenantUUID,ts);
        } else return t;
    }

    protected void flushHeliumTenant(HeliumTenant t) {
        log.debug("#> new balance "+t.getDcBalance()+" for "+t.getTenantUUID());
        // Manage alarm
        boolean riseAlarm = false;

        // do not alarm the user with a small amount of DCs like free registration
        if ( t.getAlarmed() == 0 && consoleConfig.getHeliumTenantDcAlarm() > 0 && t.getDcBalance() > consoleConfig.getHeliumTenantDcAlarm() ) {
            if ( consoleConfig.getHeliumTenantDcWarn() > 0 && t.getDcBalance() < consoleConfig.getHeliumTenantDcWarn() ) {
                // do not set email immediately on init, we will send the nex level only
                t.setAlarmed(2);
            } else {
                t.setAlarmed(1);
            }
        }

        // check the warn level first
        if ( t.getAlarmed() == 1 && consoleConfig.getHeliumTenantDcWarn() > 0 && t.getDcBalance() < consoleConfig.getHeliumTenantDcWarn() ) {
            t.setAlarmed(2);
            riseAlarm = true;
        }
        // check next level
        if ( t.getAlarmed() == 2 && consoleConfig.getHeliumTenantDcAlarm() > 0 && t.getDcBalance() < consoleConfig.getHeliumTenantDcAlarm() ) {
            t.setAlarmed(3);
            riseAlarm = true;
        }
        if ( consoleConfig.getHeliumTenantDcWarn() > 0 && t.getDcBalance() > consoleConfig.getHeliumTenantDcWarn() ) {
            t.setAlarmed(1); // rearm alarm
            riseAlarm = false;
        }
        heliumTenantRepository.save(t);

        if ( riseAlarm ) {
            try {
                mqttSender.publishMessage(
                    "helium/tenant/alarm/" + t.getTenantUUID(),
                    mapper.writeValueAsString(t),
                    2
                );
            } catch (Exception x) {
                this.prometeusService.addMqttConnectionLoss();
                log.error("Something went wrong with publishing tenant alarm on MQTT");
                log.error(x.getMessage());
            }
        }
    }

    public HeliumTenant createNewHeliumTenant(String tenantUUID, HeliumTenantSetup ts) {
        try {
            if (tenantUUID.compareToIgnoreCase("create") == 0) {
                log.error("#### FOUND create ");
                throw new Exception();
            }
        } catch (Exception x) {
            x.printStackTrace();
        }
        HeliumTenant t = new HeliumTenant();
        t.setTenantUUID(tenantUUID);
        t.setDcBalance(ts.getFreeTenantDc());
        t.setState(HeliumTenant.TenantState.NORMAL);
        t.setMaxCopy(ts.getMaxCopy());
        return heliumTenantRepository.save(t);
    }

    public eu.heliumiot.console.jpa.db.Tenant getTenant(UUID tenantId) {
        eu.heliumiot.console.jpa.db.Tenant t = tenantRepository.findOneTenantById(tenantId);
        return t;
    }

    public boolean clearTenant(String tenantUUID) {
        synchronized (this) {
            HeliumTenant t = this.getHeliumTenant(tenantUUID,false);
            if ( t != null ) {
                log.warn("Clear a tenant ("+t.getTenantUUID()+") balance was "+t.getDcBalance());
                t.setDcBalance(0);
                this.flushHeliumTenant(t);
                return true;
            } else return false;
        }
    }

    // ===============================================
    // Async route management
    // Here to not have cycle integration with tenantSetupService
    // Bad ..
    // ===============================================

    @Autowired
    private HeliumTenantSetupRepository heliumTenantSetupRepository;

    @Scheduled(fixedRateString = "${helium.tenant.routeregistration.scanPeriod}", initialDelay = 15_000) // default 5s
    protected void asyncRouteRegistration() {
        if ( ! this.serviceEnable ) return;
        this.runningJobs++;
        log.debug("asyncRouteRegistration - starting");
        long now = Now.NowUtcMs();
        try {
            // find all tenant manually created w/o Helium tenant
            List<eu.heliumiot.console.jpa.db.Tenant> missingTenants = tenantRepository.findMissingTenant();
            if ( missingTenants != null && missingTenants.size() > 0 ) {
                HeliumTenantSetup hts = heliumTenantSetupService.getHeliumTenantSetup(HELIUM_TENANT_SETUP_DEFAULT,false);
                if ( hts != null ) {
                    for (eu.heliumiot.console.jpa.db.Tenant t : missingTenants) {
                        // make sure we do not conflict with interactive way
                        if ( t.getCreated_at().getTime() < (now - 30_000) ) {
                            log.info("Sync Helium Tenant manually created (" + t.getName() + ") with default profile");
                            // create the HeliumTenantSetup
                            heliumTenantSetupService.createAndSave(hts, t.getId().toString());
                            // create HeliumTenant
                            this.createNewHeliumTenant(t.getId().toString(), hts);
                        }
                    }
                }
            }

            // find all the tenant w/o route created
            List<HeliumTenantSetup> tss = heliumTenantSetupRepository.findHeliumTenantSetupByRouteId(null);
            if ( tss != null ) {
                for ( HeliumTenantSetup ts : tss ) {
                    if ( ! ts.isTemplate() && ts.getRouteId() == null ) {
                        log.debug("asyncRouteRegistration - Found tenant "+ts.getTenantUUID()+" with route to create");
                        // find tenant to update the max_copy in case of
                        HeliumTenant ht = this.getHeliumTenant(ts.getTenantUUID(),true);
                        int mc = ts.getMaxCopy();
                        if ( ht.getMaxCopy() > 0 ) mc = ht.getMaxCopy();
                        String rId = novaService.immediateRouteCreation(
                                ts.getTenantUUID(),
                                mc
                        );
                        if ( rId != null ) {
                            ts.setRouteId(rId);
                            // save and clear cache for making sure information are up-to-date
                            heliumTenantSetupService.flushTenantSetup(ts);
                            log.debug("Route for tenant "+ts.getTenantUUID()+" created with id "+ts.getRouteId());
                        } else {
                            log.debug("Failed to create route");
                        }
                    }
                }
            }

        } catch (Exception x) {
            log.error("Failure in asyncRouteRegistration "+x.getMessage());
            x.printStackTrace();
        } finally {
            this.runningJobs--;
        }
    }


    // Background cleaning of the HeliumTenant and associated HeliumTenantSetup and routes
    @Scheduled(fixedRateString = "${helium.tenant.removedeleted.scanPeriod}", initialDelay = 15_000) // every 1 hour
    protected void asyncTenantDeletion() {
        if ( ! this.serviceEnable ) return;
        this.runningJobs++;
        log.debug("asyncTenantDeletion - starting");
        try {

            // find all the Helium tenant without associated Tenant
            List<HeliumTenant> hts = heliumTenantRepository.findDeletedTenant();
            if ( hts != null ) {
                for ( HeliumTenant ht : hts ) {
                    // Mark HeliumTenant deleted
                    ht.setState(HeliumTenant.TenantState.DELETED);
                    heliumTenantRepository.save(ht);

                    // Find the associated HeliumTenant Setup, clear route and HTS
                    HeliumTenantSetup tsetup = heliumTenantSetupService.getHeliumTenantSetup(ht.getTenantUUID(),false);
                    if ( tsetup != null ) heliumTenantSetupService.deleteTenantSetupTemplateUnsecured(tsetup);
                }
            }

        } catch (Exception x) {
            log.error("Failure in asyncTenantDeletion "+x.getMessage());
            x.printStackTrace();
        } finally {
            this.runningJobs--;
        }
    }



    // ======================================================
    // Async reporting
    // ======================================================

    @Autowired
    protected PrometeusService prometeusService;

    @Autowired
    protected MqttSender mqttSender;

    public void reportStatToMqtt(HeliumDeviceStatItf i) {

        try {
            mqttSender.publishMessage(
                    "helium/device/stats/" + i.getDeviceId(),
                    mapper.writeValueAsString(i),
                    2
            );
            this.prometeusService.addDelayedStatUpdate();
        } catch (Exception x) {
            this.prometeusService.addMqttConnectionLoss();
            log.error("Something went wrong with publishing on MQTT");
            log.error(x.getMessage());
        }

    }


    // ======================================================
    // Manage consumption
    // ======================================================
    @Autowired
    protected HeliumDeviceCacheService heliumDeviceCacheService;

    public void processUplink( String tenantUUID, String deviceUUID,  int payloadSize, int duplicates) {
        if ( tenantUUID == null ) {
            tenantUUID = heliumDeviceCacheService.getTenantId(deviceUUID);
            if (tenantUUID == null) {
                log.error("Impossible to find tenantUUID for (" + deviceUUID + ") - 1 ");
                return;
            }
        }
        long start = Now.NowUtcMs();
        HeliumDeviceStatItf i = new HeliumDeviceStatItf();
        i.setDeviceId(deviceUUID);
        i.setTenantId(tenantUUID);
        HeliumTenantSetup ts = heliumTenantSetupService.getHeliumTenantSetup(tenantUUID,false);
        if ( ts == null ) {
            HeliumTenant t = this.getHeliumTenant(tenantUUID,true);
            ts = heliumTenantSetupService.getHeliumTenantSetup(tenantUUID,false);
            if ( ts == null ) {
                log.error("Should not be  here ... (1)");
                return;
            }
        }
        synchronized (this) {
            HeliumTenant t = this.getHeliumTenant(tenantUUID,false);
            if (t != null) {
                payloadSize = (payloadSize / 24) + 1;
                int uplinkDc = payloadSize * ts.getDcPer24BMessage();
                int duplicateDc = payloadSize * duplicates * ts.getDcPer24BDuplicate();
                t.setDcBalance(t.getDcBalance() - uplinkDc);
                t.setDcBalance(t.getDcBalance() - duplicateDc);
                this.flushHeliumTenant(t);

                // publish message to update the stats async
                i.setUplinkDc(uplinkDc);
                i.setDuplicateDc(duplicateDc);
                i.setUplink(1);
                i.setDuplicate(duplicates);
                reportStatToMqtt(i);

                // check deactivation
                if ( !processBalance(ts,t) ) {
                    this.flushHeliumTenant(t);
                }
            }
        }

        log.debug("Process UPLINK in "+(Now.NowUtcMs()-start)+"ms");
    }

    public void processDownlink( String tenantUUID, String deviceUUID,  int payloadSize ) {
        if ( tenantUUID == null ) {
            tenantUUID = heliumDeviceCacheService.getTenantId(deviceUUID);
            if (tenantUUID == null) {
                log.error("Impossible to find tenantUUID for (" + deviceUUID + ") - 2");
                return;
            }
        }
        long start = Now.NowUtcMs();
        HeliumDeviceStatItf i = new HeliumDeviceStatItf();
        i.setDeviceId(deviceUUID);
        i.setTenantId(tenantUUID);
        HeliumTenantSetup ts = heliumTenantSetupService.getHeliumTenantSetup(tenantUUID,false);
        if ( ts == null ) {
            HeliumTenant t = this.getHeliumTenant(tenantUUID,true);
            ts = heliumTenantSetupService.getHeliumTenantSetup(tenantUUID,false);
            if ( ts == null ) {
                log.error("Should not be  here ... (1a)");
                return;
            }
        }
        synchronized (this) {
            HeliumTenant t = this.getHeliumTenant(tenantUUID,false);
            if (t != null) {
                payloadSize = (payloadSize / 24) + 1;
                int downlinkDc = payloadSize * ts.getDcPer24BDownlink();
                t.setDcBalance(t.getDcBalance() - downlinkDc);
                this.flushHeliumTenant(t);

                // publish message to update the stats async
                i.setDownlinkDc(downlinkDc);
                i.setDownlink(1);
                reportStatToMqtt(i);

                // check deactivation
                if ( !processBalance(ts,t) ) {
                    this.flushHeliumTenant(t);
                }
            }
        }

        log.debug("Process DOWNLINK in "+(Now.NowUtcMs()-start)+"ms");
    }


    public void processDeviceInsertionActivityInactivity(HeliumDeviceStatItf infos) {
        long start = Now.NowUtcMs();
        synchronized (this) {
            HeliumTenant t = this.getHeliumTenant(infos.getTenantId(),false);
            if (t != null) {
                t.setDcBalance(t.getDcBalance() - infos.getActivityDc());
                t.setDcBalance(t.getDcBalance() - infos.getInactivityDc());
                t.setDcBalance(t.getDcBalance() - infos.getRegistrationDc());
                this.flushHeliumTenant(t);

                HeliumTenantSetup ts = heliumTenantSetupService.getHeliumTenantSetup(infos.getTenantId(),false);
                if ( ts != null ) {
                    if ( !processBalance(ts,t) ) {
                        this.flushHeliumTenant(t);
                    }
                }
            }
        }
        reportStatToMqtt(infos);
        log.debug("Process INSERT ACTIVITY INACTIVITY in "+(Now.NowUtcMs()-start)+"ms");
    }

    @Autowired
    protected NovaService novaService;

    /**
     * This function is called when the device get a Join Success
     * Basically a Join Accept is fired in this case
     * @param tenantUUID
     * @param deviceUUID
     * @param devAddr
     */
    public void processJoin(String tenantUUID, String deviceUUID, String devAddr ) {
        long start = Now.NowUtcMs();
        HeliumDeviceStatItf i = new HeliumDeviceStatItf();
        i.setDeviceId(deviceUUID);
        i.setTenantId(tenantUUID);
        HeliumTenantSetup ts = heliumTenantSetupService.getHeliumTenantSetup(tenantUUID,false);
        if ( ts == null ) {
            HeliumTenant t = this.getHeliumTenant(tenantUUID,true);
            ts = heliumTenantSetupService.getHeliumTenantSetup(tenantUUID,false);
            if ( ts == null ) {
                log.error("Should not be  here ... (2)");
                return;
            }
        }
        synchronized (this) {
            HeliumTenant t = this.getHeliumTenant(tenantUUID,false);
            if (t != null) {
                t.setDcBalance(t.getDcBalance() - ts.getDcPerJoinAccept());
                this.flushHeliumTenant(t);

                // publish message to update the stats async
                i.setJoin(0);
                i.setDownlink(0);
                i.setDownlinkDc(0);
                i.setUplink(0);
                i.setUplinkDc(0);
                i.setJoinDc(0);
                i.setJoinAcceptDc(ts.getDcPerJoinAccept());
                reportStatToMqtt(i);

                // check deactivation
                if ( !processBalance(ts,t) ) {
                    this.flushHeliumTenant(t);
                }
            }
        }
        if ( consoleConfig.isHeliumGrpcSkfEnable() ) {
            novaService.refreshOneEuiSkf(ts.getRouteId(),deviceUUID);
        }
        log.debug("Process JOIN in "+(Now.NowUtcMs()-start)+"ms");
    }

    /**
     * This is called for a group of Join request including the duplicates
     * we consider a group as a single join request with multiple duplicates
     * @param deviceEui
     * @param packets
     */
    public void invoiceJoin(String deviceEui, int packets ) {
        long start = Now.NowUtcMs();
        String tenantUUID = heliumDeviceCacheService.getTenantId(deviceEui);
        if ( tenantUUID == null ) {
            log.error("Join invoice - Get a device to invoice w/o tenantId associated: "+deviceEui+" for "+packets+" packets");
            if ( heliumDeviceCacheService.getHeliumDevice(deviceEui,false) == null ) {
                log.error("And this device does not exists...");
            }
            return;
        }

        HeliumDeviceStatItf i = new HeliumDeviceStatItf();
        i.setDeviceId(deviceEui);
        i.setTenantId(tenantUUID);
        HeliumTenantSetup ts = heliumTenantSetupService.getHeliumTenantSetup(tenantUUID,false);
        if ( ts == null ) {
            HeliumTenant t = this.getHeliumTenant(tenantUUID,true);
            ts = heliumTenantSetupService.getHeliumTenantSetup(tenantUUID,false);
            if ( ts == null ) {
                log.error("Found a Tenant "+tenantUUID+" not having a TenantSetup associated (2)");
                return;
            }
        }
        synchronized (this) {
            HeliumTenant t = this.getHeliumTenant(tenantUUID,false);
            if (t != null) {
                if ( ts.getMaxJoinRequestDup() > 0 ) {
                    if ( packets > ts.getMaxJoinRequestDup()+1 ) {
                        // maximum number of invoiced duplicates, adding the original one
                        packets = ts.getMaxJoinRequestDup()+1;
                    }
                }
                t.setDcBalance(t.getDcBalance() - (long)packets*ts.getDcPerJoinRequest());
                this.flushHeliumTenant(t);

                // publish message to update the stats async
                i.setJoin(1);               // 1 join request detected
                i.setDuplicateJoin(packets-1);
                i.setUplink(0);
                i.setUplinkDc(0);
                i.setDownlinkDc(0);
                i.setDownlink(0);
                i.setJoinDc(packets*ts.getDcPerJoinRequest());
                i.setJoinAcceptDc(0);
                reportStatToMqtt(i);

                // check deactivation
                if ( !processBalance(ts,t) ) {
                    this.flushHeliumTenant(t);
                }
                prometeusService.addLoRaInvoicableUplink(packets);
            }
        }
        log.debug("Process JOIN invoicing in "+(Now.NowUtcMs()-start)+"ms");
    }


    /**
     * Process the tenant balance and return false when the balance has reached a minimum
     * value and devices needs to be blocked.
     * The device modification is asynchronously managed
     * @return false when the tenant runs out of DCs and deactivation is requested
     */
    protected boolean processBalance(HeliumTenantSetup ts, HeliumTenant t) {
        if ( t.getDcBalance() < ts.getDcBalanceStop() ) {
            ObjectMapper mapper = new ObjectMapper();
            mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
            mapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
            mapper.configure(SerializationFeature.FAIL_ON_SELF_REFERENCES, false);

            // manage deactivation
            t.setState(HeliumTenant.TenantState.REQUESTDEACTIVATION);
            HeliumTenantActDeactItf i = new HeliumTenantActDeactItf();
            i.setActivateTenant(false);
            i.setDeactivateTenant(true);
            i.setTenantId(t.getTenantUUID());
            i.setTime(Now.NowUtcMs());
            try {
                mqttSender.publishMessage(
                        "helium/tenant/manage/" + i.getTenantId(),
                        mapper.writeValueAsString(i),
                        2
                );
                this.prometeusService.addDelayedStatUpdate();
            } catch (Exception x) {
                this.prometeusService.addMqttConnectionLoss();
                log.error("Something went wrong with publishing on MQTT tenant deactivation");
                log.error(x.getMessage());
            }
            return false;
        } else {
            return true;
        }

    }

    /**
     * Add credits to a tenant, returns true when added
     * Asynchronous process
     * @param tenantUUID
     * @param amount
     * @return
     */
    public boolean processBalanceIncrease(String tenantUUID, long amount) {
        HeliumTenantSetup ts = heliumTenantSetupService.getHeliumTenantSetup(tenantUUID,false);
        if ( ts == null ) {
            log.error("Impossible to find tenant setup for "+tenantUUID);
            return false;
        }
        long balance = 0;
        long initialBalance = 0;
        boolean toReactivate = false;
        synchronized (this) {
            HeliumTenant t = this.getHeliumTenant(tenantUUID,false);
            if (t != null && ts != null) {
                initialBalance = t.getDcBalance();
                t.setDcBalance(initialBalance + amount);
                balance = t.getDcBalance();
                if (
                        ( initialBalance <= ts.getDcBalanceStop() || t.getState() == HeliumTenant.TenantState.DEACTIVATED )
                     && balance > ts.getDcBalanceStop()
                ) {
                    t.setState(HeliumTenant.TenantState.REQUESTREACTIVATION);
                    toReactivate = true;
                }
                this.flushHeliumTenant(t);
            }
        }

        if ( toReactivate ) {
            HeliumTenantActDeactItf i = new HeliumTenantActDeactItf();
            i.setActivateTenant(true);
            i.setDeactivateTenant(false);
            i.setTenantId(tenantUUID);
            i.setTime(Now.NowUtcMs());
            try {
                mqttSender.publishMessage(
                        "helium/tenant/manage/" + i.getTenantId(),
                        mapper.writeValueAsString(i),
                        2
                );
                this.prometeusService.addDelayedStatUpdate();
            } catch (Exception x) {
                this.prometeusService.addMqttConnectionLoss();
                log.error("Something went wrong with publishing on MQTT tenant reactivation");
                log.error(x.getMessage());
            }
        }
        return true;
    }


    /**
     * Commit tenant deactivation once made asynchronously
     */
    public void commitTenantDeactivation(String tenantUUID) {
        synchronized (this) {
            HeliumTenant t = this.getHeliumTenant(tenantUUID,false);
            if (t != null) {
                t.setState(HeliumTenant.TenantState.DEACTIVATED);
                this.flushHeliumTenant(t);
            }
        }
    }

    public void commitTenantReactivation(String tenantUUID) {
        synchronized (this) {
            HeliumTenant t = this.getHeliumTenant(tenantUUID,false);
            if (t != null) {
                t.setState(HeliumTenant.TenantState.NORMAL);
                this.flushHeliumTenant(t);
            }
        }
    }

    // Secure the tenant reactivation in case a payment is received but the
    // processing of the reactivation has been missed
    @Scheduled(fixedRateString = "${helium.tenant.activation.scanPeriod}", initialDelay = 40_000)
    protected void backgroundTenantReactivation() {
        log.info("running backgroundTenantReactivation");
        List<HeliumTenant> ts = heliumTenantRepository.findHeliumTenantByState(HeliumTenant.TenantState.DEACTIVATED);
        for ( HeliumTenant t : ts ) {
            HeliumTenantSetup s = heliumTenantSetupService.getHeliumTenantSetup(t.getTenantUUID(),false);
            if ( s != null && s.getDcBalanceStop() < t.getDcBalance() ) {
                // we have a candidate to be restored
                log.warn("Tenant "+t.getTenantUUID()+" was deactivated with valid DC Balance "+t.getDcBalance()+" / "+s.getDcBalanceStop());
                HeliumTenantActDeactItf i = new HeliumTenantActDeactItf();
                i.setActivateTenant(true);
                i.setDeactivateTenant(false);
                i.setTenantId(t.getTenantUUID());
                i.setTime(Now.NowUtcMs());
                try {
                    mqttSender.publishMessage(
                            "helium/tenant/manage/" + i.getTenantId(),
                            mapper.writeValueAsString(i),
                            2
                    );
                    this.prometeusService.addDelayedStatUpdate();
                } catch (Exception x) {
                    this.prometeusService.addMqttConnectionLoss();
                    log.error("Something went wrong with publishing on MQTT tenant reactivation");
                    log.error(x.getMessage());
                }
            }
        }
    }


    // ======================================================
    // API
    // ======================================================

    @Autowired
    protected UserCacheService userCacheService;

    @Autowired
    protected UserTenantRepository userTenantRepository;

    /**
     * Get the list of tenant a User own
     * @param userId
     * @return
     * @throws ITRightException
     */
    public List<TenantBalancesItf> getTenantDcBalances(String userId)
    throws ITRightException {
        UserCacheService.UserCacheElement user = userCacheService.getUserById(userId);
        if (user == null) throw new ITRightException();
        // admin see everything and don't need to buy Dcs ...
        ArrayList<TenantBalancesItf> rs = new ArrayList<>();
        if ( !user.user.isAdmin() ) {
            // search if tenant authorization exists
            List<UserTenant> uts = userTenantRepository.findUserTenantByUserIdAndIsAdmin(
                    UUID.fromString(userId),
                    true
            );
            if ( uts == null || uts.size() == 0 ) throw new ITRightException();

            for ( UserTenant ut : uts ) {
                HeliumTenant ht = this.getHeliumTenant(ut.getTenantId().toString(),true);
                HeliumTenantSetup hts = heliumTenantSetupService.getHeliumTenantSetup(ut.getTenantId().toString(),true);
                eu.heliumiot.console.jpa.db.Tenant t = this.getTenant(ut.getTenantId());
                TenantBalancesItf r = new TenantBalancesItf();
                r.setTenantUUID(ut.getTenantId().toString());
                r.setDcBalance(ht.getDcBalance());
                r.setMinBalance(hts.getDcBalanceStop());
                if ( t != null ) {
                    r.setTenantName(t.getName());
                }
                rs.add(r);
            }
        }
        return rs;
    }

    public List<TenantBalancesItf> getAllTenantDcBalances(String userId)
            throws ITRightException {
        UserCacheService.UserCacheElement user = userCacheService.getUserById(userId);
        if (user == null) throw new ITRightException();
        // admin see everything and don't need to buy Dcs ...
        ArrayList<TenantBalancesItf> rs = new ArrayList<>();
        if ( !user.user.isAdmin() ) {
            // search if tenant authorization exists
            List<UserTenant> uts = userTenantRepository.findUserTenantByUserId(
                    UUID.fromString(userId)
            );
            if ( uts == null || uts.size() == 0 ) throw new ITRightException();

            for ( UserTenant ut : uts ) {
                eu.heliumiot.console.jpa.db.Tenant t = this.getTenant(ut.getTenantId());
                TenantBalancesItf r = new TenantBalancesItf();
                r.setTenantUUID(ut.getTenantId().toString());
                r.setDcBalance(0);
                r.setMinBalance(0);
                if ( t != null ) {
                    r.setTenantName(t.getName());
                }
                rs.add(r);
            }
        }
        return rs;
    }


    public TenantBalanceItf getTenantDcBalance(String userId, String tenantId)
    throws ITRightException
    {

        UserCacheService.UserCacheElement user = userCacheService.getUserById(userId);
        if (user == null) throw new ITRightException();
        if ( !user.user.isAdmin() ) {
            // search if tenant authorization exists
            UserTenant ut = userTenantRepository.findOneUserByUserIdAndTenantId(
                    UUID.fromString(userId),
                    UUID.fromString(tenantId)
            );

            if ( ut == null ) throw new ITRightException();
            // when non admin we just return 0, only admin can see DC balance
            // be we can add an information with a color code under 1000 DCs
            // Here we are the right to get the DC Balance info
            if ( ! ut.isAdmin() ) {
                HeliumTenant ht = this.getHeliumTenant(tenantId,true);
                HeliumTenantSetup hts = heliumTenantSetupService.getHeliumTenantSetup(tenantId,true);
                eu.heliumiot.console.jpa.db.Tenant t = this.getTenant(UUID.fromString(ht.getTenantUUID()));
                TenantBalanceItf r = new TenantBalanceItf();
                r.setDcBalance(0);
                r.setMinBalance(0);
                r.setBalanceOk((ht.getDcBalance()-hts.getDcBalanceStop()) > 1000);
                r.setOwnerMode(false);
                if ( t != null ) {
                    r.setTenantName(t.getName());
                }
                return r;
            }
        }

        // Here we are the right to get the DC Balance info
        HeliumTenant ht = this.getHeliumTenant(tenantId,false);
        long bstop = 0;
        long bal = 0;
        String name = "N/A";
        if ( ht != null ) {
            HeliumTenantSetup hts = heliumTenantSetupService.getHeliumTenantSetup(tenantId, true);
            eu.heliumiot.console.jpa.db.Tenant t = this.getTenant(UUID.fromString(ht.getTenantUUID()));
            bstop = hts.getDcBalanceStop();
            bal = ht.getDcBalance();
            if ( t != null ) {
                name = t.getName();
            }
        }

        TenantBalanceItf r = new TenantBalanceItf();
        r.setDcBalance(bal);
        r.setMinBalance(bstop);
        r.setOwnerMode(true);
        r.setBalanceOk((bal-bstop) > 1000);
        r.setTenantName(name);
        return r;
    }

    @Autowired
    protected ChirpstackApiAccess chirpstackApiAccess;


    /**
     * Create a new tenant for an existing user. Possible until reaching the max tenant parameter
     * associated to the given coupon code of default. Coupon code can be "" (default)
     * @param userId
     * @param req
     * @throws ITParseException
     * @throws ITRightException
     */
    public void addNewTenant(String userId, TenantCreateReqItf req)
    throws ITParseException, ITRightException {

        // verify tenantName
        if ( req.getTenantName().length() < 3 ) {
            throw new ITParseException("error_tenant_size");
        }

        // get User
        UserCacheService.UserCacheElement user = userCacheService.getUserById(userId);
        if ( user == null ) throw new ITRightException();

        // check profile
        String profile = HELIUM_TENANT_SETUP_DEFAULT;
        if  (req.getCouponCode().length() > 0) {
            // process verification ...
            // set profile based on the invitation code verification
            String uuid = heliumTenantSetupService.acquiresCoupon(req.getCouponCode());
            if ( uuid != null ) {
                profile = uuid;
            } else {
                throw new ITParseException("error_invalidcoupon");
            }
        }

        // is signup allowed
        HeliumTenantSetup hts = heliumTenantSetupService.getHeliumTenantSetup(profile,false);
        if( hts == null ) {
            throw new ITParseException("error_invalidcoupon");
        }

        // get tenant owned by user to check number limit
        List<UserTenant> uts = userTenantRepository.findUserTenantByUserIdAndIsAdmin(
                user.user.getId(),
                true
        );
        if ( uts.size() >= hts.getMaxOwnedTenants() ) throw new ITParseException("error_max_tenant");

        // Lets create the tenant first
        Tenant tenant = Tenant.newBuilder()
                .setName(req.getTenantName())
                .setDescription("Other user tenant")
                .setCanHaveGateways(false)
                .setMaxDeviceCount(hts.getMaxDevices())
                .setMaxGatewayCount(0)
                .setPrivateGateways(false)
                .build();

        CreateTenantRequest tenantReq = CreateTenantRequest.newBuilder()
                .setTenant(tenant)
                .build();

        HttpHeaders heads = new HttpHeaders();
        heads.add("authorization", "Bearer "+consoleConfig.getChirpstackApiAdminKey());

        String tenantId = null;
        try {

            byte[] respB = chirpstackApiAccess.execute(
                    HttpMethod.POST,
                    "/api.TenantService/Create",
                    null,
                    heads,
                    tenantReq.toByteArray()
            );
            CreateTenantResponse resp = CreateTenantResponse.parseFrom(respB);

            if ( resp != null ) {
                tenantId = resp.getId();
            }

        } catch ( ITRightException x ) {
            log.error("Impossible to create additional tenant - rights");
        } catch ( ITNotFoundException x ) {
            log.error("Impossible to create additional tenant - not found");
        } catch ( InvalidProtocolBufferException x ) {
            log.error("Impossible to create additional tenant - protobuf");
        }

        if ( tenantId == null ) throw new ITParseException("error_internal");

        TenantUser tu = TenantUser.newBuilder()
                .setIsAdmin(true)
                .setIsDeviceAdmin(true)
                .setIsGatewayAdmin(false)
                .setTenantId(tenantId)
                .setEmail(user.user.getEmail())
                .setUserId(user.user.getId().toString())
                .build();

        AddTenantUserRequest addtenant = AddTenantUserRequest.newBuilder()
                .setTenantUser(tu)
                .build();

        boolean linksuccess = false;
        try {

            byte[] respB = chirpstackApiAccess.execute(
                    HttpMethod.POST,
                    "/api.TenantService/AddUser",
                    null,
                    heads,
                    addtenant.toByteArray()
            );
            linksuccess = true;

        } catch ( ITRightException x ) {
            log.error("Impossible to create additional tenant 2 - rights");
            this.deleteTenant(null,tenantId,true);
            throw new ITParseException("error_internal");
        } catch ( ITNotFoundException x ) {
            log.error("Impossible to create additional tenant 2 - not found");
            this.deleteTenant(null,tenantId,true);
            throw new ITParseException("error_internal");
        }

        if ( linksuccess ) {
            // create the HeliumTenantSetup
            heliumTenantSetupService.createAndSave(hts, tenantId);

            // create HeliumTenant
            this.createNewHeliumTenant(tenantId, hts);
        }

    }


    public void deleteTenant(String userId, String tenantId, boolean force)
    throws ITRightException, ITParseException {

        if ( !force ) {
            log.error("Tenant deletion not forced is not implemented");
            return;
        }

        HttpHeaders heads = new HttpHeaders();
        heads.add("authorization", "Bearer "+consoleConfig.getChirpstackApiAdminKey());

        // delete tenant
        DeleteTenantRequest dt = DeleteTenantRequest.newBuilder()
                .setId(tenantId)
                .build();
        try {

            byte[] respB = chirpstackApiAccess.execute(
                    HttpMethod.POST,
                    "/api.TenantService/Delete",
                    null,
                    heads,
                    dt.toByteArray()
            );

        } catch ( ITRightException x ) {
            log.error("Impossible to delete tenant - rights");
            throw new ITParseException("error_internal");
        } catch (ITNotFoundException x) {
            log.error("Impossible to delete tenant - not found");
            throw new ITParseException("error_internal");
        } catch ( ITParseException x) {
            log.error("Impossible to delete tenant - parse");
            throw new ITParseException("error_internal");
        }

    }

    // ------------
    // Search tenants by id / name / owner email ...
    @Autowired
    protected TenantRepository tenantRepository;

    @Autowired
    protected HeliumUserRepository heliumUserRepository;

    public List<TenantSearchRespItf> searchTenants(String searchKey)
    throws ITParseException {
        if ( searchKey.length() < 3 || searchKey.length() > 15 ) throw new ITParseException();
        HashMap<String, String> tenantsId = new HashMap<>();

        // bypass "***" for getting all tenants
        if ( searchKey.compareTo("***") == 0 ) {

            Iterable<eu.heliumiot.console.jpa.db.Tenant> t0 = tenantRepository.findAll();
            for (eu.heliumiot.console.jpa.db.Tenant t : t0) {
                if (tenantsId.get(t.getId().toString()) == null) {
                    tenantsId.put(t.getId().toString(), t.getId().toString());
                }
            }

        } else {
            // create a list with 10 - 20 entries based on the search criteria type like
            List<eu.heliumiot.console.jpa.db.Tenant> t0 = tenantRepository.findTenantLike(searchKey);
            for (eu.heliumiot.console.jpa.db.Tenant t : t0) {
                if (tenantsId.get(t.getId().toString()) == null) {
                    tenantsId.put(t.getId().toString(), t.getId().toString());
                }
            }

            // search 5 owners with email like
            List<HeliumUser> u1 = heliumUserRepository.findHeliumUsersBySearch(searchKey);
            for (HeliumUser u : u1) {
                List<UserTenant> t1 = userTenantRepository.findUserTenantByUserId(UUID.fromString(u.getUserid()));
                for (UserTenant t : t1) {
                    if (tenantsId.get(t.getTenantId().toString()) == null) {
                        tenantsId.put(t.getTenantId().toString(), t.getTenantId().toString());
                    }
                }
            }
        }

        // Build the output based on this
        ArrayList<TenantSearchRespItf> r = new ArrayList<>();
        for ( String tId : tenantsId.values() ) {
            HeliumTenant ht = this.getHeliumTenant(tId,false);
            if ( ht == null ) continue;
            HeliumTenantSetup hts = heliumTenantSetupService.getHeliumTenantSetup(tId,false);
            if ( hts == null ) continue;
            TenantSearchRespItf k = new TenantSearchRespItf();
            k.setTenantUUID(ht.getTenantUUID());
            k.setRouteId(((hts != null && hts.getRouteId() != null)?hts.getRouteId():"N/A"));
            k.setDcBalance(ht.getDcBalance());
            eu.heliumiot.console.jpa.db.Tenant t = this.getTenant(UUID.fromString(tId));
            if ( t == null ) continue;
            k.setTenantName(t.getName());
            List<UserTenant> u = userTenantRepository.findUserTenantByTenantIdAndIsAdmin(t.getId(),true);
            if ( u.size() == 0 ) k.setOwnerEmail("admin");
            else {
                UserCacheService.UserCacheElement uc = userCacheService.getUserById(u.get(0).getUserId().toString());
                k.setOwnerEmail(uc.user.getEmail());
            }
            r.add(k);
        }
        return r;
    }

    // ###################

    @Autowired
    protected HeliumTenantStatService heliumTenantStatService;

    public TenantBasicStatRespItf getTenantBasicStat(String userId, String tenantId)
    throws ITRightException, ITParseException {
        // check user and ownership
        UserCacheService.UserCacheElement user = userCacheService.getUserById(userId);
        if (user == null) throw new ITRightException();
        if ( !user.user.isAdmin() ) {
            // search if tenant authorization exists
            UserTenant ut = userTenantRepository.findOneUserByUserIdAndTenantId(
                    UUID.fromString(userId),
                    UUID.fromString(tenantId)
            );

            if ( ut == null ) throw new ITRightException();
            if ( ! ut.isAdmin() ) {
                throw new ITRightException();
            }
        }

        long duration = 7*Now.ONE_FULL_DAY;
        long start = Now.TodayMidnightUtc() - duration;
        TenantBasicStatRespItf stats = heliumTenantStatService.getStatForTenantFromDate(
                tenantId,
                start,
                duration
        );

        return stats;
    }

    public TenantSetupStatsRespItf getTenantActivityStat(String userId, String tenantId)
        throws ITRightException, ITParseException {
        // check user and ownership
        UserCacheService.UserCacheElement user = userCacheService.getUserById(userId);
        if (user == null) throw new ITRightException();
        if ( !user.user.isAdmin() ) {
            // search if tenant authorization exists
            UserTenant ut = userTenantRepository.findOneUserByUserIdAndTenantId(
                UUID.fromString(userId),
                UUID.fromString(tenantId)
            );

            if ( ut == null ) throw new ITRightException();
            if ( ! ut.isAdmin() ) {
                throw new ITRightException();
            }
        }

        long duration = 15*Now.ONE_FULL_DAY;
        long start = Now.TodayMidnightUtc() - duration;
        TenantSetupStatsRespItf stats = heliumTenantStatService.getTenantStatsForChart(
            tenantId,
            start,
            duration
        );

        return stats;
    }

    public TenantSetupStatsRespItf getTenantDeviceActivityStat(String userId, String tenantId)
        throws ITRightException, ITParseException {
        // check user and ownership
        UserCacheService.UserCacheElement user = userCacheService.getUserById(userId);
        if (user == null) throw new ITRightException();
        if ( !user.user.isAdmin() ) {
            // search if tenant authorization exists
            UserTenant ut = userTenantRepository.findOneUserByUserIdAndTenantId(
                UUID.fromString(userId),
                UUID.fromString(tenantId)
            );

            if ( ut == null ) throw new ITRightException();
            if ( ! ut.isAdmin() ) {
                throw new ITRightException();
            }
        }

        long duration = 2*Now.ONE_FULL_DAY;     // during the last 48 hours
        long start = Now.NowUtcMs() - duration;
        TenantSetupStatsRespItf stats = heliumTenantStatService.getTenantDeviceStatsForChart(
            tenantId,
            start,
            duration,
            10          // 10 most consuming devices
        );

        return stats;
    }

    public TenantSetupStatsRespItf getTenantDeviceInactivityStat(String userId, String tenantId)
        throws ITRightException, ITParseException {
        // check user and ownership
        UserCacheService.UserCacheElement user = userCacheService.getUserById(userId);
        if (user == null) throw new ITRightException();
        if ( !user.user.isAdmin() ) {
            // search if tenant authorization exists
            UserTenant ut = userTenantRepository.findOneUserByUserIdAndTenantId(
                UUID.fromString(userId),
                UUID.fromString(tenantId)
            );

            if ( ut == null ) throw new ITRightException();
            if ( ! ut.isAdmin() ) {
                throw new ITRightException();
            }
        }

        // get devices with activity but no uplink (like join loop) or inactivity_dc > 0
        long duration = Now.ONE_FULL_DAY;     // during the last 24 hours
        long start = Now.NowUtcMs() - duration;
        TenantSetupStatsRespItf stats = heliumTenantStatService.getTenantInactiveDeviceStatsForChart(
            tenantId,
            start,
            duration,
            10          // max 10 devices
        );

        return stats;
    }

    /* Admin API to get stat on top tenant for analysis */
    public TenantSetupStatsRespItf getTopTenantActivityStat(String userId)
        throws ITRightException, ITParseException {
        // check user and ownership
        UserCacheService.UserCacheElement user = userCacheService.getUserById(userId);
        if (user == null) throw new ITRightException();
        if ( !user.user.isAdmin() ) {
           throw new ITRightException();
        }

        // get devices with activity but no uplink (like join loop) or inactivity_dc > 0
        long duration = Now.ONE_FULL_DAY;     // during the last 24 hours
        long start = Now.NowUtcMs() - duration;
        TenantSetupStatsRespItf stats = heliumTenantStatService.getTopConsumerStatsForChart(
            start,
            duration,
            20          // max 20 tenant
        );

        return stats;
    }


    public TenantSetupRespItf getTenantSetup(String userId, String tenantId)
            throws ITRightException {
        TenantSetupRespItf r = new TenantSetupRespItf();

        UserCacheService.UserCacheElement user = userCacheService.getUserById(userId);
        if (user == null) throw new ITRightException();
        if ( !user.user.isAdmin() ) {
            // search if tenant authorization exists
            UserTenant ut = userTenantRepository.findOneUserByUserIdAndTenantId(
                    UUID.fromString(userId),
                    UUID.fromString(tenantId)
            );

            if ( ut == null ) throw new ITRightException();
            if ( ! ut.isAdmin() ) {
                throw new ITRightException();
            }
        }
        HeliumTenantSetup ts = heliumTenantSetupService.getHeliumTenantSetup(tenantId,false);
        if ( ts == null ) throw new ITRightException();

        r.setTenantUUID(tenantId);
        r.setDcBalanceStop(ts.getDcBalanceStop());
        r.setDcMin(ts.getDcMin());
        r.setDcPrice(ts.getDcPrice());
        return r;
    }


    @Autowired
    protected HeliumDcTransactionRepository heliumDcTransactionRepository;

    @Autowired
    protected EncryptionHelper encryptionHelper;

    public static final int HTRANSACTION_TYPE_TRANSFER = 0;
    public static final int HTRANSACTION_TYPE_STRIPE = 1;


    /**
     * Transfer DCs between different tenant
     * The source must be owned by user
     * The destination must be accessible by user
     * The amount is a maximum
     * @param userId
     * @param req
     * @return
     */
    public TenantDcTransferRespItf transferDcBetweenTenant(
            String userId,
            String ip,
            TenantDcTransferReqItf req
    ) throws ITRightException {

        // check if stripe is authorized
        if ( ! consoleConfig.isTransferEnable() ) throw new ITRightException("transfer_disable");

        UserCacheService.UserCacheElement user = userCacheService.getUserById(userId);
        if (user == null) throw new ITRightException();
        if (req.getDcs() <= 0) throw new ITRightException();

        // check ownership
        UserTenant ts = userTenantRepository.findOneUserByUserIdAndTenantId(UUID.fromString(userId), UUID.fromString(req.getTenantSrcUUID()));
        UserTenant td = userTenantRepository.findOneUserByUserIdAndTenantId(UUID.fromString(userId), UUID.fromString(req.getTenantDestUUID()));
        if ( ts == null || ts.isAdmin() == false || td == null ) {
            log.warn("TransferDC - attempt to transfer from not owned tenant by " + userId);
            throw new ITRightException();
        }

        // security, Dc transfer should be higher than the default given DCs
        // to make it simpler, need at least to transfer 2_000 DCs
        // possibly we could add a DC transfer rate limit
        if ( req.getDcs() < 2_000 ) throw new ITRightException("dc_amount");

        // Execute transaction
        long realDc = 0;
        synchronized (this) {
            HeliumTenant src = this.getHeliumTenant(req.getTenantSrcUUID(),false);
            if (src != null) {
                if ( src.getDcBalance() >= req.getDcs() ) {
                    src.setDcBalance(src.getDcBalance()-req.getDcs());
                    realDc = req.getDcs();
                } else {
                    realDc = src.getDcBalance();
                    if ( realDc > 0 ) {
                        src.setDcBalance(0);
                    } else realDc = 0;
                }
                if ( realDc > 0 ) {
                    this.flushHeliumTenant(src);
                }
            }
        }

        if ( realDc == 0 ) throw new ITRightException();

        if ( ! processBalanceIncrease(req.getTenantDestUUID(), realDc) ) {
            // rollback
            log.error("RollBack in transaction, Template messaging... strange");
            synchronized (this) {
                HeliumTenant src = this.getHeliumTenant(req.getTenantSrcUUID(),false);
                if (src != null ) {
                        src.setDcBalance(src.getDcBalance()+realDc);
                }
                throw new ITRightException();
            }
        }


        // Store transaction
        HeliumDcTransaction t = new HeliumDcTransaction();
        t.setUserUUID(user.user.getId().toString());
        t.setType(HTRANSACTION_TYPE_TRANSFER);
        t.setDcs(realDc);
        t.setDcsRequested(req.getDcs());
        t.setTargetTenantUUID(req.getTenantDestUUID());
        t.setSourceTenantUUID(req.getTenantSrcUUID());
        t.setCreatedAt(new Timestamp(Now.NowUtcMs()));
        try {
            if ( ip != null ) {
                String eIp = encryptionHelper.encryptStringWithServerKey(ip);
                t.setUserIP(eIp);
            } else t.setUserIP("");
        } catch (Exception e) {
            t.setUserIP("");
        }
        t = heliumDcTransactionRepository.save(t);

        TenantDcTransferRespItf r = new TenantDcTransferRespItf();
        r.setTranscationUUID(t.getId().toString());
        r.setDcs(t.getDcs());

        return r;
    }

    /**
     * Get a API key to access tenant information through the chirpstack restapi
     * If not existing an API key is created. Name of the key MigrationKey
     * @param userUUID
     * @param tenantUUID
     * @return
     */
    public TenantApiKeyRespItf getTenantApiKey(String userUUID, String tenantUUID, String bearer)
    throws ITParseException, ITRightException {

        UserCacheService.UserCacheElement user = userCacheService.getUserById(userUUID);
        if (user == null) throw new ITRightException();

        // check ownership
        UserTenant ts = userTenantRepository.findOneUserByUserIdAndTenantId(UUID.fromString(userUUID), UUID.fromString(tenantUUID));
        if ( ts == null || ts.isAdmin() == false ) {
            log.warn("Create ApiKey - attempt to create from non tenant owner / try by " + userUUID);
            throw new ITRightException();
        }

        this.clearMigrationApiKey(userUUID,tenantUUID, bearer);

        HttpHeaders heads = new HttpHeaders();
        heads.add("authorization", "Bearer "+bearer);

        try {

            // Create a new Api key
            io.chirpstack.restapi.ApiKey a = io.chirpstack.restapi.ApiKey.newBuilder()
                    .setTenantId(tenantUUID)
                    .setName("MigrationKey")
                    .setIsAdmin(false)
                    .build();

            CreateApiKeyRequest car = CreateApiKeyRequest.newBuilder()
                    .setApiKey(a)
                    .build();

            byte[] respC = chirpstackApiAccess.execute(
                    HttpMethod.POST,
                    "/api.InternalService/CreateApiKey",
                    null,
                    heads,
                    car.toByteArray()
            );

            if ( respC != null ) {
                CreateApiKeyResponse kr = CreateApiKeyResponse.parseFrom(respC);
                TenantApiKeyRespItf r = new TenantApiKeyRespItf();
                r.setTenantUUID(tenantUUID);
                r.setTenantApiKey(kr.getToken());
                return r;
            }

        } catch ( ITRightException x ) {
            log.error("Impossible to create api keys - rights");
        } catch ( ITNotFoundException x ) {
            log.error("Impossible to create api keys - not found");
        } catch ( InvalidProtocolBufferException x ) {
            log.error("Impossible to create api keys - protobuf");
        } catch ( ITParseException x) {
            log.error("Impossible to create api keys - parse "+x.getMessage());
        }
        throw new ITParseException("api_key_create");
    }

    /**
     * Clear the Migration API keys
     * @param userUUID
     * @param tenantUUID
     * @throws ITParseException
     * @throws ITRightException
     */
    public void clearMigrationApiKey(String userUUID, String tenantUUID, String bearer)
    throws ITParseException, ITRightException {

        UserCacheService.UserCacheElement user = userCacheService.getUserById(userUUID);
        if (user == null) throw new ITRightException("api_key_right");

        // check ownership
        UserTenant ts = userTenantRepository.findOneUserByUserIdAndTenantId(UUID.fromString(userUUID), UUID.fromString(tenantUUID));
        if ( ts == null || ts.isAdmin() == false ) {
            log.warn("Create ApiKey - attempt to delete from non tenant owner / try by " + userUUID);
            throw new ITRightException("api_key_right");
        }

        ListApiKeysRequest lar = ListApiKeysRequest.newBuilder()
                .setTenantId(tenantUUID)
                .setOffset(0)
                .setLimit(10)
                .setIsAdmin(false)
                .build();

        HttpHeaders heads = new HttpHeaders();
        heads.add("authorization", "Bearer "+bearer);
        try {

            byte[] respB = chirpstackApiAccess.execute(
                    HttpMethod.POST,
                    "/api.InternalService/ListApiKeys",
                    null,
                    heads,
                    lar.toByteArray()
            );
            ListApiKeysResponse resp = ListApiKeysResponse.parseFrom(respB);
            if ( resp != null ) {
                for ( io.chirpstack.restapi.ApiKey a : resp.getResultList() ) {
                    if ( a.getName().compareToIgnoreCase("MigrationKey") == 0 ) {
                        // destroy the previous ApiKey
                        DeleteApiKeyRequest dar = DeleteApiKeyRequest.newBuilder()
                                .setId(a.getId())
                                .build();
                        try {
                            chirpstackApiAccess.execute(
                                    HttpMethod.POST,
                                    "/api.InternalService/DeleteApiKey",
                                    null,
                                    heads,
                                    dar.toByteArray()
                            );
                        } catch (Exception x) {
                            log.error("Failed to delete previous Api Key "+x.getMessage());
                        }

                    }
                }
            }
            return;

        } catch ( ITRightException x ) {
            log.error("Impossible to list api keys - rights");
        } catch ( ITNotFoundException x ) {
            log.error("Impossible to list api keys - not found");
        } catch ( InvalidProtocolBufferException x ) {
            log.error("Impossible to list api keys - protobuf");
        } catch ( ITParseException x) {
            log.error("Impossible to list api keys - parse "+x.getMessage());
        }
        throw new ITParseException("api_key_delete");

    }

    /**
     * update the Max Copy param for a given tenant / route
     * this is the number of frame the router can purchase for a single packet
     * @param userUUID
     * @param req
     * @throws ITRightException
     */
    public void updateMaxCopyValue(String userUUID, TenantUpdateMaxCopyReqItf req)
    throws ITRightException, ITParseException {
        UserCacheService.UserCacheElement user = userCacheService.getUserById(userUUID);
        if (user == null) throw new ITRightException("tenant_right");

        // Check value
        if ( req.getNewMaxCopy() <= 0 || req.getNewMaxCopy() > consoleConfig.getHeliumRouteMaxCopy() )
            throw new ITParseException("tenant_mc_value");

        // check ownership
        if (! user.user.isAdmin() ) {
            UserTenant ts = userTenantRepository.findOneUserByUserIdAndTenantId(UUID.fromString(userUUID), UUID.fromString(req.getTenantId()));
            if (ts == null || ts.isAdmin() == false) {
                log.warn("updateMaxCopyValue - attempt to update by non tenant owner / try by " + userUUID);
                throw new ITRightException("tenant_right");
            }
        }

        // Now we need to update the route
        HeliumTenantSetup hts = heliumTenantSetupService.getHeliumTenantSetup(req.getTenantId(),false);
        if ( hts == null ) throw new ITRightException("tenant_notfound");
        route_v1 resp = novaService.grpcUpdateOneRoute(hts.getRouteId(),req.getNewMaxCopy());
        if ( resp != null ) {
            // Ok, update it
            synchronized (this) {
                HeliumTenant ht = getHeliumTenant(req.getTenantId(),false);
                ht.setMaxCopy(req.getNewMaxCopy());
                flushHeliumTenant(ht);
            }
            hts.setMaxCopy(req.getNewMaxCopy());
            heliumTenantSetupService.flushTenantSetup(hts);
        } else {
            throw new ITParseException("nova_failure");
        }

    }


}
