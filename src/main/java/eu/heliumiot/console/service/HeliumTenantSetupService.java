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
import eu.heliumiot.console.api.interfaces.*;
import eu.heliumiot.console.jpa.db.HeliumCoupon;
import eu.heliumiot.console.jpa.db.HeliumTenantSetup;
import eu.heliumiot.console.jpa.repository.HeliumCouponRepository;
import eu.heliumiot.console.jpa.repository.HeliumTenantSetupRepository;
import fr.ingeniousthings.tools.*;
import io.micrometer.core.instrument.Gauge;
import io.micrometer.core.instrument.MeterRegistry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

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

    @Autowired
    private NovaService novaService;

    private ObjectCache<String,HeliumTenantSetup> heliumSetupCache;
    @PostConstruct
    private void initHeliumTenantSetupService() {
        log.info("initHeliumTenantSetupService initialization");
        this.heliumSetupCache = new ObjectCache<String, HeliumTenantSetup>("HeliumSetup", 5000) {
            @Override
            public void onCacheRemoval(String key, HeliumTenantSetup obj, boolean batch, boolean last) {
                log.info("Removal of helium setup "+key+" previously updated");
            }

            @Override
            public void bulkCacheUpdate(List<HeliumTenantSetup> objects) {

            }

        };

        // Update the entries after migration 0.9 if value is -2 for dc_per_join_request and others
        Pageable page = PageRequest.of(0,100);
        Slice<HeliumTenantSetup> alltenants = heliumTenantSetupRepository.findHeliumTenantSetupByDcPerJoinRequest(-2,page);
        boolean nextPage;
        if ( alltenants != null && alltenants.getNumberOfElements() > 0 ) {
            log.info("### [db V2_0_9] Migrate the HeliumTenantSetup with join values");
            int i = 0;
            do {
                for (HeliumTenantSetup t : alltenants.getContent()) {
                    t.setMaxJoinRequestDup(consoleConfig.getHeliumBillingMaxJoinRequestDup());
                    t.setDcPerJoinRequest(consoleConfig.getHeliumBillingDcPerJoinRequest());
                    t.setDcPerJoinAccept(consoleConfig.getHeliumBillingDcPerJoinAccept());
                    heliumTenantSetupRepository.save(t);
                    i++;
                }
                if (alltenants.hasNext()) {
                    nextPage = true;
                    // As the entries have been updated we stay on page 0...
                    alltenants = heliumTenantSetupRepository.findHeliumTenantSetupByDcPerJoinRequest(-2, page);
                } else nextPage = false;
            } while (nextPage);
            log.info("### [db V2_0_9] "+i+" Done");
        }

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
            ts.setMaxCopy(consoleConfig.getHeliumRouteDefaultCopy());
            ts.setDcPerJoinRequest(consoleConfig.getHeliumBillingDcPerJoinRequest());
            ts.setDcPerJoinAccept(consoleConfig.getHeliumBillingDcPerJoinAccept());
            ts.setMaxJoinRequestDup(consoleConfig.getHeliumBillingMaxJoinRequestDup());
            ts.setRouteId("N/A");
            ts.setTemplate(true);
            heliumTenantSetupRepository.save(ts);
        }
        this.heliumSetupCache.put(ts,ts.getTenantUUID());
        runningJobs=0;
        serviceEnable=true;

        Gauge.builder("cons.tenant.setup.cache_total_time", this.heliumSetupCache.getTotalCacheTime())
                .description("total time tenant setup cache execution")
                .register(registry);
        Gauge.builder("cons.tenant.setup.cache_total", this.heliumSetupCache.getTotalCacheTry())
                .description("total tenant setup cache try")
                .register(registry);
        Gauge.builder("cons.tenant.setup.cache_miss", this.heliumSetupCache.getCacheMissStat())
                .description("total tenant setup cache miss")
                .register(registry);

    }


    private MeterRegistry registry;
    public HeliumTenantSetupService(MeterRegistry registry) {
        this.registry = registry;
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

    // Search but not create if not exists
    protected HeliumTenantSetup getHeliumTenantSetup(String tenantUUID, boolean create) {
        return getHeliumTenantSetup(tenantUUID,create,true,100);
    }

    protected HeliumTenantSetup getHeliumTenantSetup(String tenantUUID, boolean addInCache, int cacheLimit) {
        return getHeliumTenantSetup(tenantUUID,true,addInCache,cacheLimit);
    }


    // Get an element from cache, if failed, get it from DB, the tenant will be cached only if
    // we want to add in cache and cache is under the given limit
    protected HeliumTenantSetup getHeliumTenantSetup(String tenantUUID, boolean create, boolean addInCache, int cacheLimit) {
        try {
            if (tenantUUID.compareToIgnoreCase("create") == 0) {
                log.error("### Create found 4 ");
                throw new Exception();
            }
        } catch (Exception x) {
            x.printStackTrace();
            return null;
        }

        HeliumTenantSetup ts = heliumSetupCache.get(tenantUUID);
        if (ts == null) {
            ts = heliumTenantSetupRepository.findOneHeliumTenantSetupByTenantUUID(tenantUUID);
            if ( ts != null ) {
              if ( addInCache && heliumSetupCache.cacheUsage() <= cacheLimit ) {
                  heliumSetupCache.put(ts,tenantUUID);
              }
            } else {
                if ( create ) {
                    // create the tenant with the default setup
                    HeliumTenantSetup def = heliumSetupCache.get(HELIUM_TENANT_SETUP_DEFAULT);
                    if (def == null) {
                        def = heliumTenantSetupRepository.findOneHeliumTenantSetupByTenantUUID(HELIUM_TENANT_SETUP_DEFAULT);
                        if (def == null) {
                            log.error("can't find default tenant settings");
                            return null;
                        }
                        heliumSetupCache.put(def, HELIUM_TENANT_SETUP_DEFAULT);

                    }
                    ts = this.createAndSave(def, tenantUUID);
                    if (addInCache && heliumSetupCache.cacheUsage() <= cacheLimit) {
                        heliumSetupCache.put(ts, tenantUUID.toString());
                    }
                } else {
                    return null;
                }
            }
        }
        return ts;
    }

    public void flushTenantSetup(HeliumTenantSetup ts) {
        if ( ts.getTenantUUID().compareToIgnoreCase("create") == 0 ) {
            log.error("### Create found 1 ");
        } else {
            heliumTenantSetupRepository.save(ts);
        }
        this.heliumSetupCache.remove(ts.getTenantUUID(),false);
    }

    /**
     * create a new Tenant Setup for a new tenant
     */
    public HeliumTenantSetup createAndSave(HeliumTenantSetup def, String tenantUUID) {
        try {
            if (tenantUUID.compareToIgnoreCase("create") == 0) {
                log.error("### Create found 2 ");
                throw new Exception();
            }
        } catch (Exception x) {
            x.printStackTrace();
            return null;
        }

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
        ts.setMaxCopy(def.getMaxCopy());
        ts.setRouteId(null);
        ts.setTemplate(false);
        ts.setDcPerJoinRequest(def.getDcPerJoinRequest());
        ts.setDcPerJoinAccept(def.getDcPerJoinAccept());
        ts.setMaxJoinRequestDup(def.getMaxJoinRequestDup());
        heliumTenantSetupRepository.save(ts);
        return ts;
    }


    // ===============================================
    // API
    // ===============================================

    @Autowired
    protected UserCacheService userCacheService;


    public List<TenantSetupTemplateListRespItf> getTenantSetupTemplates (
            String user
    ) throws ITRightException {
        UserCacheService.UserCacheElement u = userCacheService.getUserById(user);
        if ( u == null || ! u.user.isAdmin() ) throw new ITRightException();

        List<HeliumTenantSetup>  templates = heliumTenantSetupRepository.findHeliumTenantSetupByTemplate(true);
        ArrayList<TenantSetupTemplateListRespItf> r = new ArrayList<>();

        for ( HeliumTenantSetup def : templates) {
            TenantSetupTemplateListRespItf ts = new TenantSetupTemplateListRespItf();
            ts.setId(def.getId());
            ts.setTenantUUID(def.getTenantUUID());
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
            ts.setMaxCopy(def.getMaxCopy());
            ts.setDcPerJoinRequest(def.getDcPerJoinRequest());
            ts.setDcPerJoinAccept(def.getDcPerJoinAccept());
            ts.setMaxJoinRequestDup(def.getMaxJoinRequestDup());
            r.add(ts);
        }

        return r;
    }


    public TenantSetupTemplateListRespItf getOneTenantSetup (
        String user,
        String tenantUUID
    ) throws ITRightException, ITNotFoundException {
        UserCacheService.UserCacheElement u = userCacheService.getUserById(user);
        if ( u == null || ! u.user.isAdmin() ) throw new ITRightException();

        HeliumTenantSetup def = heliumTenantSetupRepository.findOneHeliumTenantSetupByTenantUUID(tenantUUID);
        if ( def == null ) throw new ITNotFoundException();

        TenantSetupTemplateListRespItf ts = new TenantSetupTemplateListRespItf();
        ts.setId(def.getId());
        ts.setTenantUUID(def.getTenantUUID());
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
        ts.setMaxCopy(def.getMaxCopy());
        ts.setDcPerJoinRequest(def.getDcPerJoinRequest());
        ts.setDcPerJoinAccept(def.getDcPerJoinAccept());
        ts.setMaxJoinRequestDup(def.getMaxJoinRequestDup());
        return ts;
    }


    /**
     * Update an existing tenant template, should also work for any tenant Setup
     * @param user
     * @param def
     * @throws ITRightException
     */
    public  void updateTenantSetupTemplates (
            String user,
            TenantSetupTemplateUpdateReqItf def
    ) throws ITRightException {
        UserCacheService.UserCacheElement u = userCacheService.getUserById(user);
        if ( u == null || ! u.user.isAdmin() ) throw new ITRightException();

        HeliumTenantSetup ts = this.getHeliumTenantSetup(def.getTenantUUID(),false,false,100);
        if ( ts == null ) throw new ITRightException();

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
        ts.setMaxCopy(def.getMaxCopy());
        ts.setDcPerJoinRequest(def.getDcPerJoinRequest());
        ts.setDcPerJoinAccept(def.getDcPerJoinAccept());
        ts.setMaxJoinRequestDup(def.getMaxJoinRequestDup());

        heliumTenantSetupRepository.save(ts);
        this.heliumSetupCache.remove(ts.getTenantUUID(),false);
    }

    /**
     * Create a new tenant template, should also work for any tenant Setup
     * @param user
     * @param def
     * @throws ITRightException
     */
    public void createTenantSetupTemplates (
            String user,
            TenantSetupTemplateCreateReqItf def
    ) throws ITRightException {
        UserCacheService.UserCacheElement u = userCacheService.getUserById(user);
        if ( u == null || ! u.user.isAdmin() ) throw new ITRightException();

        HeliumTenantSetup ts = heliumTenantSetupRepository.findOneHeliumTenantSetupByTenantUUID(def.getTenantUUID());
        if ( ts != null ) throw new ITRightException();

        ts = new HeliumTenantSetup();
        if ( def.getTenantUUID().length() < 30 ) {
            ts.setTemplate(true);
            ts.setRouteId("N/A");
        } else {
            ts.setTemplate(false);
            ts.setRouteId(null);
        }

        try {
            if (def.getTenantUUID().compareToIgnoreCase("create") == 0) {
                log.error("### Create found 5 ");
                throw new Exception();
            }
        } catch (Exception x) {
            x.printStackTrace();
            throw new ITRightException();
        }

        ts.setTenantUUID(def.getTenantUUID());
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
        ts.setMaxCopy(( (def.getMaxCopy() > 0 )? def.getMaxCopy() : 1));
        ts.setDcPerJoinRequest(def.getDcPerJoinRequest());
        ts.setDcPerJoinAccept(def.getDcPerJoinAccept());
        ts.setMaxJoinRequestDup(def.getMaxJoinRequestDup());
        heliumTenantSetupRepository.save(ts);
    }

    public void deleteTenantSetupTemplate (
            String user,
            String tenantSetupId
    ) throws ITRightException {
        UserCacheService.UserCacheElement u = userCacheService.getUserById(user);
        if (u == null || !u.user.isAdmin()) throw new ITRightException();

        // exists ?
        UUID id = UUID.fromString(tenantSetupId);
        HeliumTenantSetup ts = heliumTenantSetupRepository.findOneHeliumTenantSetupById(id);
        if (ts == null) throw new ITRightException();

        // can't delete default
        if ( ts.getTenantUUID().compareToIgnoreCase(HELIUM_TENANT_SETUP_DEFAULT) == 0 ) {
            throw new ITRightException();
        }

        this.deleteTenantSetupTemplateUnsecured(ts);
    }

    public void deleteTenantSetupTemplateUnsecured (
            HeliumTenantSetup ts
    ) {
        if ( ! ts.isTemplate() && ts.getRouteId() != null ) {
            // we need to delete the associated route

            novaService.addDelayedRouteRemoval(ts.getRouteId());
        }
        this.heliumSetupCache.remove(ts.getTenantUUID(),false);
        heliumTenantSetupRepository.delete(ts);
    }

    // =======================================================================
    // MANAGE COUPONS
    // =======================================================================

    @Autowired
    protected HeliumCouponRepository heliumCouponRepository;

    public List<TenantSetupTemplateCouponRespItf> createTenantSetupTemplatesCoupon(
            String user,
            TenantSetupTemplateCouponReqItf req
    ) throws ITRightException, ITParseException {

        long now = Now.NowUtcMs();

        UserCacheService.UserCacheElement u = userCacheService.getUserById(user);
        if ( u == null || ! u.user.isAdmin() ) throw new ITRightException();

        HeliumTenantSetup ts = heliumTenantSetupRepository.findOneHeliumTenantSetupByTenantUUID(req.getTenantUUID());
        if ( ts == null ) throw new ITRightException();
        if ( ! ts.isTemplate() ) throw new ITRightException();
        if ( ts.getTenantUUID().compareTo(HELIUM_TENANT_SETUP_DEFAULT) == 0 ) throw new ITRightException();

        if ( req.getToCreate() > 50 || (req.getStop() > 0 && req.getStop() < now) ) throw new ITParseException();

        ArrayList<TenantSetupTemplateCouponRespItf> ret = new ArrayList<>();
        if ( req.getPrefix().length() > 16 ) req.setPrefix(req.getPrefix().substring(0,16));
        for ( int i = 0 ; i < req.getToCreate() ; i++ ) {
            String couponId = req.getPrefix() + "-" + RandomString.getRandomAZString(6) + "-" + RandomString.getRandomHexString(6);
            HeliumCoupon c = new HeliumCoupon();
            c.setCouponState(HeliumCoupon.CouponState.ACTIVE);
            c.setInUse(0);
            c.setCouponFor(req.getCouponFor());
            c.setMaxUse(req.getMaxUse());
            c.setStart(req.getStart());
            c.setStop(req.getStop());
            c.setTenantUUID(ts.getTenantUUID());
            c.setCouponID(couponId);
            heliumCouponRepository.save(c);
            TenantSetupTemplateCouponRespItf _r = new TenantSetupTemplateCouponRespItf();
            _r.setCouponID(c.getCouponID());
            _r.setTenantUUID(c.getTenantUUID());
            ret.add(_r);
        }
        return ret;
    }

    public List<CouponListRespItf> listTenantSetupTemplatesCoupon(
            String user,
            boolean filterInactive
    ) throws ITRightException {

        long now = Now.NowUtcMs();

        UserCacheService.UserCacheElement u = userCacheService.getUserById(user);
        if (u == null || !u.user.isAdmin()) throw new ITRightException();

        ArrayList<CouponListRespItf> ret = new ArrayList<>();
        heliumCouponRepository.findAll().forEach(coupon -> {
            if (
                 ! filterInactive ||
                 (
                       coupon.getCouponState() == HeliumCoupon.CouponState.ACTIVE
                    && (coupon.getStart() == 0 || coupon.getStart() < now )
                    && (coupon.getStop() == 0 || coupon.getStop() > now )
                    && coupon.getInUse() < coupon.getMaxUse()
                 )
            ) {
                CouponListRespItf _r = new CouponListRespItf();
                _r.setCouponID(coupon.getCouponID());
                _r.setTenantUUID(coupon.getTenantUUID());
                _r.setInUse(coupon.getInUse());
                _r.setMaxUse(coupon.getMaxUse());
                _r.setStop(coupon.getStop());
                _r.setStart(coupon.getStart());
                _r.setCouponFor(coupon.getCouponFor());
                ret.add(_r);
            }
        });
        return ret;
    }


    public synchronized String acquiresCoupon(String couponId) {

        // check if exist & valid
        HeliumCoupon c = heliumCouponRepository.findOneHeliumCouponByCouponID(couponId);
        if ( c == null || c.getCouponState() == HeliumCoupon.CouponState.CLEARED ) return null;

        // check if in the period of availability
        long now = Now.NowUtcMs();
        if ( ( c.getStart() > 0 && c.getStart() > now ) || ( c.getStop() > 0 && c.getStop() < now )  ) return null;

        // Get the coupon
        c.setInUse(c.getInUse()+1);
        if ( c.getInUse() >= c.getMaxUse() ) c.setCouponState(HeliumCoupon.CouponState.CLEARED);
        heliumCouponRepository.save(c);
        return c.getTenantUUID();

    }


}
