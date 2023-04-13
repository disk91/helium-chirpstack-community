package eu.heliumiot.console.service;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import eu.heliumiot.console.ConsoleConfig;
import eu.heliumiot.console.api.interfaces.ServerLogItf;
import eu.heliumiot.console.jpa.db.Device;
import eu.heliumiot.console.jpa.db.HeliumLogs;
import eu.heliumiot.console.jpa.db.SumResult;
import eu.heliumiot.console.jpa.repository.*;
import fr.ingeniousthings.tools.DateConverters;
import fr.ingeniousthings.tools.Now;
import fr.ingeniousthings.tools.Tools;
import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.Gauge;
import io.micrometer.core.instrument.MeterRegistry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.function.Supplier;

@Component
public class PrometeusService {

    private final Logger log = LoggerFactory.getLogger(this.getClass());

    // ---- API Metrics

    private long apiTotalTimeMs = 0;        // time spent in API - OK
    private long apiTotalCount = 0;         // number of API calls - OK
    private long apiTotalError = 0;         // number of API error - OK

    private long apiDcBalanceTimeMs = 0;    // time specific for the Dc balance API - OK
    private long apiDcBalanceTotal = 0;     // number of API call - OK

    private long apiHeliumTimeMs = 0;       // time specific for the Helium API calls -OK
    private long apiHeliumTotal = 0;        // number of Helium Api calls - OK
    private long apiHeliumErrors = 0;       // number of failure on Helium Api - OK

    private long userTotalLoginCount = 0;        // number of successful login - OK

    // ------ LoRaWan Metrics
    private long loRaTotalTravelTimeMs = 0;      // time between emission and reception - OK
    private long loRaUplinkCount = 0;           // number of uplink messages - OK
    private long loRaDownlinkCount = 0;         // number of downlink messages - OK
    private long loRaTotalUplinkBytes = 0;      // number of bytes transfered in Uplink - OK
    private long loRaTotalDownlinkBytes = 0;    // number of bytes transfered in Downlink - OK

    private long loRaJoinCount = 0;             // number of Join request - OK

    // ------- internal Metrics
    private long mqttConnectionLoss = 0;        // number of MQTT disconnection - OK
    private long redisStreamReadError = 0;      // number of Redis read error - OK
    private long internalDelayedUsageStats = 0; // number of pending stat update in the queue - OK
    private long routeUpdateTotalTimeMs = 0;    // time spent in processing route update - OK
    private long routeUpdateTotal = 0;          // count the number of route update - OK
    private long deviceCreationTotal = 0;       // count the device creation detection - OK
    private long deviceDeletionTotal = 0;       // count the device deletion detection - OK
    private long devadrUpdateTotalTimeMs = 0;   // time spent in processing update in the route - OK
    private long devadrUpdateTotal = 0;         // number of devadr filter update - OK

    // ------- data stats
    private long dcTotal = 0;                   // total number of DCs in the tenants
    private long deviceTotal = 0;               // number of devices in db
    private long tenantTotal = 0;               // number of tenants
    private long userTotal = 0;                 // number of users

    private long lastSeenTestDevice = 0;        // one of the device is a test device, measure last seen
                                                // to verify if network works, for alarm generation

    // -------- db info

    private long queryTotalMs = 0;              // time to execute the data stats
    private long queryTotal = 0;                // number of query execution


    // =============================================================
    // Stat updaters

    synchronized public void addApiTotalTimeMs(long startMs) {
        this.apiTotalTimeMs += Now.NowUtcMs() - startMs;
        this.apiTotalCount++;
    }
    synchronized public void addApiTotalError() {
        this.apiTotalError++;
    }

    synchronized public void addUserTotalLogin() {
        this.userTotalLoginCount++;
    }

    synchronized public void addApiDcBalanceTimeMs(long startMs) {
        this.apiDcBalanceTimeMs += Now.NowUtcMs() - startMs;
        this.apiDcBalanceTotal++;
    }
    synchronized public void addHeliumApiTotalTimeMs(long startMs) {
        this.apiHeliumTimeMs += Now.NowUtcMs() - startMs;
        this.apiHeliumTotal++;
    }
    synchronized public void addHeliumTotalError() { this.apiHeliumErrors++; }

    synchronized public void addMqttConnectionLoss() { this.mqttConnectionLoss++; }

    synchronized public void addLoRaUplink(long travelTime, long bytes) {
        this.loRaUplinkCount ++;
        this.loRaTotalUplinkBytes += bytes;
        this.loRaTotalTravelTimeMs += travelTime;
    }

