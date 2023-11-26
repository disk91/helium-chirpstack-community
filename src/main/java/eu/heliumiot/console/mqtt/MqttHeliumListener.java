/*
 * Copyright (c) - Paul Pinault (aka disk91) - 2022.
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
package eu.heliumiot.console.mqtt;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import eu.heliumiot.console.ConsoleConfig;
import eu.heliumiot.console.jpa.db.HeliumParameter;
import eu.heliumiot.console.jpa.db.HeliumTenant;
import eu.heliumiot.console.mqtt.api.HeliumDeviceActDeactItf;
import eu.heliumiot.console.mqtt.api.HeliumDeviceStatItf;
import eu.heliumiot.console.mqtt.api.HeliumTenantActDeactItf;
import eu.heliumiot.console.service.*;
import fr.ingeniousthings.tools.Now;
import org.eclipse.paho.client.mqttv3.*;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

import static eu.heliumiot.console.service.HeliumParameterService.PARAM_MQTT_HELIUM_CLIENT_ID;

@Component
public class MqttHeliumListener implements MqttCallback {
    private final Logger log = LoggerFactory.getLogger(this.getClass());

    protected static final int MQTT_QOS = 2;

    @Autowired
    private ConsoleConfig mqttConfig;

    @Autowired
    protected PrometeusService prometeusService;

    @Autowired
    protected HeliumParameterService heliumParameterService;

    private MqttConnectOptions connectionOptions;
    private MemoryPersistence persistence;
    private MqttClient mqttClient;

    protected String[] _topics = {"helium/#"};
    protected int[] _qos = { MQTT_QOS };

    ObjectMapper mapper = new ObjectMapper();

    protected boolean requestToStop = false;
    protected boolean stopped = false;

    public void stopMqttListener() {
        this.requestToStop = true;
        this.stop();
        this.stopped = true;
    }

    @PostConstruct
    public MqttClient initMqtt() {

        HeliumParameter mqttClientId = heliumParameterService.getParameter(PARAM_MQTT_HELIUM_CLIENT_ID);
        String clientId = mqttConfig.getMqttId()+mqttClientId.getStrValue();
        this.persistence = new MemoryPersistence();
        this.connectionOptions = new MqttConnectOptions();
        try {
            log.info("MQTT H Url :"+mqttConfig.getMqttServer());
            log.info("MQTT H User :"+mqttConfig.getMqttLogin());
            //log.info("Password :"+mqttConfig.getPassword());
            log.info("MQTT H Id : "+clientId);
            this.mqttClient = new MqttClient(mqttConfig.getMqttServer(), clientId, persistence);
            this.connectionOptions.setCleanSession(false);          // restart by processing pending events
            this.connectionOptions.setAutomaticReconnect(false);    // reconnect managed manually
            this.connectionOptions.setConnectionTimeout(5);         // do not wait more than 5s to reconnect
            this.connectionOptions.setKeepAliveInterval(60);
            this.connectionOptions.setUserName(mqttConfig.getMqttLogin());
            this.connectionOptions.setPassword(mqttConfig.getMqttPassword().toCharArray());
            this.connect();

            this.mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
            this.mapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
            this.mapper.configure(SerializationFeature.FAIL_ON_SELF_REFERENCES, false);

            log.info("MQTT H Starting Mqtt listener");
        } catch (MqttException me) {
            log.error("MQTT H Init ERROR : "+me.getMessage());
        }
        return this.mqttClient;
    }

    public void connect() throws MqttException {
        log.debug("MQTT H - Connect");
        this.mqttClient.connect(this.connectionOptions);
        this.mqttClient.setCallback(this);
        this.mqttClient.subscribe(_topics,_qos);
    }

    // stop the listener once we request a stop of the application
    public void stop() {
        try {
            this.mqttClient.unsubscribe(_topics);
            this.mqttClient.disconnect();
            this.mqttClient.close();
        } catch (MqttException me) {
            log.error("MQTT H ERROR :"+me.getMessage());
        }
    }


    private boolean pendingReconnection = false;
    /*
     * (non-Javadoc)
     * @see org.eclipse.paho.client.mqttv3.MqttCallback#connectionLost(java.lang.Throwable)
     */
    @Override
    public void connectionLost(Throwable arg0) {
        log.error("MQTT H - Connection Lost");
        try {
            // immediate retry, then will be async
            log.error("MQTT H - Direct reconnecting");
            this.connect();
            log.error("MQTT H - Direct reconnected");
        } catch (MqttException me) {
            log.warn("MQTT H - direct reconnection failed - "+me.getMessage());
            pendingReconnection = true;
        }
        prometeusService.addMqttConnectionLoss();
    }

    @Scheduled(fixedDelayString = "${helium.mqtt.reconnect.scanPeriod}", initialDelay = 30_000) // default 10s
    protected void autoReconnect() {
        if ( ! pendingReconnection ) return;
        try {
            if ( mqttClient.isConnected() ) {
                log.info("MQTT H - reconnected");
            } else {
                log.info("MQTT H - reconnecting");
                this.connect();
                log.info("MQTT H - reconnected");
            }
            pendingReconnection = false;
        } catch (MqttException me) {
            log.warn("MQTT H - reconnection failed - "+me.getMessage());
        }
    }


    /*
     * (non-Javadoc)
     * @see org.eclipse.paho.client.mqttv3.MqttCallback#deliveryComplete(org.eclipse.paho.client.mqttv3.IMqttDeliveryToken)
     */
    @Override
    public void deliveryComplete(IMqttDeliveryToken arg0) {
        // log.info("MQTT - delivery completed");
    }

    @Autowired
    protected HeliumDeviceStatService heliumDeviceStatService;

    @Autowired
    protected HeliumDeviceService heliumDeviceService;

    @Autowired
    protected UserService userService;

    @Override
    public void messageArrived(String topicName, MqttMessage message) throws Exception {
        // Leave it blank for Publisher
        try {
            long start = Now.NowUtcMs();
            //log.info("MQTT - MessageArrived on "+topicName);
// =================================================
// INTERNAL ASYNCHRONOUS MESSAGES
// =================================================

            if (topicName.matches("helium/device/stats/.*")) {
                HeliumDeviceStatItf e = mapper.readValue(message.toString(), HeliumDeviceStatItf.class);
                heliumDeviceStatService.updateDeviceStat(e);
                prometeusService.delDelayedStatUpdate();
            } else if (topicName.matches("helium/device/deactivate/.*")) {
                HeliumDeviceActDeactItf e = mapper.readValue(message.toString(), HeliumDeviceActDeactItf.class);
                heliumDeviceService.processDeviceDeactivation(e);
                prometeusService.delDelayedStatUpdate();
            } else if (topicName.matches("helium/device/activate/.*")) {
                HeliumDeviceActDeactItf e = mapper.readValue(message.toString(), HeliumDeviceActDeactItf.class);
                heliumDeviceService.processDeviceReactivation(e);
                prometeusService.delDelayedStatUpdate();
            } else if (topicName.matches("helium/tenant/manage/.*")) {
                HeliumTenantActDeactItf e = mapper.readValue(message.toString(), HeliumTenantActDeactItf.class);
                if (e.isActivateTenant()) {
                    heliumDeviceService.processTenantReactivation(e.getTenantId());
                } else if (e.isDeactivateTenant()) {
                    heliumDeviceService.processTenantDeactivation(e.getTenantId());
                } else {
                    log.error("Invalid state for manage tenant request");
                }
                prometeusService.delDelayedStatUpdate();
            } else if (topicName.matches("helium/tenant/alarm/.*")) {
                HeliumTenant t = mapper.readValue(message.toString(), HeliumTenant.class);
                log.info("FireDcBalanceAlarm for "+t.getTenantUUID());
                userService.fireDcBalanceAlarm(t);
            } else {
// =================================================
// OTHERS
// =================================================

                // standard json messages
                log.debug("MQTT H - MessageArrived on " + topicName);
                //log.info("MQTT - message "+message);
            }
            log.debug("MQTT H processing time " + (Now.NowUtcMs() - start) + "ms for " + topicName);
        } catch (Exception e) {
            // cath Exception to not kill the MQTT process
            log.error("MQTT H - Exception "+e.getMessage());
            e.printStackTrace();
        }
    }

}
