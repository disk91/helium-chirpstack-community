/*
 * Copyright (c) - Paul Pinault (aka disk91) - 2020.
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
import eu.heliumiot.console.ConsolePrivateConfig;
import eu.heliumiot.console.api.interfaces.AdvHotspotGetItf;
import eu.heliumiot.console.etl.api.HotspotIdent;
import eu.heliumiot.console.etl.api.HotspotState;
import eu.heliumiot.console.jpa.mongoRep.HotspotsMongoRepository;
import eu.heliumiot.console.jpa.mongodb.Hotspots;
import fr.ingeniousthings.tools.*;
import io.micrometer.core.instrument.Gauge;
import io.micrometer.core.instrument.MeterRegistry;
import jakarta.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
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
    protected HotspotsMongoRepository hotspotMongoRepository;

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
                        _toSave.parallelStream().forEach(hotspotMongoRepository::save);
                        _toSave.clear();
                    }
                } else {
                    hotspotMongoRepository.save(obj);
                }
            }

            @Override
            public void bulkCacheUpdate(List<Hotspots> objects) {
                hotspotMongoRepository.saveAll(objects);
            }

        };

        factory = new SimpleClientHttpRequestFactory();
        factory.setConnectTimeout(800);
        factory.setReadTimeout(2500);

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
            d = hotspotMongoRepository.findOneHotspotByHotspotId(hotspotId);
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
            d = hotspotMongoRepository.save(d);
        }
        this.hotspotCache.put(d,d.getHotspotId(),true);
        return d;
    }


    // =============================================================
    // API Interface
    // =============================================================

    public AdvHotspotGetItf getHotspotForUser(String hotspotId, String userId)
        throws ITRightException, ITNotFoundException {
        //UserCacheService.UserCacheElement user = userCacheService.getUserById(userId);
        //if (user == null) throw new ITRightException();

        Hotspots h = this.getHotspot(hotspotId);
        if ( h == null ) throw new ITNotFoundException();
        AdvHotspotGetItf r = new AdvHotspotGetItf();
        r.initFromHotspots(h);

        return r;
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
        } else if ( _queueSize >= 100 ){
            this.metrics_queue_full++;
        }
    }

    @Scheduled(fixedRate = 200, initialDelay = 20_000)
    protected void refreshHotspotData() {
        try {
            // take a single pending request to not overload the backend etl
            if (_queueSize > 0) {
                boolean oneGet = false;
                Hotspots h = null;
                while (!oneGet) {
                    // we can have multiple time the same hotspot in queue, do not
                    // refresh it, sound faster here than rechecking the whole queue
                    // every time.
                    if (_queueSize > 0) {
                        synchronized (_queueSizeLock) {
                            _queueSize--;
                        }
                        h = syncPending.poll();
                        if (h != null && (Now.NowUtcMs() - h.getLastEtlUpdate()) > Now.ONE_HOUR) {
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
                    h.setSumOfIoTRewards((hd.getSumRewardBeacon() + hd.getSumRewardWitness() + hd.getSumRewardDc()) / 1_000_000);
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
                    this.metrics_resptm_total += (Now.NowUtcMs() - now);
                } catch (ITNotFoundException x) {
                    this.metrics_call_failed++;
                    int failure = h.getFailure();
                    h.setFailure(failure + 1);
                    // does not retry too fast
                    if (failure >= 2) h.setLastEtlUpdate(Now.NowUtcMs() - 45*Now.ONE_MINUTE);
                    else addRefreshHostpotData(h);
                }
                hotspotMongoRepository.save(h);
            }
        } catch (Exception e) {
            log.error("refresh Hotspot Data Failure "+e.getMessage());
            e.printStackTrace();
        }
    }


    // ==============================================================
    // ETL live dapa Api
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
                log.debug("ETL Hotspot "+hotspotId+" not found "+responseEntity.getStatusCode());
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

    // ==============================================================
    // ETL Api cache data
    // ==============================================================

    protected HttpEntity<String> createEtlApiHeaders(){
        HttpHeaders headers = new HttpHeaders();
        ArrayList<MediaType> accept = new ArrayList<>();
        accept.add(MediaType.APPLICATION_JSON);
        headers.setAccept(accept);
        headers.add(HttpHeaders.USER_AGENT,"console/"+consoleConfig.getHeliumRouteOui());
        if ( consolePrivateConfig.getHeliumEtlApiUser() != null && !consolePrivateConfig.getHeliumEtlApiUser().isEmpty()) {
            String auth = consolePrivateConfig.getHeliumEtlApiUser() + ":" + consolePrivateConfig.getHeliumEtlApiPassword();
            byte[] encodedAuth = Base64.getEncoder().encode(
                auth.getBytes(StandardCharsets.US_ASCII));
            String authHeader = "Basic " + new String(encodedAuth);
            headers.add(HttpHeaders.AUTHORIZATION, authHeader);
        }
        return new HttpEntity<String>(headers);
    }


    public List<HotspotIdent> getHostpotAround(double latN, double latS, double lonW, double lonE) throws ITParseException, ITNotFoundException {
        // get from ETL
        RestTemplate restTemplate = new RestTemplate(this.factory);
        String url = "";
        try {
            HttpEntity<String> he = createHeaders();
            url = consolePrivateConfig.getHeliumEtlApiUrl() + "/hotspot/3.0/search/"+latN+"/"+latS+"/"+lonW+"/"+lonE+"/";
            ResponseEntity<List<HotspotIdent>> responseEntity =
                restTemplate.exchange(
                    url,
                    HttpMethod.GET,
                    he,
                    new ParameterizedTypeReference<List<HotspotIdent>>() {}
                );
            if (responseEntity.getStatusCode() == HttpStatus.OK) {
                if (responseEntity.getBody() != null) {
                    return responseEntity.getBody();
                } else {
                    log.warn("Response from ETL-API with empty body for hotspot around ");
                    throw new ITNotFoundException();
                }
            } else {
                log.debug("ELT-API Hotspot around not found "+responseEntity.getStatusCode());
                // if (responseEntity.getBody() != null) log.info(""+responseEntity.getBody());
                throw new ITNotFoundException();
            }
        } catch ( ITNotFoundException x ) {
            throw new ITNotFoundException();
        } catch (Exception e) {
            //e.printStackTrace();
            log.warn("Exception "+e.getMessage());
            throw new ITParseException();
        }
    }

    // ============================================================
    // Clean old Hotspot
    // ============================================================
    @Scheduled(fixedRate = 86_400_000, initialDelay = 1_980_000) // every 24h / first after 33m
    protected void clearOldHotspot() {
        long start = Now.NowUtcMs();
        try {
            long limit = start - ( consolePrivateConfig.getHeliumDevMaxHotspotDays() * Now.ONE_FULL_DAY );
            hotspotMongoRepository.deleteHotspotByAge(limit);
            log.info("clear_old_Hotspot - delete_helium_device_stats_history duration " + (Now.NowUtcMs() - start) / 1000 + "s");
        } catch ( Exception x ) {
            log.error("clear_old_Hotspot - error ("+x.getMessage()+") - after "+ (Now.NowUtcMs() - start) / 1000 + "s");
        }
    }


}
