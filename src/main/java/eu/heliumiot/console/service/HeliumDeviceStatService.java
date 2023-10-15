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
import eu.heliumiot.console.jpa.db.HeliumDeviceStat;
import eu.heliumiot.console.mqtt.api.HeliumDeviceStatItf;
import eu.heliumiot.console.jpa.repository.HeliumDeviceStatsRepository;
import fr.ingeniousthings.tools.Now;
import fr.ingeniousthings.tools.ObjectCache;
import io.micrometer.core.instrument.Gauge;
import io.micrometer.core.instrument.MeterRegistry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;

@Service
public class HeliumDeviceStatService {
    private final Logger log = LoggerFactory.getLogger(this.getClass());

    @Autowired
    protected ConsoleConfig consoleConfig;

    @Autowired
    protected HeliumDeviceStatsRepository heliumDeviceStatsRepository;

    protected int runningJobs;
    protected boolean serviceEnable; // false to stop the services

    private ObjectCache<String, HeliumDeviceStat> heliumDeviceStatCache;
    @PostConstruct
    private void initHeliumDeviceStatService() {
        this.heliumDeviceStatCache = new ObjectCache<String, HeliumDeviceStat>(
                "DeviceStat",
                5000,
                2*Now.ONE_HOUR
        ) {
            @Override
            public void onCacheRemoval(String key, HeliumDeviceStat obj, boolean batch, boolean last) {
                if ( batch ) {
                    if ( obj != null ) {
                        obj.setLastCommit(Now.NowUtcMs());
                        heliumDeviceStatsRepository.save(obj);
                    }
                } else {
                    obj.setLastCommit(Now.NowUtcMs());
                    heliumDeviceStatsRepository.save(obj);
                }
            }

            @Override
            public void bulkCacheUpdate(List<HeliumDeviceStat> objects) {
                for (HeliumDeviceStat obj : objects ) {
                    obj.setLastCommit(Now.NowUtcMs());
                    heliumDeviceStatsRepository.save(obj);
                }
            }

        };

        runningJobs=0;
        serviceEnable=true;

        Gauge.builder("cons.device.stat.cache_total_time", this.heliumDeviceStatCache.getTotalCacheTime())
                .description("total time device stat cache execution")
                .register(registry);
        Gauge.builder("cons.device.stat.cache_total", this.heliumDeviceStatCache.getTotalCacheTry())
                .description("total device stat cache try")
                .register(registry);
        Gauge.builder("cons.device.stat.cache_miss", this.heliumDeviceStatCache.getCacheMissStat())
                .description("total device stat cache miss")
                .register(registry);

    }

    private final MeterRegistry registry;
    public HeliumDeviceStatService(MeterRegistry registry) {
        this.registry = registry;
    }


    // request to stop the service properly
    public void stopService() {
        this.serviceEnable = false;
    }

    // return true when the service has stopped all the running jobs
    public boolean hasStopped() {
        return (!this.serviceEnable && this.runningJobs == 0);
    }

    public void stopHeliumDeviceStatServiceCache() {
        log.info("Stopping HeliumDeviceStatService");
        this.heliumDeviceStatCache.flush();
    }

    @Scheduled(fixedRateString = "${logging.cache.fixedrate}", initialDelay = 62_000)
    protected void cacheStatus() {
        if ( ! this.serviceEnable ) return;
        this.runningJobs++;
        try {
            this.heliumDeviceStatCache.log();
        } finally {
            this.runningJobs--;
        }
    }

    @Scheduled(fixedRate = 900_000, initialDelay = 900_000)
    protected void cacheFlush() {
        if ( ! this.serviceEnable ) return;
        this.runningJobs++;
        long start = Now.NowUtcMs();
        try {
            // sync the cache with DB
            this.heliumDeviceStatCache.flush();
        } finally {
            this.runningJobs--;
        }
        log.debug("Device stat cache flushed in "+(Now.NowUtcMs()-start)+"ms");
    }

