package eu.heliumiot.console.service;


import eu.heliumiot.console.ConsolePrivateConfig;
import eu.heliumiot.console.api.interfaces.GetDeviceFramesItf;
import eu.heliumiot.console.api.interfaces.TenantBalanceItf;
import eu.heliumiot.console.jpa.db.*;
import eu.heliumiot.console.jpa.mongodb.DeviceFrames;
import eu.heliumiot.console.jpa.mongoRep.DeviceFramesMongoRepository;
import eu.heliumiot.console.jpa.repository.DeviceRepository;
import eu.heliumiot.console.jpa.repository.TenantRepository;
import eu.heliumiot.console.jpa.repository.UserTenantRepository;
import fr.ingeniousthings.tools.ITNotFoundException;
import fr.ingeniousthings.tools.ITRightException;
import fr.ingeniousthings.tools.Now;
import fr.ingeniousthings.tools.ObjectCache;
import io.micrometer.core.instrument.Gauge;
import io.micrometer.core.instrument.MeterRegistry;
import jakarta.annotation.PostConstruct;
import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class PrivDeviceFramesService {
    private final Logger log = LoggerFactory.getLogger(this.getClass());

    private ObjectCache<String, DeviceFrames> frameCache;

    @Autowired
    protected ConsolePrivateConfig consolePrivateConfig;

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
            consolePrivateConfig.getHeliumDevFrameCacheSize(),
            consolePrivateConfig.getHeliumDevFrameCacheExpiration()
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

    // =============================================================
    // Safe user access
    // =============================================================

    @Autowired
    protected UserCacheService userCacheService;

    @Autowired
    protected HeliumDeviceCacheService heliumDeviceCacheService;

    @Autowired
    protected TenantRepository tenantRepository;

    @Autowired
    protected eu.heliumiot.console.jpa.repository.DeviceRepository deviceRepository;


    public GetDeviceFramesItf getDeviceByUser(String devEui, String userId)
    throws ITRightException, ITNotFoundException {
        UserCacheService.UserCacheElement user = userCacheService.getUserById(userId);
        if (user == null) throw new ITRightException();

        // find Tenant from DeviceId
        HeliumDevice dev;
        if ( !user.user.isAdmin() ) {
            // throws ITRightException / ITNotFoundException
            dev = heliumDeviceCacheService.getHeliumDeviceForUser(devEui, userId);
        } else {
            dev = heliumDeviceCacheService.getHeliumDevice(devEui);
        }
        if ( dev == null ) throw new ITNotFoundException();

        DeviceFrames df = this.getDevice(devEui);
        if ( df == null ) throw new ITNotFoundException();

        // get Tenant
        Tenant t = tenantRepository.findOneTenantById(UUID.fromString(dev.getTenantUUID()));
        if ( t == null ) throw new ITNotFoundException();

        // get Device
        Device _d = deviceRepository.findOneDeviceByDevEui(dev.getDeviceUUID());
        if ( _d == null ) throw new ITNotFoundException();

        GetDeviceFramesItf r = new GetDeviceFramesItf();
        r.setDevName(_d.getName());
        r.setTenantID(dev.getTenantUUID());
        r.setTenantName(t.getName());

        r.initFromDeviceFrames(df);
        return r;
    }

}
