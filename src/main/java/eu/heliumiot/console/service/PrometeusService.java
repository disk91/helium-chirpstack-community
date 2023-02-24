package eu.heliumiot.console.service;

import fr.ingeniousthings.tools.Now;
import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.Gauge;
import io.micrometer.core.instrument.MeterRegistry;
import org.springframework.stereotype.Component;

import java.util.function.Supplier;

@Component
public class PrometeusService {

    private long apiTotalTimeMs = 0;        // time spent in API
    private long apiTotalCount = 0;         // number of API calls
    private long apiTotalError = 0;         // number of API error

    private long apiDcBalanceTimeMs = 0;    // time specific for the Dc balance API
    private long apiDcBalanceTotal = 0;     // number of API call

    private long userTotalLoginCount = 0;        // number of successful login

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

    protected Supplier<Number> getApiDcBallanceTotal() {
        return ()->apiDcBalanceTotal;
    }



    public PrometeusService(MeterRegistry registry) {

        Gauge.builder("cons.api.total_time_ms", getApiTotalTimeMs()).register(registry);
        Gauge.builder("cons.api.total", getApiTotalCount()).register(registry);
        Gauge.builder("cons.user.total_login", getUserTotalLoginCount()).register(registry);
        Gauge.builder("cons.api.total_error", getApiTotalError()).register(registry);
        Gauge.builder("cons.api.dc_bal_total_time_ms", getApiDcBalanceTimeMs()).register(registry);
        Gauge.builder("cons.api.dc_bal_total", getApiDcBallanceTotal()).register(registry);

    }

}
