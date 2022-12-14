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
import eu.heliumiot.console.ConsoleConfig;
import eu.heliumiot.console.api.interfaces.TenantBalanceItf;
import eu.heliumiot.console.jpa.db.HeliumTenant;
import eu.heliumiot.console.jpa.db.HeliumTenantSetup;
import eu.heliumiot.console.jpa.db.UserTenant;
import eu.heliumiot.console.jpa.repository.UserTenantRepository;
import eu.heliumiot.console.mqtt.MqttSender;
import eu.heliumiot.console.mqtt.api.HeliumDeviceStatItf;
import eu.heliumiot.console.jpa.repository.HeliumTenantRepository;
import eu.heliumiot.console.mqtt.api.HeliumTenantActDeactItf;
import fr.ingeniousthings.tools.ITRightException;
import fr.ingeniousthings.tools.Now;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
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
    protected HeliumTenant getHeliumTenant(String tenantUUID){
        HeliumTenant t = heliumTenantRepository.findOneHeliumTenantByTenantUUID(tenantUUID);
        if ( t == null ) {
            // create it
            HeliumTenantSetup ts = heliumTenantSetupService.getHeliumTenantSetup(tenantUUID);
            t = new HeliumTenant();
            t.setTenantUUID(tenantUUID);
            t.setDcBalance(ts.getFreeTenantDc());
            t.setState(HeliumTenant.TenantState.NORMAL);
            return heliumTenantRepository.save(t);
        } else return t;
    }

    protected void flushHeliumTenant(HeliumTenant t) {
        log.debug("#> new balance "+t.getDcBalance()+" for "+t.getTenantUUID());
        heliumTenantRepository.save(t);
    }

    // ======================================================
    // Async reporting
    // ======================================================


    @Autowired
    protected MqttSender mqttSender;

    public void reportStatToMqtt(HeliumDeviceStatItf i) {

        try {
            mqttSender.publishMessage(
                    "helium/device/stats/" + i.getDeviceId(),
                    mapper.writeValueAsString(i),
                    2
            );
        } catch (Exception x) {
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
        HeliumTenantSetup ts = heliumTenantSetupService.getHeliumTenantSetup(tenantUUID);
        if ( ts == null ) {
            HeliumTenant t = this.getHeliumTenant(tenantUUID);
            ts = heliumTenantSetupService.getHeliumTenantSetup(tenantUUID);
            if ( ts == null ) {
                log.error("Should not be  here ... (1)");
                return;
            }
        }
        synchronized (this) {
            HeliumTenant t = this.getHeliumTenant(tenantUUID);
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
        HeliumTenantSetup ts = heliumTenantSetupService.getHeliumTenantSetup(tenantUUID);
        if ( ts == null ) {
            HeliumTenant t = this.getHeliumTenant(tenantUUID);
            ts = heliumTenantSetupService.getHeliumTenantSetup(tenantUUID);
            if ( ts == null ) {
                log.error("Should not be  here ... (1a)");
                return;
            }
        }
        synchronized (this) {
            HeliumTenant t = this.getHeliumTenant(tenantUUID);
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
            HeliumTenant t = this.getHeliumTenant(infos.getTenantId());
            if (t != null) {
                t.setDcBalance(t.getDcBalance() - infos.getActivityDc());
                t.setDcBalance(t.getDcBalance() - infos.getInactivityDc());
                t.setDcBalance(t.getDcBalance() - infos.getRegistrationDc());
                this.flushHeliumTenant(t);

                HeliumTenantSetup ts = heliumTenantSetupService.getHeliumTenantSetup(infos.getTenantId());
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
    public void processJoin(String tenantUUID, String deviceUUID, String devAddr ) {
        long start = Now.NowUtcMs();
        HeliumDeviceStatItf i = new HeliumDeviceStatItf();
        i.setDeviceId(deviceUUID);
        i.setTenantId(tenantUUID);
        HeliumTenantSetup ts = heliumTenantSetupService.getHeliumTenantSetup(tenantUUID);
        if ( ts == null ) {
            HeliumTenant t = this.getHeliumTenant(tenantUUID);
            ts = heliumTenantSetupService.getHeliumTenantSetup(tenantUUID);
            if ( ts == null ) {
                log.error("Should not be  here ... (2)");
                return;
            }
        }
        synchronized (this) {
            HeliumTenant t = this.getHeliumTenant(tenantUUID);
            if (t != null) {
                int uplinkDc = ts.getDcPer24BMessage();
                int downlinkDc = ts.getDcPer24BDownlink();
                t.setDcBalance(t.getDcBalance() - uplinkDc);
                t.setDcBalance(t.getDcBalance() - downlinkDc);
                this.flushHeliumTenant(t);

                // publish message to update the stats async
                i.setUplinkDc(uplinkDc);
                i.setDownlinkDc(downlinkDc);
                i.setJoin(1);
                i.setDownlink(1); // JOIN ACCEPT
                reportStatToMqtt(i);

                // check deactivation
                if ( !processBalance(ts,t) ) {
                    this.flushHeliumTenant(t);
                }
            }
        }
        novaService.refreshDevAddrList(devAddr);
        log.debug("Process JOIN in "+(Now.NowUtcMs()-start)+"ms");
    }


    /**
     * Process the tenant balance and return false when the balance has reached a minimum
     * value and devices needs to be blocked.
     * The device modification is asynchronously managed
     * @return false when the tenant runs out of DCs and deactiviation is requested
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
            } catch (Exception x) {
                log.error("Something went wrong with publishing on MQTT tenant deactivation");
                log.error(x.getMessage());
            }
            return false;
        } else {
            return true;
        }

    }

    /**
     * Add credits to a tennant, returns true when added
     * Asynchronous process
     * @param tenantUUID
     * @param amount
     * @return
     */
    public boolean processBalanceIncrease(String tenantUUID, long amount) {
        HeliumTenantSetup ts = heliumTenantSetupService.getHeliumTenantSetup(tenantUUID);
        if ( ts == null ) {
            log.error("Impossible to find tenant setup for "+tenantUUID);
            return false;
        }
        long balance = 0;
        synchronized (this) {
            HeliumTenant t = this.getHeliumTenant(tenantUUID);
            if (t != null && ts != null) {
                t.setDcBalance(t.getDcBalance() + amount);
                balance = t.getDcBalance();
                if ( balance > ts.getDcBalanceStop() ) {
                    t.setState(HeliumTenant.TenantState.REQUESTREACTIVATION);
                }
                this.flushHeliumTenant(t);
            }
        }

        if ( balance > ts.getDcBalanceStop() ) {
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
            } catch (Exception x) {
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
            HeliumTenant t = this.getHeliumTenant(tenantUUID);
            if (t != null) {
                t.setState(HeliumTenant.TenantState.DEACTIVATED);
                this.flushHeliumTenant(t);
            }
        }
    }

    public void commitTenantReactivation(String tenantUUID) {
        synchronized (this) {
            HeliumTenant t = this.getHeliumTenant(tenantUUID);
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
            HeliumTenantSetup s = heliumTenantSetupService.getHeliumTenantSetup(t.getTenantUUID());
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
                } catch (Exception x) {
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
    protected UserService userService;

    @Autowired
    protected UserTenantRepository userTenantRepository;

    public TenantBalanceItf getTenantDcBalance(String userId, String tenantId)
    throws ITRightException
    {

        UserService.UserCacheElement user = userService.getUserById(userId);
        if (user == null) throw new ITRightException();
        if ( !user.user.isAdmin() ) {
            // search if tenant authorization exists
            UserTenant ut = userTenantRepository.findOneUserByUserIdAndTenantId(
                    UUID.fromString(userId),
                    UUID.fromString(tenantId)
            );

            if ( ut == null ) throw new ITRightException();
            // when non admin we just return 0, only admin can see DC balance
            if ( ! ut.isAdmin() ) {
                TenantBalanceItf r = new TenantBalanceItf();
                r.setDcBalance(0);
                r.setMinBalance(0);
                return r;
            }
        }

        // Here we are the right to get the DC Balance info
        HeliumTenant ht = this.getHeliumTenant(tenantId);
        HeliumTenantSetup hts = heliumTenantSetupService.getHeliumTenantSetup(tenantId);

        TenantBalanceItf r = new TenantBalanceItf();
        r.setDcBalance(ht.getDcBalance());
        r.setMinBalance(hts.getDcBalanceStop());
        return r;
    }

}
