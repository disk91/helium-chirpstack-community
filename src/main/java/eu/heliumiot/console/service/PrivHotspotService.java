package eu.heliumiot.console.service;


import eu.heliumiot.console.ConsoleConfig;
import eu.heliumiot.console.ConsolePrivateConfig;
import eu.heliumiot.console.etl.api.HotspotData;
import eu.heliumiot.console.etl.api.HotspotState;
import eu.heliumiot.console.jpa.mongoRep.HotspotsMongoRepository;
import eu.heliumiot.console.jpa.mongodb.Hotspots;
import fr.ingeniousthings.tools.ITNotFoundException;
import fr.ingeniousthings.tools.Now;
import fr.ingeniousthings.tools.ObjectCache;
import io.micrometer.core.instrument.Gauge;
import io.micrometer.core.instrument.MeterRegistry;
import jakarta.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.function.Supplier;

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

    private SimpleClientHttpRequestFactory factory;

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

        factory = new SimpleClientHttpRequestFactory();
        factory.setConnectTimeout(800);
        factory.setReadTimeout(1500);

        Gauge.builder("cons.hotspot.cache_total_time", this.hotspotCache.getTotalCacheTime())
            .description("total time hotspot cache execution")
            .register(registry);
        Gauge.builder("cons.hotspot.cache_total", this.hotspotCache.getTotalCacheTry())
            .description("total hotspot cache try")
            .register(registry);
        Gauge.builder("cons.hotspot.cache_miss", this.hotspotCache.getCacheMissStat())
            .description("total hotspot cache miss")
            .register(registry);

        Gauge.builder("cons.etl.queue.full", this.getMetricsQueueFull())
            .description("missed hotspot sync due to queue full")
            .register(registry);
        Gauge.builder("cons.etl.call.total", this.getMetricsCallTotal())
            .description("number of ETL call")
            .register(registry);
        Gauge.builder("cons.etl.response.time", this.getMetricsResptmTotal())
            .description("sum of time pass to call ETL")
            .register(registry);
        Gauge.builder("cons.etl.call.failed", this.getMetricsCallFailed())
            .description("total etl call failed")
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
        hotspotId = hotspotId;
        Hotspots d = this.hotspotCache.get(hotspotId);
        if ( d == null ) {
            // search in db
            d = hotpotMongoRepository.findOneHotspotByHotspotId(hotspotId);
            if ( d != null ) {
                this.hotspotCache.put(d,d.getHotspotId());
            }
        }
        if ( d != null ) addRefreshHostpotData(d);
        return d;
    }

    public Hotspots updateHotspot(Hotspots d) {
        if ( d.getId() == null ) {
            // new entry
            d = hotpotMongoRepository.save(d);
        }
        this.hotspotCache.put(d,d.getHotspotId(),true);
        return d;
    }


    // =============================================================
    // Refresh ETL stats
    // =============================================================

    // stats / metrics
    protected long metrics_queue_full = 0;
    public Supplier<Number> getMetricsQueueFull() { return ()->this.metrics_queue_full; };
    protected long metrics_resptm_total = 0;
    public Supplier<Number> getMetricsResptmTotal() { return ()->this.metrics_resptm_total; };
    protected long metrics_call_total = 0;
    public Supplier<Number> getMetricsCallTotal() { return ()->this.metrics_call_total; };
    protected long metrics_call_failed = 0;
    public Supplier<Number> getMetricsCallFailed() { return ()->this.metrics_call_failed; };

    // queue
    protected ConcurrentLinkedQueue<Hotspots> syncPending = new ConcurrentLinkedQueue<>();
    protected long _queueSize = 0;
    protected final Object _queueSizeLock = new Object();

    public void addRefreshHostpotData(Hotspots h) {
        long now = Now.NowUtcMs();
        if( (now - h.getLastEtlUpdate()) > Now.ONE_HOUR && _queueSize < 100 && h.getId() != null) {
            syncPending.add(h);
            synchronized (_queueSizeLock ) {_queueSize++;}
        } else {
            this.metrics_queue_full++;
        }
    }

    @Scheduled(fixedRate = 200, initialDelay = 20_000)
    protected void refreshHotspotData() {
        // take a single pending request to not overload the backend etl
        if ( _queueSize > 0 ) {
            boolean oneGet = false;
            Hotspots h = null;
            while ( !oneGet ) {
                // we can have multiple time the same hotspot in queue, do not
                // refresh it, sound faster here than rechecking the whole queue
                // every time.
                if ( _queueSize > 0 ) {
                    synchronized (_queueSizeLock) {
                        _queueSize--;
                    }
                    h = syncPending.poll();
                    if ( h!=null && (Now.NowUtcMs() - h.getLastEtlUpdate()) > Now.ONE_HOUR ) {
                        oneGet = true;
                    }
                } else return;
            }
            try {
                long now = Now.NowUtcMs();
                HotspotState hd = getHostpotState(h.getHotspotId());
                h.setBrand(hd.getBrand());
                h.setLastWitnessMs(hd.getLastWitness());
                h.setLastBeaconMs(hd.getLastBeacon());
                h.setLastRewardMs(hd.getLastReward());
                h.setSumOfIoTRewards((hd.getSumRewardBeacon()+hd.getSumRewardWitness()+hd.getSumRewardDc()) / 1_000_000);
                h.setBeaconned(hd.getBeaconned());
                h.setWitnessed(hd.getWitnessed());
                h.setMaxTxDistance(hd.getMaxTxDistance());
                h.setMaxRxDistance(hd.getMaxRxDistance());
                h.setMaxRxBudgetLinkDB(hd.getMaxRxBudgetLinkDB());
                h.setRewardHistories(hd.getRewardHistories());
                h.setWitnessesHistory(hd.getWitnessesHistory());
                h.setLastEtlUpdate(Now.NowUtcMs());
                h.setFailure(0);
                this.metrics_call_total++;
                this.metrics_resptm_total += (Now.NowUtcMs()-now);
            } catch (ITNotFoundException x) {
                this.metrics_call_failed++;
                int failure = h.getFailure();
                h.setFailure(failure+1);
                // does not retry too fast
                if ( failure >= 2 ) h.setLastEtlUpdate(Now.NowUtcMs()-Now.ONE_HOUR);
                else addRefreshHostpotData(h);
            }
            hotpotMongoRepository.save(h);
        }
    }


    // ==============================================================
    // ETL Api
    // ==============================================================

    @Autowired
    protected ConsoleConfig consoleConfig;

    protected HttpEntity<String> createHeaders(){
        HttpHeaders headers = new HttpHeaders();
        ArrayList<MediaType> accept = new ArrayList<>();
        accept.add(MediaType.APPLICATION_JSON);
        headers.setAccept(accept);
        headers.add(HttpHeaders.USER_AGENT,"console/"+consoleConfig.getHeliumRouteOui());
        if ( consolePrivateConfig.getHeliumEtlUser() != null && !consolePrivateConfig.getHeliumEtlUser().isEmpty()) {
            String auth = consolePrivateConfig.getHeliumEtlUser() + ":" + consolePrivateConfig.getHeliumEtlPassword();
            byte[] encodedAuth = Base64.getEncoder().encode(
                auth.getBytes(StandardCharsets.US_ASCII));
            String authHeader = "Basic " + new String(encodedAuth);
            headers.add(HttpHeaders.AUTHORIZATION, authHeader);
        }
        return new HttpEntity<String>(headers);
    }


    public HotspotState getHostpotState(String hotspotId) throws ITNotFoundException {
        // get from ETL
        RestTemplate restTemplate = new RestTemplate(this.factory);
        String url = "";
        try {
            HttpEntity<String> he = createHeaders();
            url = consolePrivateConfig.getHeliumEtlUrl() + "/hotspot/3.0/" + hotspotId + "/state";
            ResponseEntity<HotspotState> responseEntity =
                restTemplate.exchange(
                    url,
                    HttpMethod.GET,
                    he,
                    HotspotState.class
                );
            if (responseEntity.getStatusCode() == HttpStatus.OK) {
                if (responseEntity.getBody() != null) {
                    return responseEntity.getBody();
                } else {
                    log.warn("Response from ETL with empty body for hotspot "+hotspotId);
                    throw new ITNotFoundException();
                }
            } else {
                log.debug("ELT Hotspot "+hotspotId+" not found "+responseEntity.getStatusCode());
                // if (responseEntity.getBody() != null) log.info(""+responseEntity.getBody());
                throw new ITNotFoundException();
            }
        } catch ( ITNotFoundException x ) {
            throw new ITNotFoundException();
        } catch (Exception e) {
            //e.printStackTrace();
            log.warn("Exception "+e.getMessage());
            throw new ITNotFoundException();
        }
    }



}
