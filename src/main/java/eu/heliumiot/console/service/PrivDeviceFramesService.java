package eu.heliumiot.console.service;


import eu.heliumiot.console.jpa.mongodb.DeviceFrames;
import eu.heliumiot.console.jpa.mongoRep.DeviceFramesMongoRepository;
import fr.ingeniousthings.tools.Now;
import fr.ingeniousthings.tools.ObjectCache;
import io.micrometer.core.instrument.Gauge;
import io.micrometer.core.instrument.MeterRegistry;
import jakarta.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class PrivDeviceFramesService {
    private final Logger log = LoggerFactory.getLogger(this.getClass());

    private ObjectCache<String, DeviceFrames> frameCache;

    @Autowired
    protected DeviceFramesMongoRepository deviceFramesMongoRepository;

    // =============================================================
    // Cache Management
    // =============================================================

    private final MeterRegistry registry;
    public PrivDeviceFramesService(MeterRegistry registry) {
        this.registry = registry;
    }


    @PostConstruct
    private void initDeviceFrameService() {
        this.frameCache = new ObjectCache<String, DeviceFrames>(
            "DeviceFrameCache",
            1000,               // @todo param
            3600_000                    // @todo param
        ) {
            private ArrayList<DeviceFrames> _toSave = new ArrayList<>();
            @Override
            public void onCacheRemoval(String key, DeviceFrames obj, boolean batch, boolean last) {
                if ( batch ) {
                    if ( obj != null ) _toSave.add(obj);
                    if ( _toSave.size() > 5000 || last ) {
                        _toSave.parallelStream().forEach(deviceFramesMongoRepository::save);
                        _toSave.clear();
                    }
                } else {
                    deviceFramesMongoRepository.save(obj);
                }
            }

            @Override
            public void bulkCacheUpdate(List<DeviceFrames> objects) {
                deviceFramesMongoRepository.saveAll(objects);
            }

        };
        Gauge.builder("cons.device.frame.cache_total_time", this.frameCache.getTotalCacheTime())
            .description("total time device frame cache execution")
            .register(registry);
        Gauge.builder("cons.device.frame.cache_total", this.frameCache.getTotalCacheTry())
            .description("total device frame cache try")
            .register(registry);
        Gauge.builder("cons.device.frame.cache_miss", this.frameCache.getCacheMissStat())
            .description("total device frame cache miss")
            .register(registry);
    }

    protected boolean serviceEnable = true;
    protected int runningJobs = 0;

    @Scheduled(fixedRate = 900_000, initialDelay = 900_000)
    protected void cacheFlush() {
        if ( ! this.serviceEnable ) return;
        this.runningJobs++;
        long start = Now.NowUtcMs();
        try {
            // sync the cache with DB
            long updated;
            do {
                updated = this.frameCache.commit(true,250);
            } while ( updated == 250 );
            this.frameCache.flush();
        } finally {
            this.runningJobs--;
        }
        log.debug("Device Frame cache commit in "+(Now.NowUtcMs()-start)+"ms");
    }

    @Scheduled(fixedRateString = "${logging.cache.fixedrate}", initialDelay = 62_300)
    protected void cacheStatus() {
        if ( ! this.serviceEnable ) return;
        this.runningJobs++;
        try {
            this.frameCache.log();
        } finally {
            this.runningJobs--;
        }
    }

    public void stopService() {
        this.serviceEnable = false;
    }

    // return true when the service has stopped all the running jobs
    public boolean hasStopped() {
        return (!this.serviceEnable && this.runningJobs == 0);
    }

    public void stopPrivDeviceFrameServiceCache() {
        log.info("Stopping PrivDeviceFramesService");
        this.frameCache.flush();
    }


    // =============================================================
    // Cache IN & OUT
    // =============================================================

    // Returns the device entry or null if does not exists
    public DeviceFrames getDevice(String devEui) {
        DeviceFrames d = this.frameCache.get(devEui);
        if ( d == null ) {
            // search in db
            d = deviceFramesMongoRepository.findOneDeviceFramesByDevEui(devEui);
            if ( d != null ) {
                this.frameCache.put(d,d.getDevEui());
            }
        }
        return d;
    }

    public DeviceFrames updateDevice(DeviceFrames d) {
        if ( d.getId() == null ) {
            // new entry
            d = deviceFramesMongoRepository.save(d);
        }
        this.frameCache.put(d,d.getDevEui(),true);
        return d;
    }


}