    protected HeliumDeviceStat getCurrentDeviceStat(String devId, String tenantId, boolean createOne) {
        long dayRef = Now.TodayMidnightUtc();
        // try cache
        devId = devId.toUpperCase();
        HeliumDeviceStat s = this.heliumDeviceStatCache.get(devId);
        if ( s != null && s.getDay() == dayRef ) {
            if ( Now.NowUtcMs() - s.getLastCommit() > Now.ONE_HOUR ) {
                s.setLastCommit(Now.NowUtcMs());
                heliumDeviceStatsRepository.save(s);
            }
            return s;
        }
        if ( s != null ) {
            // not the right period
            this.heliumDeviceStatCache.remove(devId,true);
            s = null;
        } else {
            // try from database
            s = heliumDeviceStatsRepository.findOneHeliumDeviceStatByDeviceUUIDAndDay(
                    devId,
                    dayRef
            );
            if ( s != null ) {
                this.heliumDeviceStatCache.put(s,devId);
            }
        }
        if  ( s == null && createOne ) {
            // create a new one
            s = new HeliumDeviceStat();
            s.setDay(dayRef);
            s.setDeviceUUID(devId);
            s.setTenantUUID(tenantId);
            s.setLastCommit(Now.NowUtcMs());
            s.setUplinkDc(0);
            s.setDuplicateDc(0);
            s.setDuplicate(0);
            s.setDownlinkDc(0);
            s.setInactivityDc(0);
            s.setActivityDc(0);
            s.setRegistrationDc(0);
            s.setUplink(0);
            s.setDownlink(0);
            s.setJoinReq(0);
            s.setJoinDc(0);
            s.setJoinAcceptDc(0);
            s = heliumDeviceStatsRepository.save(s);
            this.heliumDeviceStatCache.put(s,devId);
        }
        return s;
    }

    public void updateDeviceStat(
            HeliumDeviceStatItf e
    ) {
        HeliumDeviceStat s = this.getCurrentDeviceStat(e.getDeviceId(), e.getTenantId(), true);
        s.setUplinkDc(s.getUplinkDc()+e.getUplinkDc());
        s.setDuplicate(s.getDuplicate()+e.getDuplicate());
        s.setDuplicateDc(s.getDuplicateDc()+e.getDuplicateDc());
        s.setDownlinkDc(s.getDownlinkDc()+e.getDownlinkDc());
        s.setInactivityDc(s.getInactivityDc()+e.getInactivityDc());
        s.setActivityDc(s.getActivityDc()+e.getActivityDc());
        s.setRegistrationDc(s.getRegistrationDc()+e.getRegistrationDc());
        s.setUplink(s.getUplink()+e.getUplink());
        s.setDownlink(s.getDownlink()+e.getDownlink());
        s.setJoinReq(s.getJoinReq()+e.getJoin());
        s.setJoinDc(s.getJoinDc()+e.getJoinDc());
        s.setJoinAcceptDc(s.getJoinAcceptDc()+e.getJoinAcceptDc());
        this.heliumDeviceStatCache.put(s,e.getDeviceId()); // refresh update status
    }

    /**
     * Get the devices stat from the database, unsafe as the cache is not used,
     * Just checked for the current period is <= stop
     * @param devEUI
     * @param start
     * @param stop
     * @return
     */
    public List<HeliumDeviceStat> getDeviceStatsUnsafe(String devEUI, String tenantID, long start, long stop) {
        devEUI = devEUI.toUpperCase();
        List<HeliumDeviceStat> r = this.heliumDeviceStatsRepository.findHeliumDeviceStatByDeviceUUIDAndDayBetween(
                devEUI,
                start,
                stop
        );
        if ( r == null ) r = new ArrayList<HeliumDeviceStat>();
        HeliumDeviceStat c = this.getCurrentDeviceStat(devEUI,tenantID,false);
        if ( c != null && ( c.getDay() >= start && c.getDay() <= stop ) ) {
            r.add(c);
        }
        return r;
    }

}
