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

import eu.heliumiot.console.jpa.db.HeliumDevice;
import eu.heliumiot.console.jpa.repository.HeliumDeviceRepository;
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
import java.util.List;

// Main purpose is to not have a circular inclusion and optimize device access with inMemory storage
@Service
public class HeliumDeviceCacheService {

    private final Logger log = LoggerFactory.getLogger(this.getClass());

    @Autowired
    protected HeliumDeviceRepository heliumDeviceRepository;

    protected int runningJobs;
    protected boolean serviceEnable; // false to stop the services

    private ObjectCache<String, HeliumDevice> heliumDeviceCache;
    @PostConstruct
    private void initHeliumDeviceCacheService() {
        this.heliumDeviceCache = new ObjectCache<String, HeliumDevice>(
                "DeviceROCache",
                5000,
                 Now.ONE_HOUR
        ) {
            @Override
            public void onCacheRemoval(String key, HeliumDevice obj, boolean batch, boolean last) {
            }

            @Override
            public void bulkCacheUpdate(List<HeliumDevice> objects) {

            }

        };
        runningJobs=0;
        serviceEnable=true;

        Gauge.builder("cons.device.cache_total_time", this.heliumDeviceCache.getTotalCacheTime())
                .description("total time device cache execution")
                .register(registry);
        Gauge.builder("cons.device.cache_total", this.heliumDeviceCache.getTotalCacheTry())
                .description("total device cache try")
                .register(registry);
        Gauge.builder("cons.device.cache_miss", this.heliumDeviceCache.getCacheMissStat())
                .description("total device cache miss")
                .register(registry);
    }

    private MeterRegistry registry;
    public HeliumDeviceCacheService(MeterRegistry registry) {
        this.registry = registry;
    }

    @Scheduled(fixedRateString = "${logging.cache.fixedrate}", initialDelay = 63_000)
    protected void cacheStatus() {
        if ( ! this.serviceEnable ) return;
        this.runningJobs++;
        try {
            this.heliumDeviceCache.log();
        } finally {
            this.runningJobs--;
        }
    }

    public void stopService() {
        this.serviceEnable = false;
    }

    // return true when the service has stopped all the running jobs
    public boolean hasStopped() {
        return (this.serviceEnable == false && this.runningJobs == 0);
    }


    public HeliumDevice getHeliumDevice(String deviceEui) {
        return getHeliumDevice(deviceEui,true);
    }

    /**
     * Get a device from cache, add it if not yet in the cache
     * @param deviceEui
     * @return
     */
    public HeliumDevice getHeliumDevice(String deviceEui, boolean cache) {
        deviceEui = deviceEui.toUpperCase();
        HeliumDevice dev = heliumDeviceCache.get(deviceEui);
        if ( dev == null ) {
            dev = heliumDeviceRepository.findOneHeliumDeviceByDeviceEui(deviceEui);
            if ( cache && dev != null ) heliumDeviceCache.put(dev,deviceEui);
        }
        return dev;
    }

    public void removeFromCache(String deviceEui) {
        deviceEui = deviceEui.toUpperCase();
        heliumDeviceCache.remove(deviceEui,false);
    }

    /**
     * Get the tenant ID with a device EUI
     * @param deviceEui
     * @return
     */
    public String getTenantId(String deviceEui) {
        HeliumDevice dev = this.getHeliumDevice(deviceEui);
        if ( dev != null ) return dev.getTenantUUID();
        return null;
    }


}
