package eu.heliumiot.console.service;


import eu.heliumiot.console.ConsolePrivateConfig;
import eu.heliumiot.console.jpa.mongoRep.DeviceFramesMongoRepository;
import eu.heliumiot.console.jpa.mongoRep.HotspotsMongoRepository;
import eu.heliumiot.console.jpa.mongodb.DeviceFrames;
import eu.heliumiot.console.jpa.mongodb.Hotspots;
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
public class PrivHotspotService {
    private final Logger log = LoggerFactory.getLogger(this.getClass());

    private ObjectCache<String, Hotspots> hotspotCache;

    @Autowired
    protected ConsolePrivateConfig consolePrivateConfig;

    @Autowired
    protected HotspotsMongoRepository hotpotMongoRepository;

    // =============================================================
    // Cache Management
    // =============================================================

    private final MeterRegistry registry;
    public PrivHotspotService(MeterRegistry registry) {
        this.registry = registry;
    }


    @PostConstruct
    private void initHostpotService() {
        this.hotspotCache = new ObjectCache<String, Hotspots>(
            "HotspotCache",
            consolePrivateConfig.getHeliumHotspotCacheSize(),
            consolePrivateConfig.getHeliumHotspotCacheExpiration()
        ) {
            private ArrayList<Hotspots> _toSave = new ArrayList<>();
            @Override
            public void onCacheRemoval(String key, Hotspots obj, boolean batch, boolean last) {
                if ( batch ) {
                    if ( obj != null ) _toSave.add(obj);
                    if ( _toSave.size() > 5000 || last ) {
                        _toSave.parallelStream().forEach(hotpotMongoRepository::save);
                        _toSave.clear();
                    }
                } else {
                    hotpotMongoRepository.save(obj);
                }
            }

            @Override
            public void bulkCacheUpdate(List<Hotspots> objects) {
                hotpotMongoRepository.saveAll(objects);
            }

        };
        Gauge.builder("cons.hotspot.cache_total_time", this.hotspotCache.getTotalCacheTime())
            .description("total time hotspot cache execution")
            .register(registry);
        Gauge.builder("cons.hotspot.cache_total", this.hotspotCache.getTotalCacheTry())
            .description("total hotspot cache try")
            .register(registry);
        Gauge.builder("cons.hotspot.cache_miss", this.hotspotCache.getCacheMissStat())
            .description("total hotspot cache miss")
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
                updated = this.hotspotCache.commit(true,250);
            } while ( updated == 250 );
            this.hotspotCache.flush();
        } finally {
            this.runningJobs--;
        }
        log.debug("Hotspot cache commit in "+(Now.NowUtcMs()-start)+"ms");
    }

    @Scheduled(fixedRateString = "${logging.cache.fixedrate}", initialDelay = 62_300)
    protected void cacheStatus() {
        if ( ! this.serviceEnable ) return;
        this.runningJobs++;
        try {
            this.hotspotCache.log();
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

    public void stopPrivHotspotServiceCache() {
        log.info("Stopping PrivHotspotService");
        this.hotspotCache.flush();
    }


    // =============================================================
    // Cache IN & OUT
    // =============================================================

    // Returns the hotspot entry or null if does not exists
    public Hotspots getHotspot(String hotspotId) {
        hotspotId = hotspotId.toLowerCase();
        Hotspots d = this.hotspotCache.get(hotspotId);
        if ( d == null ) {
            // search in db
            d = hotpotMongoRepository.findOneHotspotByHotspotId(hotspotId);
            if ( d != null ) {
                this.hotspotCache.put(d,d.getHotspotId().toLowerCase());
            }
        }
        return d;
    }

    public Hotspots updateHotspot(Hotspots d) {
        if ( d.getId() == null ) {
            // new entry
            d = hotpotMongoRepository.save(d);
        }
        this.hotspotCache.put(d,d.getHotspotId().toLowerCase(),true);
        return d;
    }


}
