package eu.heliumiot.console.service;

import fr.ingeniousthings.tools.Now;
import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.Gauge;
import io.micrometer.core.instrument.MeterRegistry;
import org.springframework.stereotype.Component;

import java.util.function.Supplier;

@Component
public class PrometeusService {

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

    // ------- devices stats
    private long dcTotal = 0;                   // total number of DCs in the tenants
    private long deviceTotal = 0;               // number of devices in db
    private long tenantTotal = 0;               // number of tenants
    private long userTotal = 0;                 // number of users



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

    }

}
