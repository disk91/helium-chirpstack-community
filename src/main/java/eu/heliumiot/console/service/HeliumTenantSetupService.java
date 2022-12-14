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
import eu.heliumiot.console.jpa.db.HeliumTenantSetup;
import eu.heliumiot.console.jpa.repository.HeliumTenantSetupRepository;
import fr.ingeniousthings.tools.ObjectCache;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;

@Service
public class HeliumTenantSetupService {
    private final Logger log = LoggerFactory.getLogger(this.getClass());

    public final static String HELIUM_TENANT_SETUP_DEFAULT = "default";

    @Autowired
    protected ConsoleConfig consoleConfig;

    protected int runningJobs;
    protected boolean serviceEnable; // false to stop the services

    @Autowired
    private HeliumTenantSetupRepository heliumTenantSetupRepository;

    private ObjectCache<String,HeliumTenantSetup> heliumSetupCache;
    @PostConstruct
    private void initHeliumTenantSetupService() {
        log.info("initHeliumTenantSetupService initialization");
        this.heliumSetupCache = new ObjectCache<String, HeliumTenantSetup>("HeliumSetup", 5000) {
            @Override
            public void onCacheRemoval(String key, HeliumTenantSetup obj) {
                log.info("Removal of helium setup "+key+" previously updated");
            }
        };

        // search for default entry
        HeliumTenantSetup ts = heliumTenantSetupRepository.findOneHeliumTenantSetupByTenantUUID(HELIUM_TENANT_SETUP_DEFAULT);
        if ( ts == null ) {
            ts = new HeliumTenantSetup();
            ts.setTenantUUID(HELIUM_TENANT_SETUP_DEFAULT);
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
            ts.setMaxDevices(consoleConfig.getHeliumBillingMaxDevices());
            ts.setMaxOwnedTenants(consoleConfig.getHeliumBillingMaxTenant());
            ts.setDcPrice(consoleConfig.getHeliumBillingDcPrice());
            ts.setDcMin(consoleConfig.getHeliumBillingDcMinAmount());
            ts.setSignupAllowed(consoleConfig.isHeliumAllowsSignup());
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
                HeliumTenantSetup def = heliumSetupCache.get(HELIUM_TENANT_SETUP_DEFAULT);
                if ( def == null  ) {
                    def = heliumTenantSetupRepository.findOneHeliumTenantSetupByTenantUUID(HELIUM_TENANT_SETUP_DEFAULT);
                    if ( def == null ) {
                        log.error("can't find default tenant settings");
                        return null;
                    }
                    heliumSetupCache.put(def,HELIUM_TENANT_SETUP_DEFAULT);

                }
                ts = this.createAndSave(def,tenantUUID);
                if ( addInCache && heliumSetupCache.cacheUsage() <= cacheLimit ) {
                    heliumSetupCache.put(ts,tenantUUID.toString());
                }
            }
        }
        return ts;
    }

    /**
     * create a new Tenant Setup for a new tenant
     */
    public HeliumTenantSetup createAndSave(HeliumTenantSetup def, String tenantUUID) {
        HeliumTenantSetup ts = new HeliumTenantSetup();
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
        ts.setMaxOwnedTenants(def.getMaxOwnedTenants());
        ts.setMaxDevices(def.getMaxDevices());
        ts.setDcPrice(def.getDcPrice());
        ts.setDcMin(def.getDcMin());
        ts.setSignupAllowed(def.isSignupAllowed());
        heliumTenantSetupRepository.save(ts);
        return ts;
    }



}