    synchronized public void addLoRaJoin() { this.loRaJoinCount++; }

    synchronized public void addLoRaDownlink(long size) {
        this.loRaDownlinkCount++;
        this.loRaTotalDownlinkBytes+=size;
    }

    synchronized public void addRedisStreamError() { this.redisStreamReadError++; }

    synchronized public void addDelayedStatUpdate() { this.internalDelayedUsageStats++; }
    synchronized public void delDelayedStatUpdate() { this.internalDelayedUsageStats--; }

    synchronized public void addRouteUpdate(long start) {
        this.routeUpdateTotal++;
        this.routeUpdateTotalTimeMs+=Now.NowUtcMs() - start;
    }

    synchronized public void addDevAddrUpdate(long start) {
        this.devadrUpdateTotal++;
        this.devadrUpdateTotalTimeMs+=Now.NowUtcMs() - start;
    }

    synchronized public void addDeviceCreation() {
        this.deviceCreationTotal++;
    }

    synchronized public void addDeviceDeletion() {
        this.deviceDeletionTotal++;
    }

    public void updateDcTotal(long dcTot) {
        this.dcTotal = dcTot;
    }

    public void updateTenantTotal(long total) {
        this.tenantTotal = total;
    }

    public void updateDeviceTotal(long total) {
        this.deviceTotal = total;
    }

    public void updateUserTotal(long total) {
        this.userTotal = total;
    }

    public void setLastSeenTestDevice(long timestamp){
        this.lastSeenTestDevice = timestamp;
    }

    public void addQueryDb(long duration) {
        this.queryTotal++;
        this.queryTotalMs+=duration;
    }

    // =============================================================
    // Prometheus interface


    protected Supplier<Number> getApiTotalTimeMs() {
        return ()->apiTotalTimeMs;
    }

    protected Supplier<Number> getApiTotalCount() {
        return ()->apiTotalCount;
    }

    protected Supplier<Number> getApiTotalError() {
        return ()->apiTotalError;
    }

    protected Supplier<Number> getUserTotalLoginCount() {
        return ()->userTotalLoginCount;
    }

    protected Supplier<Number> getApiDcBalanceTimeMs() {
        return ()->apiDcBalanceTimeMs;
    }

    protected Supplier<Number> getApiDcBalanceTotal() {
        return ()->apiDcBalanceTotal;
    }
    protected Supplier<Number> getHeliumApiTotalTimeMs() {
        return ()->apiHeliumTimeMs;
    }

    protected Supplier<Number> getHeliumApiTotal() {
        return ()->apiHeliumTotal;
    }

    protected Supplier<Number> getHeliumApiTotalError() {
        return ()->apiHeliumErrors;
    }

    protected Supplier<Number> getMqttConnectionLoss() {
        return ()->mqttConnectionLoss;
    }

    protected Supplier<Number> getRedisStreamError() {
        return ()->redisStreamReadError;
    }

    protected Supplier<Number> getLoRaTravelTime() {
        return ()->loRaTotalTravelTimeMs;
    }

    protected Supplier<Number> getLoRaTotalUplink() {
        return ()->loRaUplinkCount;
    }

    protected Supplier<Number> getLoRaTotalBytesUplink() {
        return ()->loRaTotalUplinkBytes;
    }

    protected Supplier<Number> getLoRaTotalJoin() {
        return ()->loRaJoinCount;
    }
    protected Supplier<Number> getLoRaTotalDownlink() {
        return ()->loRaDownlinkCount;
    }

    protected Supplier<Number> getLoRaTotalBytesDownlink() {
        return ()->loRaTotalDownlinkBytes;
    }

    protected Supplier<Number> getDelayedStatUpdate() {
        return ()->internalDelayedUsageStats;
    }

