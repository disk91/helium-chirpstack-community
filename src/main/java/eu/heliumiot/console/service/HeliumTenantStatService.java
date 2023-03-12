package eu.heliumiot.console.service;

import eu.heliumiot.console.api.interfaces.TenantBasicStatRespItf;
import eu.heliumiot.console.jpa.db.HeliumDeviceStat;
import eu.heliumiot.console.jpa.db.HeliumTenantSetup;
import eu.heliumiot.console.jpa.db.Tenant;
import eu.heliumiot.console.jpa.repository.HeliumDeviceStatsRepository;
import eu.heliumiot.console.jpa.repository.TenantRepository;
import fr.ingeniousthings.tools.ITParseException;
import fr.ingeniousthings.tools.Now;
import fr.ingeniousthings.tools.ObjectCache;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.UUID;

@Service
public class HeliumTenantStatService {
    private final Logger log = LoggerFactory.getLogger(this.getClass());

    private ObjectCache<String, TenantBasicStatRespItf> tenantStatCache;
    @PostConstruct
    private void initHeliumTenantStatCacheService() {
        log.info("initHeliumTenantStatCacheService initialization");
        this.tenantStatCache = new ObjectCache<String, TenantBasicStatRespItf>("TenantStatCache", 5000) {
            @Override
            public void onCacheRemoval(String key, TenantBasicStatRespItf obj) {
                // nothing to do, readOnly
            }
        };
    }

    @Autowired
    protected HeliumTenantSetupService heliumTenantSetupService;

    @Autowired
    protected HeliumDeviceStatsRepository heliumDeviceStatsRepository;

    @Autowired
    protected TenantRepository tenantRepository;

    public TenantBasicStatRespItf getStatForTenantFromDate(String tenantUUID, long start, long duration)
    throws ITParseException {
        long _start = Now.NowUtcMs();
        // try from cache
        TenantBasicStatRespItf r = tenantStatCache.get(tenantUUID);
        if ( r != null ){
            if (r.getDay() == start && r.getDuration() == duration) {
                return r;
            } else {
                // need to recalculated
                tenantStatCache.remove(tenantUUID,true);
            }
        }

        // calculate stats
        log.debug("Basic Stats calculation for "+tenantUUID+" between "+start+" and "+(start+duration));

        // we need to recalculate / build
        r = new TenantBasicStatRespItf();

        // Get the tenant configuration
        HeliumTenantSetup hts = heliumTenantSetupService.getHeliumTenantSetup(tenantUUID);
        if ( hts == null ) throw new ITParseException();

        r.setTenantUUID(hts.getTenantUUID());
        r.setDcBalanceStop(hts.getDcBalanceStop());
        r.setFreeTenantDc(hts.getFreeTenantDc());
        r.setDcPer24BMessage(hts.getDcPer24BMessage());
        r.setDcPer24BDuplicate(hts.getDcPer24BDuplicate());
        r.setDcPer24BDownlink(hts.getDcPer24BDownlink());
        r.setDcPerDeviceInserted(hts.getDcPerDeviceInserted());
        r.setDcPerInactivityPeriod(hts.getDcPerInactivityPeriod());
        r.setInactivityBillingPeriodMs(hts.getInactivityBillingPeriodMs());
        r.setDcPerActivityPeriod(hts.getDcPerActivityPeriod());
        r.setActivityBillingPeriodMs(hts.getActivityBillingPeriodMs());
        r.setMaxDcPerDevice(hts.getMaxDcPerDevice());
        r.setLimitDcRatePerDevice(hts.getLimitDcRatePerDevice());
        r.setLimitDcRatePeriodMs(hts.getLimitDcRatePeriodMs());
        r.setMaxOwnedTenants(hts.getMaxOwnedTenants());
        r.setMaxDevices(hts.getMaxDevices());
        r.setDcPrice(hts.getDcPrice());
        r.setDcMin(hts.getDcMin());
        r.setMaxCopy(hts.getMaxCopy());

        // get name
        Tenant t = tenantRepository.findOneTenantById(UUID.fromString(tenantUUID));
        if ( t == null ) r.setTenantName("unknown");
        else r.setTenantName(t.getName());

        // get the usage stat
        boolean success = false;
        HeliumDeviceStat s = null;
        try {
            s = heliumDeviceStatsRepository.findSumStatForTenantBetween(
                    tenantUUID,
                    start,
                    (start + duration)
            );
            success = true;
        } catch (Exception x) {
            // We have an exception when no value match the SUM on the period
            // forget this
        }
        if ( !success || s == null ) {
            r.setRegistrationDc(0);
            r.setJoinReq(0);
            r.setUplink(0);
            r.setUplinkDc(0);
            r.setDuplicate(0);
            r.setDuplicateDc(0);
            r.setDownlink(0);
            r.setDownlinkDc(0);
            r.setActivityDc(0);
            r.setInactivityDc(0);
        } else {
            r.setRegistrationDc(s.getRegistrationDc());
            r.setJoinReq(s.getJoinReq());
            r.setUplink(s.getUplink());
            r.setUplinkDc(s.getUplinkDc());
            r.setDuplicate(s.getDuplicate());
            r.setDuplicateDc(s.getDuplicateDc());
            r.setDownlink(s.getDownlink());
            r.setDownlinkDc(s.getDownlinkDc());
            r.setActivityDc(s.getActivityDc());
            r.setInactivityDc(s.getInactivityDc());
        }

        tenantStatCache.put(r,tenantUUID);

        long _duration = Now.NowUtcMs() - _start;
        log.debug("Basic stat calculation duration "+_duration+"ms");
        if ( _duration > 2000 ) {
            log.warn("Basic stat calculation duration "+_duration+"ms");
        }
        return r;
    }


}
