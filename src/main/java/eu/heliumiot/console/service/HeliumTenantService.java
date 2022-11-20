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

import eu.heliumiot.console.ConsoleConfig;
import eu.heliumiot.console.jpa.db.HeliumTenant;
import eu.heliumiot.console.jpa.db.HeliumTenantSetup;
import eu.heliumiot.console.mqtt.api.HeliumDeviceStatItf;
import eu.heliumiot.console.jpa.repository.HeliumTenantRepository;
import eu.heliumiot.console.jpa.repository.HeliumTenantSetupRepository;
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

    private ObjectCache<String,HeliumTenantSetup> heliumSetupCache;
    @PostConstruct
    private void initHeliumTenantService() {
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
    }

    public void stopHeliumTenantService() {
        log.info("Stopping HeliumTenantService");
    }

    @Scheduled(fixedRateString = "${logging.cache.fixedrate}", initialDelay = 60_000)
    protected void cacheStatus() {
        this.heliumSetupCache.log();
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
            return heliumTenantRepository.save(t);
        } else return t;
    }

    protected void flushHeliumTenant(HeliumTenant t) {
        heliumTenantRepository.save(t);
    }

    public HeliumDeviceStatItf processUplink(String tenantUUID, String deviceUUID,  int payloadSize, int duplicates) {
        log.info("Payload Size "+payloadSize);
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
                return i;
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
                i.setEmpty(false);

                // check deactivation
                processBalance(ts,t);
            }
        }

        log.debug("Process UPLINK in "+(Now.NowUtcMs()-start)+"ms");
        return i;
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
                    processBalance(ts, t);
                }
            }
        }
        log.debug("Process INSERT ACTIVITY INACTIVITY in "+(Now.NowUtcMs()-start)+"ms");
    }

    public HeliumDeviceStatItf processJoin(String tenantUUID, String deviceUUID) {
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
                return i;
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
                i.setEmpty(false);

                // check deactivation
                processBalance(ts,t);
            }
        }
        log.debug("Process JOIN in "+(Now.NowUtcMs()-start)+"ms");
        return i;
    }

    /**
     * Process the tenant balance and return false when the balance has reached a minimum
     * value and devices needs to be blocked.
     * ?? Peut etre le mettre au niveau du tenant ce flag pour un processing plus global
     * @return
     */
    protected boolean processBalance(HeliumTenantSetup ts, HeliumTenant t) {
        // @ TODO faire la gestion de la desactivation pour manque de DCs
        // action on update the balance

        // call deactivation with state OUTOFDCS when needed

        // on fait un appel asynchrone qui met ca a zero
        // meme file que la reactivation comme ca on traite en sequence

        if ( t.getDcBalance() < ts.getDcBalanceStop() ) {
            // manage deactivation
            return false;
        } else {
            return true;
        }

    }


}