    protected Supplier<Number> getRouteUpdateTotalMs() {
        return ()->routeUpdateTotalTimeMs;
    }
    protected Supplier<Number> getRouteUpdateTotal() {
        return ()->routeUpdateTotal;
    }
    protected Supplier<Number> getDevAddrUpdateTotalMs() {
        return ()->devadrUpdateTotalTimeMs;
    }
    protected Supplier<Number> getDevAddrUpdateTotal() {
        return ()->devadrUpdateTotal;
    }
    protected Supplier<Number> getDeviceCreation() {
        return ()->deviceCreationTotal;
    }
    protected Supplier<Number> getDeviceDeletion() {
        return ()->deviceDeletionTotal;
    }
    protected Supplier<Number> getDcTotal() {
        return ()->dcTotal;
    }
    protected Supplier<Number> getDeviceTotal() {
        return ()->deviceTotal;
    }
    protected Supplier<Number> getTenantTotal() {
        return ()->tenantTotal;
    }
    protected Supplier<Number> getUserTotal() {
        return ()->userTotal;
    }
    protected Supplier<Number> getLastSeenTestDev() {
        return ()->((lastSeenTestDevice >0)?(Now.NowUtcMs() - lastSeenTestDevice):0);
    }
    protected Supplier<Number> getDbQueryTotalMs() {
        return ()->queryTotalMs;
    }
    protected Supplier<Number> getDbQueryTotal() {
        return ()->queryTotal;
    }


    public PrometeusService(MeterRegistry registry) {

        Gauge.builder("cons.api.total_time_ms", getApiTotalTimeMs())
                .description("total time in API exposed by console")
                .register(registry);
        Gauge.builder("cons.api.total", getApiTotalCount())
                .description("number of console API calls")
                .register(registry);
        Gauge.builder("cons.api.total_error", getApiTotalError())
                .description("number of API error response - not 200")
                .register(registry);
        Gauge.builder("cons.user.total_login", getUserTotalLoginCount())
                .description("number of login success")
                .register(registry);
        Gauge.builder("cons.api.dc_bal_total_time_ms", getApiDcBalanceTimeMs())
                .description("total time in DC balance specific API call")
                .register(registry);
        Gauge.builder("cons.api.dc_bal_total", getApiDcBalanceTotal())
                .description("total DC balance specific API call")
                .register(registry);
        Gauge.builder("cons.nova.total_time_ms", getHeliumApiTotalTimeMs())
                .description("total time in Nova API")
                .register(registry);
        Gauge.builder("cons.nova.total", getHeliumApiTotal())
                .description("number of Nova API calls")
                .register(registry);
        Gauge.builder("cons.nova.total_errors", getHeliumApiTotalError())
                .description("number of Nova API Failure")
                .register(registry);
        Gauge.builder("cons.mqtt.connection_loss", getMqttConnectionLoss())
                .description("number console mqtt disconnection")
                .register(registry);
        Gauge.builder("cons.redis.stream_error", getRedisStreamError())
                .description("number console redis stream error")
                .register(registry);
        Gauge.builder("cons.lora.uplinks", getLoRaTotalUplink())
                .description("number LoRa uplink proceeded")
                .register(registry);
        Gauge.builder("cons.lora.travel_time_ms", getLoRaTravelTime())
                .description("total uplink travel time")
                .register(registry);
        Gauge.builder("cons.lora.uplinks_bytes", getLoRaTotalBytesUplink())
                .description("total uplink bytes")
                .register(registry);
        Gauge.builder("cons.lora.join", getLoRaTotalJoin())
                .description("total join request")
                .register(registry);
        Gauge.builder("cons.lora.downlinks", getLoRaTotalDownlink())
                .description("number LoRa downlink proceeded")
                .register(registry);
        Gauge.builder("cons.lora.downlinks_bytes", getLoRaTotalBytesDownlink())
                .description("total downlink bytes")
                .register(registry);
        Gauge.builder("cons.internal.delayed_stat", getDelayedStatUpdate())
                .description("Pending delayed stat computation")
                .register(registry);
        Gauge.builder("cons.route.update.total_time_ms", getRouteUpdateTotalMs())
                .description("Total time spend in updating the nova routes")
                .register(registry);
        Gauge.builder("cons.route.update.total", getRouteUpdateTotal())
                .description("Total call to update the nova routes")
                .register(registry);
        Gauge.builder("cons.devadr.update.total_time_ms", getDevAddrUpdateTotalMs())
                .description("Total time spend in updating the devaddr sessions")
                .register(registry);
        Gauge.builder("cons.devadr.update.total", getDevAddrUpdateTotal())
                .description("Total call to update the devaddr sessions")
                .register(registry);
        Gauge.builder("cons.device.creation.total", getDeviceCreation())
                .description("Number of device created")
                .register(registry);
        Gauge.builder("cons.device.deletion.total", getDeviceDeletion())
                .description("Number of device deleted")
                .register(registry);
        Gauge.builder("cons.stat.device.total", getDeviceTotal())
                .description("Number of device in the database")
                .register(registry);
        Gauge.builder("cons.stat.user.total", getUserTotal())
                .description("Number of user in the database")
                .register(registry);
        Gauge.builder("cons.stat.tenant.total", getTenantTotal())
                .description("Number of tenants in the database")
                .register(registry);
        Gauge.builder("cons.stat.dc.total", getDcTotal())
                .description("Number of DCs in the tenants")
                .register(registry);
        Gauge.builder("cons.stat.test_device.last_seen", getLastSeenTestDev())
                .description("time since receiving a message from test device")
                .register(registry);
        Gauge.builder("cons.stat.db.query_ms", getDbQueryTotalMs())
                .description("total time spent in quering the Db for stat")
                .register(registry);
        Gauge.builder("cons.stat.db.query", getDbQueryTotal())
                .description("total queries of Db for stat")
                .register(registry);

    }

