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


import eu.heliumiot.console.ConsoleApplication;
import eu.heliumiot.console.mqtt.MqttHeliumListener;
import eu.heliumiot.console.mqtt.MqttListener;
import eu.heliumiot.console.mqtt.MqttSender;
import eu.heliumiot.console.redis.RedisDeviceEventStreamListener;
import eu.heliumiot.console.redis.RedisDeviceFrameStreamListener;
import fr.ingeniousthings.tools.Now;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PreDestroy;

@Service
public class ExitService {

    private final Logger log = LoggerFactory.getLogger(this.getClass());

    private boolean exiting = false;

    /**
     * Return true when exiting - do not start any async process
     * @return
     */
    public boolean isExiting() {
        return this.exiting;
    }

    @Autowired
    private HeliumDeviceService heliumDeviceService;

    @Autowired
    private MqttListener mqttListener;

    @Autowired
    private MqttHeliumListener mqttHeliumListener;

    @Autowired
    private MqttSender mqttSender;

    @Autowired
    private RedisDeviceFrameStreamListener redisStreamMetaListener;

    @Autowired
    private RedisDeviceEventStreamListener redisDeviceEventStreamListener;

    @Autowired
    private HeliumDeviceStatService heliumDeviceStatService;

    @Autowired
    private HeliumTenantService heliumTenantService;

    @Autowired
    private HeliumDeviceCacheService heliumDeviceCacheService;

    @Autowired
    private HeliumTenantSetupService heliumTenantSetupService;

    @Autowired
    private NovaService novaService;

    @PreDestroy
    public void onCallExit() {

        if (this.exiting) return;

        // ------------------------------------------------
        log.info("Exit - stop listeners");
        mqttListener.stopMqttListener();
        mqttHeliumListener.stopMqttListener();
        mqttSender.stop();

        redisStreamMetaListener.stopService();
        long s = Now.NowUtcMs();
        long d = s;
        while ( !redisStreamMetaListener.hasStopped() ) {
            if ( (Now.NowUtcMs() - d) > 1000 ) {
                log.warn("Waiting for redis listener stop");
                d+=1000;
            }
            if ( (Now.NowUtcMs() - s) > 15_000 ) {
                log.error("Redis listener not stopping, force stop");
                break;
            }
        }

        redisDeviceEventStreamListener.stopService();
        s = Now.NowUtcMs();
        d = s;
        while ( !redisDeviceEventStreamListener.hasStopped() ) {
            if ( (Now.NowUtcMs() - d) > 1000 ) {
                log.warn("Waiting for redis event listener stop");
                d+=1000;
            }
            if ( (Now.NowUtcMs() - s) > 15_000 ) {
                log.error("Redis event listener not stopping, force stop");
                break;
            }
        }

        // ------------------------------------------------
        log.info("Exit - stop services");
        heliumDeviceService.stopService();
        heliumDeviceStatService.stopService();
        heliumTenantService.stopService();
        heliumTenantSetupService.stopService();
        heliumDeviceCacheService.stopService();
        novaService.stopService();


        int services = 0;
        s = Now.NowUtcMs();
        d = s;
        do {
            services = 0;
            if ( ! heliumDeviceService.hasStopped() ) services++;
            if ( ! heliumDeviceStatService.hasStopped() ) services++;
            if ( ! heliumTenantService.hasStopped() ) services++;
            if ( ! heliumTenantSetupService.hasStopped() ) services++;
            if ( ! heliumDeviceCacheService.hasStopped() ) services++;
            if ( ! novaService.hasStopped() ) services++;

            if ( (Now.NowUtcMs() - d) > 1000 ) {
                log.error("Waiting for "+services+" services to stop");
                d+=1000;
            }
            if ( (Now.NowUtcMs() - s) > 30_000 ) {
                log.error("Services not stopping, force stop");
                break;
            }
        } while (services > 0);

        // ------------------------------------------------
        log.info("Exit - commit caches");
        heliumDeviceStatService.stopHeliumDeviceStatServiceCache();
        heliumTenantService.stopHeliumTenantServiceCache();

        log.info("Exit - completed");
        this.exiting = true;
        try {
            // not ideal but let some time to finish everythings
            Thread.sleep(1_000);
        } catch (InterruptedException e) {};
        ConsoleApplication.exit();
    }


}

