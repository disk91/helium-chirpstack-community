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
import eu.heliumiot.console.jpa.db.HeliumTenant;
import eu.heliumiot.console.jpa.db.HeliumTenantSetup;
import eu.heliumiot.console.mqtt.MqttSender;
import eu.heliumiot.console.mqtt.api.HeliumDeviceStatItf;
import eu.heliumiot.console.jpa.repository.HeliumTenantRepository;
import eu.heliumiot.console.jpa.repository.HeliumTenantSetupRepository;
import eu.heliumiot.console.mqtt.api.HeliumTenantActDeactItf;
import fr.ingeniousthings.tools.Now;
import fr.ingeniousthings.tools.ObjectCache;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;

@Service
public class HeliumTenantService {
    private final Logger log = LoggerFactory.getLogger(this.getClass());

    @Autowired
    protected ConsoleConfig consoleConfig;

    protected int runningJobs;
    protected boolean serviceEnable; // false to stop the services

    private ObjectCache<String,HeliumTenantSetup> heliumSetupCache;
    @PostConstruct
    private void initHeliumTenantService() {
        log.info("initHeliumTenantService initialization");
        this.heliumSetupCache = new ObjectCache<String, HeliumTenantSetup>("HeliumSetup", 5000) {
            @Override
            public void onCacheRemoval(String key, HeliumTenantSetup obj) {
                log.info("Removal of helium setup "+key+" previously updated");
            }
        };

        // search for default entry
        HeliumTenantSetup ts = heliumTenantSetupRepository.findOneHeliumTenantSetupByTenantUUID("default");
        if ( ts == null ) {
            ts = new HeliumTenantSetup();
            ts.setTenantUUID("default");
            ts.setDcBalanceStop(consoleConfig.getHeliumBillingDcBalanceStop());
            ts.setDcPer24BMessage(consoleConfig.getHeliumBillingDcPer24BytesMessage());
            ts.setDcPer24BDuplicate(consoleConfig.getHeliumBillingDcPer24BDuplicate());
            ts.setDcPer24BDownlink(consoleConfig.getHeliumBillingDcPer24BDownlink());
            ts.setDcPerDeviceInserted(consoleConfig.getHeliumBillingDcPerDeviceInserted());
            ts.setDcPerInactivityPeriod(consoleConfig.getHeliumBillingDcPerInactivityPeriod());
            ts.setInactivityBillingPeriodMs(consoleConfig.getHeliumBillingInactivityBillingPeriodMs());
            ts.setDcPerActivityPeriod(consoleConfig.getHeliumBillingDcPerActivityPeriod());
            ts.setActivityBillingPeriodMs(consoleConfig.getHeliumBillingActivityBillingPeriod());
            ts.setFreeTenantDc(consoleConfig.getHeliumBillingFreeTenantDc());
            ts.setMaxDcPerDevice(consoleConfig.getHeliumBillingMaxDcPerDevice());
            ts.setLimitDcRatePerDevice(consoleConfig.getHeliumBillingLimitDcRatePerDevice());
            ts.setLimitDcRatePeriodMs(consoleConfig.getHeliumBillingLimitDcRatePeriod());
            heliumTenantSetupRepository.save(ts);
        }
        this.heliumSetupCache.put(ts,ts.getTenantUUID());
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

    @Scheduled(fixedRateString = "${logging.cache.fixedrate}", initialDelay = 60_000)
    protected void cacheStatus() {
        if ( ! this.serviceEnable ) return;
        this.runningJobs++;
        try {
            this.heliumSetupCache.log();
        } finally {
            this.runningJobs--;
        }
    }

    // Get One element from cache, if failed, get it from DB and add it to cache
    protected HeliumTenantSetup getHeliumTenantSetup(String tenantUUID) {
        return getHeliumTenantSetup(tenantUUID,true,100);
    }

    // Get an element from cache, if failed, get it from DB, the tenant will be cached only if
    // we want to add in cache and cache is under the given limit
    protected HeliumTenantSetup getHeliumTenantSetup(String tenantUUID, boolean addInCache, int cacheLimit) {
        HeliumTenantSetup ts = heliumSetupCache.get(tenantUUID);
        if (ts == null) {
            ts = heliumTenantSetupRepository.findOneHeliumTenantSetupByTenantUUID(tenantUUID);
            if ( ts != null ) {
              if ( addInCache && heliumSetupCache.cacheUsage() <= cacheLimit ) {
                  heliumSetupCache.put(ts,tenantUUID);
              }
            } else {
                // create the tenant with the default setup
                HeliumTenantSetup def = heliumSetupCache.get("default");
                if ( def == null  ) {
                    def = heliumTenantSetupRepository.findOneHeliumTenantSetupByTenantUUID("default");
                    if ( def == null ) {
                        log.error("can't find default tenant settings");
                        return null;
                    }
                    heliumSetupCache.put(def,"default");

                }
                ts = new HeliumTenantSetup();
                ts.setTenantUUID(tenantUUID);
                ts.setDcBalanceStop(def.getDcBalanceStop());
                ts.setFreeTenantDc(def.getFreeTenantDc());
                ts.setDcPer24BMessage(def.getDcPer24BMessage());
                ts.setDcPer24BDuplicate(def.getDcPer24BDuplicate());
                ts.setDcPer24BDownlink(def.getDcPer24BDownlink());
                ts.setDcPerDeviceInserted(def.getDcPerDeviceInserted());
                ts.setDcPerInactivityPeriod(def.getDcPerInactivityPeriod());
                ts.setInactivityBillingPeriodMs(def.getInactivityBillingPeriodMs());
                ts.setDcPerActivityPeriod(def.getDcPerActivityPeriod());
                ts.setActivityBillingPeriodMs(def.getActivityBillingPeriodMs());
                ts.setMaxDcPerDevice(def.getMaxDcPerDevice());
                ts.setLimitDcRatePerDevice(def.getLimitDcRatePerDevice());
                ts.setLimitDcRatePeriodMs(def.getLimitDcRatePeriodMs());

                heliumTenantSetupRepository.save(ts);
                if ( addInCache && heliumSetupCache.cacheUsage() <= cacheLimit ) {
                    heliumSetupCache.put(ts,tenantUUID.toString());
                }
            }
        }
        return ts;
    }


    @Autowired
    private HeliumTenantRepository heliumTenantRepository;

    @Autowired
    private HeliumTenantSetupRepository heliumTenantSetupRepository;

    /**
     * Get a Helium Tenant and create one if not yet existing
     * @param tenantUUID
     * @return
     */
    protected HeliumTenant getHeliumTenant(String tenantUUID){
        HeliumTenant t = heliumTenantRepository.findOneHeliumTenantByTenantUUID(tenantUUID);
        if ( t == null ) {
            // create it
            HeliumTenantSetup ts = this.getHeliumTenantSetup(tenantUUID);
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

    protected ObjectMapper mapper;

    @PostConstruct
    private void initHeliumDeviceService() {
        mapper = new ObjectMapper();
        mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        mapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
        mapper.configure(SerializationFeature.FAIL_ON_SELF_REFERENCES, false);
    }

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
        HeliumTenantSetup ts = this.getHeliumTenantSetup(tenantUUID);
        if ( ts == null ) {
            HeliumTenant t = this.getHeliumTenant(tenantUUID);
            ts = this.getHeliumTenantSetup(tenantUUID);
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
        HeliumTenantSetup ts = this.getHeliumTenantSetup(tenantUUID);
        if ( ts == null ) {
            HeliumTenant t = this.getHeliumTenant(tenantUUID);
            ts = this.getHeliumTenantSetup(tenantUUID);
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

                HeliumTenantSetup ts = this.getHeliumTenantSetup(infos.getTenantId());
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
        HeliumTenantSetup ts = this.getHeliumTenantSetup(tenantUUID);
        if ( ts == null ) {
            HeliumTenant t = this.getHeliumTenant(tenantUUID);
            ts = this.getHeliumTenantSetup(tenantUUID);
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
        HeliumTenantSetup ts = this.getHeliumTenantSetup(tenantUUID);
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

}