    @Autowired
    protected HeliumTenantRepository heliumTenantRepository;

    @Autowired
    protected TenantRepository tenantRepository;

    @Autowired
    protected UserRepository userRepository;

    @Autowired
    protected DeviceRepository deviceRepository;

    @Autowired
    protected ConsoleConfig consoleConfig;

    @Scheduled(fixedRateString = "${helium.prometeus.scanPeriod}", initialDelay = 1_000)
    protected void backgroundPrometeusStatsUpdate() {
        long start = Now.NowUtcMs();
        // collect metrics form DB and DB response time metrics for this
        long tenants = tenantRepository.count();
        long users = userRepository.count();
        long devices = deviceRepository.count();
        Long sumDc = heliumTenantRepository.selectSumOfDcs();
        if ( sumDc == null ) sumDc = Long.valueOf(0);

        Device d = null;
        if ( consoleConfig.getTestdeviceEui().length() == 16 ) {
            d = deviceRepository.findOneDeviceByDevEui(Tools.EuiStringToByteArray(consoleConfig.getTestdeviceEui()));
        }

        this.addQueryDb(Now.NowUtcMs()-start);
        this.updateTenantTotal(tenants);
        this.updateUserTotal(users);
        this.updateDeviceTotal(devices);
        this.updateDcTotal(sumDc.longValue());
        this.setLastSeenTestDevice(Now.NowUtcMs()); //default behavior
        if ( d != null ) {
            if ( d.getLastSeenAt() != null ) {
                this.setLastSeenTestDevice(d.getLastSeenAt().getTime());
            }
        }
        log.debug("backgroundPrometeusStatsUpdate execution has been "+(Now.NowUtcMs()-start)+"ms.");
    }

    @Autowired
    protected HeliumLogsRepository heliumLogsRepository;
    @Scheduled(fixedRateString = "${helium.prometeus.logPeriod}", initialDelay = 30_000)
    protected void backgroundLogStatsUpdate() {
        long tenants = tenantRepository.count();
        long users = userRepository.count();
        long devices = deviceRepository.count();

        ServerLogItf sl = new ServerLogItf();
        sl.setServerName(consoleConfig.getHeliumConsoleName());
        sl.setServerUrl(consoleConfig.getHeliumConsoleUrl());
        sl.setServerVersion(consoleConfig.getVersion());
        sl.setTenantNumber(tenants);
        sl.setUserNumber(users);
        sl.setDeviceNumber(devices);

        ObjectMapper mapper = new ObjectMapper();
        mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        mapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
        mapper.configure(SerializationFeature.FAIL_ON_SELF_REFERENCES, false);

        HeliumLogs hl = new HeliumLogs();
        hl.setInsertTime(Now.NowUtcMs());
        try {
            hl.setInfo(mapper.writeValueAsString(sl));
            heliumLogsRepository.save(hl);

            if ( consoleConfig.isStatReportEnable() ) {
                HttpHeaders headers = new HttpHeaders();
                ArrayList<MediaType> accept = new ArrayList<>();
                accept.add(MediaType.APPLICATION_JSON);
                headers.setAccept(accept);
                headers.add(HttpHeaders.USER_AGENT, "helium_chirp/1.0");
                RestTemplate restTemplate = new RestTemplate();
                HttpEntity<String> requestEntity = new HttpEntity<String>(hl.getInfo(), headers);
                ResponseEntity<String> responseEntity =
                        restTemplate.exchange(
                                "https://stat.helium-iot.xyz/console/1.0/misc/logs",
                                HttpMethod.POST,
                                requestEntity,
                                String.class
                        );
            }
        } catch (JsonProcessingException x) {
        } catch (Exception x){
        }

    }
}
