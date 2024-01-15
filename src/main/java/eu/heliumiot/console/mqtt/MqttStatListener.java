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
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import eu.heliumiot.console.ConsoleConfig;
import eu.heliumiot.console.ConsolePrivateConfig;
import eu.heliumiot.console.jpa.db.HeliumParameter;
import eu.heliumiot.console.jpa.db.HeliumTenant;
import eu.heliumiot.console.jpa.mongodb.DeviceFrames;
import eu.heliumiot.console.mqtt.api.HeliumDeviceActDeactItf;
import eu.heliumiot.console.mqtt.api.HeliumDeviceStatItf;
import eu.heliumiot.console.mqtt.api.HeliumTenantActDeactItf;
import eu.heliumiot.console.service.*;
import fr.ingeniousthings.tools.Now;
import io.chirpstack.json.JoinEvent;
import io.chirpstack.json.UplinkEvent;
import jakarta.annotation.PostConstruct;
import org.eclipse.paho.client.mqttv3.*;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import static eu.heliumiot.console.service.HeliumParameterService.PARAM_MQTT_HELIUM_CLIENT_ID;
import static eu.heliumiot.console.service.HeliumParameterService.PARAM_MQTT_STAT_CLIENT_ID;

@Component
public class MqttStatListener implements MqttCallback {
    private final Logger log = LoggerFactory.getLogger(this.getClass());

    protected static final int MQTT_QOS = 2;

    @Autowired
    private ConsoleConfig mqttConfig;

    @Autowired
    protected PrometeusService prometeusService;

    @Autowired
    protected HeliumParameterService heliumParameterService;

    @Autowired
    protected ConsolePrivateConfig consolePrivateConfig;

    private MqttConnectOptions connectionOptions;
    private MemoryPersistence persistence;
    private MqttClient mqttClient;

    protected String[] _topics = {"application/#"};
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

        HeliumParameter mqttClientId = heliumParameterService.getParameter(PARAM_MQTT_STAT_CLIENT_ID);
        String clientId = mqttConfig.getMqttId()+mqttClientId.getStrValue();
        this.persistence = new MemoryPersistence();
        this.connectionOptions = new MqttConnectOptions();
        try {
            log.info("MQTT DS Url :"+mqttConfig.getMqttServer());
            log.info("MQTT DS User :"+mqttConfig.getMqttLogin());
            //log.info("Password :"+mqttConfig.getPassword());
            log.info("MQTT DS Id : "+clientId);
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

            log.info("MQTT DS Starting Mqtt listener");
        } catch (MqttException me) {
            log.error("MQTT DS Init ERROR : "+me.getMessage());
        }
        return this.mqttClient;
    }

    public void connect() throws MqttException {
        log.debug("MQTT DS - Connect");
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
            log.error("MQTT DS ERROR :"+me.getMessage());
        }
    }


    private boolean pendingReconnection = false;
    /*
     * (non-Javadoc)
     * @see org.eclipse.paho.client.mqttv3.MqttCallback#connectionLost(java.lang.Throwable)
     */
    @Override
    public void connectionLost(Throwable arg0) {
        log.error("MQTT DS - Connection Lost");
        try {
            // immediate retry, then will be async
            log.error("MQTT DS - Direct reconnecting");
            this.connect();
            log.error("MQTT DS - Direct reconnected");
        } catch (MqttException me) {
            log.warn("MQTT DS - direct reconnection failed - "+me.getMessage());
            pendingReconnection = true;
        }
        prometeusService.addMqttConnectionLoss();
    }

    @Scheduled(fixedDelayString = "${helium.mqtt.reconnect.scanPeriod}", initialDelay = 30_000) // default 10s
    protected void autoReconnect() {
        if ( ! pendingReconnection ) return;
        try {
            if ( mqttClient.isConnected() ) {
                log.info("MQTT DS - reconnected");
            } else {
                log.info("MQTT DS - reconnecting");
                this.connect();
                log.info("MQTT DS - reconnected");
            }
            pendingReconnection = false;
        } catch (MqttException me) {
            log.warn("MQTT DS - reconnection failed - "+me.getMessage());
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
    protected PrivDeviceFramesService privDeviceFramesService;


    @Override
    public void messageArrived(String topicName, MqttMessage message) throws Exception {
        // Leave it blank for Publisher
        try {
            long start = Now.NowUtcMs();
            //log.info("MQTT - MessageArrived on "+topicName);
// =================================================
// INTERNAL ASYNCHRONOUS MESSAGES
// =================================================
            if (topicName.matches("application/.*/event/up$")) {
                try {
                    UplinkEvent up = mapper.readValue(message.toString(), UplinkEvent.class);
                    DeviceFrames d = privDeviceFramesService.getDevice(up.getDeviceInfo().getDevEui());
                    if ( d == null ) {
                        d = new DeviceFrames();
                    }
                    d.initFromUplinkEvent(up,consolePrivateConfig);
                    privDeviceFramesService.updateDevice(d);
                } catch (JsonProcessingException x) {
                    log.error("MQTT DS - Failed to transform Chiprstack payload "+x.getMessage());
                } catch (Exception x) {
                    log.error("MQTT DS - Exception in processing chirpstack message "+x.getMessage());
                    x.printStackTrace();
                }
            } else if ( topicName.matches("application/.*/event/join$") ) {
                try {
                    JoinEvent je = mapper.readValue(message.toString(), JoinEvent.class);
                } catch (JsonProcessingException x) {
                    log.error("MQTT DS - Failed to transform Chiprstack payload "+x.getMessage());
                } catch (Exception x) {
                    log.error("MQTT DS - Exception in processing chirpstack message "+x.getMessage());
                    x.printStackTrace();
                }
            } else {
// =================================================
// OTHERS
// =================================================
                // standard json messages
                log.debug("MQTT DS - MessageArrived on " + topicName);
                //log.info("MQTT - message "+message);
            }
            log.debug("MQTT DS processing time " + (Now.NowUtcMs() - start) + "ms for " + topicName);
        } catch (Exception e) {
            // cath Exception to not kill the MQTT process
            log.error("MQTT DS - Exception "+e.getMessage());
            e.printStackTrace();
        }
    }

}
