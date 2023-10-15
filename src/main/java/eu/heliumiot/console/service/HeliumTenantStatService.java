package eu.heliumiot.console.service;

import eu.heliumiot.console.api.interfaces.TenantBasicStatRespItf;
import eu.heliumiot.console.api.interfaces.TenantSetupStatsRespItf;
import eu.heliumiot.console.api.interfaces.TenantSetupStatsSerie;
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
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;
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
            public void onCacheRemoval(String key, TenantBasicStatRespItf obj, boolean batch, boolean last) {
                // nothing to do, readOnly
            }

            @Override
            public void bulkCacheUpdate(List<TenantBasicStatRespItf> objects) {

            }

        };
    }


    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Scheduled(fixedRate = 86_400_000, initialDelay = 1_080_000) // every 24h / first after 18m
    protected void clean_stat_tables() {
        // on every day, move the data to history after 1 month
        // clear the history data over a year
        // this is based on stored procedure
        long start = Now.NowUtcMs();
        try {
            jdbcTemplate.execute("CALL move_helium_device_stats_history()");
            log.info("clean_stat_tables - move_helium_device_stats_history duration " + (Now.NowUtcMs() - start) / 1000 + "s");
            start = Now.NowUtcMs();

            jdbcTemplate.execute("CALL delete_helium_device_stats_history()");
            log.info("clean_stat_tables - delete_helium_device_stats_history duration " + (Now.NowUtcMs() - start) / 1000 + "s");
        } catch (Exception x) {
            log.error("clean_stat_tables - error ("+x.getMessage()+") - after "+ (Now.NowUtcMs() - start) / 1000 + "s");
        }
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
        HeliumTenantSetup hts = heliumTenantSetupService.getHeliumTenantSetup(tenantUUID,false);
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
        r.setDcPerJoinRequest(hts.getDcPerJoinRequest());
        r.setDcPerJoinAccept(hts.getDcPerJoinAccept());
        r.setMaxJoinRequestDup(hts.getMaxJoinRequestDup());

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
            r.setJoinDc(0);
            r.setJoinAcceptDc(0);
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
            r.setJoinDc(s.getJoinDc());
            r.setJoinAcceptDc(s.getJoinAcceptDc());
        }

        tenantStatCache.put(r,tenantUUID);

        long _duration = Now.NowUtcMs() - _start;
        log.debug("Basic stat calculation duration "+_duration+"ms");
        if ( _duration > 2000 ) {
            log.warn("Basic stat calculation duration "+_duration+"ms");
        }
        return r;
    }

    public TenantSetupStatsRespItf getTenantStatsForChart(String tenantUUID, long start, long duration)
    throws ITParseException {
        long _start = Now.NowUtcMs();

        // calculate stats
        log.debug("Tenant Stats calculation for "+tenantUUID+" between "+start+" and "+(start+duration));

        // get the usage stat
        boolean success = false;
        List<HeliumDeviceStat> ss = null;
        try {
            ss = heliumDeviceStatsRepository.findSumStatForTenantByDayBetween(
                tenantUUID,
                start,
                (start + duration)
            );
            success = true;
        } catch (Exception x) {
            // We have an exception when no value match the SUM on the period
            // forget this
            log.debug("Failed to get Tenant stats calculation "+x.getMessage());
        }
        if ( !success ) {
            throw new ITParseException();
        } else {
            TenantSetupStatsRespItf r = new TenantSetupStatsRespItf();
            r.setDateLabel(new ArrayList<>());
            r.setSeries(new ArrayList<>());
            TenantSetupStatsSerie sUplink = new TenantSetupStatsSerie();
            sUplink.setName("uplink");
            sUplink.setData(new ArrayList<>());
            TenantSetupStatsSerie sCopies = new TenantSetupStatsSerie();
            sCopies.setName("up_copy");
            sCopies.setData(new ArrayList<>());
            TenantSetupStatsSerie sDownlink = new TenantSetupStatsSerie();
            sDownlink.setName("downlink");
            sDownlink.setData(new ArrayList<>());
            TenantSetupStatsSerie sJoin = new TenantSetupStatsSerie();
            sJoin.setName("join");
            sJoin.setData(new ArrayList<>());
            r.getSeries().add(sUplink);
            r.getSeries().add(sCopies);
            r.getSeries().add(sDownlink);
            r.getSeries().add(sJoin);
            for ( HeliumDeviceStat s : ss ) {
                r.getDateLabel().add(Now.formatToYYYYMMDDUtc(s.getDay()));
                sUplink.getData().add(s.getUplink());
                sCopies.getData().add(s.getDuplicate());
                sDownlink.getData().add(s.getDownlink());
                sJoin.getData().add(s.getJoinReq());
            }
            return r;
        }
    }

    public TenantSetupStatsRespItf getTenantDeviceStatsForChart(String tenantUUID, long start, long duration, int maxDevices)
        throws ITParseException {
        long _start = Now.NowUtcMs();

        // calculate stats
        log.debug("Tenant Device Stats calculation for "+tenantUUID+" between "+start+" and "+(start+duration));

        // get the usage stat
        boolean success = false;
        List<HeliumDeviceStat> ss = null;
        try {
            ss = heliumDeviceStatsRepository.findSumStatForTenantDeviceByDayBetween(
                tenantUUID,
                start,
                (start + duration),
                maxDevices
            );
            success = true;
        } catch (Exception x) {
            // We have an exception when no value match the SUM on the period
            // forget this
            log.debug("Failed to get Tenant Device stats calculation "+x.getMessage());
        }
        if ( !success ) {
            throw new ITParseException();
        } else {
            TenantSetupStatsRespItf r = new TenantSetupStatsRespItf();
            r.setDateLabel(new ArrayList<>());
            r.setSeries(new ArrayList<>());
            TenantSetupStatsSerie sUplink = new TenantSetupStatsSerie();
            sUplink.setName("uplink");
            sUplink.setData(new ArrayList<>());
            TenantSetupStatsSerie sCopies = new TenantSetupStatsSerie();
            sCopies.setName("up_copy");
            sCopies.setData(new ArrayList<>());
            TenantSetupStatsSerie sDownlink = new TenantSetupStatsSerie();
            sDownlink.setName("downlink");
            sDownlink.setData(new ArrayList<>());
            TenantSetupStatsSerie sJoin = new TenantSetupStatsSerie();
            sJoin.setName("join");
            sJoin.setData(new ArrayList<>());
            r.getSeries().add(sUplink);
            r.getSeries().add(sCopies);
            r.getSeries().add(sDownlink);
            r.getSeries().add(sJoin);
            for ( HeliumDeviceStat s : ss ) {
                //log.info("device:"+s.getTenantUUID()+" uplink: "+s.getUplink());
                r.getDateLabel().add(s.getDeviceUUID());
                sUplink.getData().add(s.getUplink());
                sCopies.getData().add(s.getDuplicate());
                sDownlink.getData().add(s.getDownlink());
                sJoin.getData().add(s.getJoinReq());
            }
            return r;
        }
    }

    public TenantSetupStatsRespItf getTenantInactiveDeviceStatsForChart(String tenantUUID, long start, long duration, int maxDevices)
        throws ITParseException {
        long _start = Now.NowUtcMs();

        // calculate stats
        log.debug("Tenant Inactive Device Stats calculation for "+tenantUUID+" between "+start+" and "+(start+duration));

        // get the usage stat
        boolean success = false;
        List<HeliumDeviceStat> ss = null;
        try {
            ss = heliumDeviceStatsRepository.findSumStatForTenantInactiveDeviceByDayBetween(
                tenantUUID,
                start,
                (start + duration),
                maxDevices
            );
            success = true;
        } catch (Exception x) {
            // We have an exception when no value match the SUM on the period
            // forget this
            log.debug("Failed to get Tenant Inactiv Device stats calculation "+x.getMessage());
        }
        if ( !success ) {
            throw new ITParseException();
        } else {
            TenantSetupStatsRespItf r = new TenantSetupStatsRespItf();
            r.setDateLabel(new ArrayList<>());
            r.setSeries(new ArrayList<>());
            TenantSetupStatsSerie sUplink = new TenantSetupStatsSerie();
            sUplink.setName("uplink");
            sUplink.setData(new ArrayList<>());
            TenantSetupStatsSerie sCopies = new TenantSetupStatsSerie();
            sCopies.setName("up_copy");
            sCopies.setData(new ArrayList<>());
            TenantSetupStatsSerie sDownlink = new TenantSetupStatsSerie();
            sDownlink.setName("downlink");
            sDownlink.setData(new ArrayList<>());
            TenantSetupStatsSerie sJoin = new TenantSetupStatsSerie();
            sJoin.setName("join");
            sJoin.setData(new ArrayList<>());
            TenantSetupStatsSerie sInactivity = new TenantSetupStatsSerie();
            sInactivity.setName("inactivity");
            sInactivity.setData(new ArrayList<>());
            r.getSeries().add(sUplink);
            r.getSeries().add(sCopies);
            r.getSeries().add(sDownlink);
            r.getSeries().add(sJoin);
            r.getSeries().add(sInactivity);
            for ( HeliumDeviceStat s : ss ) {
                //log.info("device:"+s.getTenantUUID()+" uplink: "+s.getUplink());
                r.getDateLabel().add(s.getDeviceUUID());
                sUplink.getData().add(s.getUplink());
                sCopies.getData().add(s.getDuplicate());
                sDownlink.getData().add(s.getDownlink());
                sJoin.getData().add(s.getJoinReq());
                sInactivity.getData().add(s.getInactivityDc());
            }
            return r;
        }
    }

    public TenantSetupStatsRespItf getTopConsumerStatsForChart(long start, long duration, int maxDevices)
        throws ITParseException {
        long _start = Now.NowUtcMs();

        // calculate stats
        log.debug("Top consumer Stats calculation for between "+start+" and "+(start+duration));

        // get the usage stat
        boolean success = false;
        List<HeliumDeviceStat> ss = null;
        try {
            ss = heliumDeviceStatsRepository.findSumStatByDayBetween(
                start,
                (start + duration),
                maxDevices
            );
            success = true;
        } catch (Exception x) {
            // We have an exception when no value match the SUM on the period
            // forget this
            log.debug("Failed to get top consumer stats calculation "+x.getMessage());
        }
        if ( !success ) {
            throw new ITParseException();
        } else {
            TenantSetupStatsRespItf r = new TenantSetupStatsRespItf();
            r.setDateLabel(new ArrayList<>());
            r.setSeries(new ArrayList<>());
            TenantSetupStatsSerie sUplink = new TenantSetupStatsSerie();
            sUplink.setName("uplink");
            sUplink.setData(new ArrayList<>());
            TenantSetupStatsSerie sCopies = new TenantSetupStatsSerie();
            sCopies.setName("up_copy");
            sCopies.setData(new ArrayList<>());
            TenantSetupStatsSerie sDownlink = new TenantSetupStatsSerie();
            sDownlink.setName("downlink");
            sDownlink.setData(new ArrayList<>());
            TenantSetupStatsSerie sJoin = new TenantSetupStatsSerie();
            sJoin.setName("join");
            sJoin.setData(new ArrayList<>());
            TenantSetupStatsSerie sInactivity = new TenantSetupStatsSerie();
            sInactivity.setName("inactivity");
            sInactivity.setData(new ArrayList<>());
            TenantSetupStatsSerie sRegistration = new TenantSetupStatsSerie();
            sRegistration.setName("registration");
            sRegistration.setData(new ArrayList<>());
            r.getSeries().add(sUplink);
            r.getSeries().add(sCopies);
            r.getSeries().add(sDownlink);
            r.getSeries().add(sJoin);
            r.getSeries().add(sInactivity);
            r.getSeries().add(sRegistration);
            for ( HeliumDeviceStat s : ss ) {
                //log.info("device:"+s.getTenantUUID()+" uplink: "+s.getUplink());
                Tenant t = tenantRepository.findOneTenantById(UUID.fromString(s.getTenantUUID()));
                if ( t != null ) {
                    r.getDateLabel().add(t.getName());
                } else {
                    r.getDateLabel().add(s.getTenantUUID());
                }
                sUplink.getData().add(s.getUplinkDc());
                sCopies.getData().add(s.getDuplicateDc());
                sDownlink.getData().add(s.getDownlinkDc());
                sJoin.getData().add(s.getJoinDc()+s.getJoinAcceptDc());
                sInactivity.getData().add(s.getInactivityDc());
                sRegistration.getData().add(s.getRegistrationDc());
            }
            return r;
        }
    }



}
