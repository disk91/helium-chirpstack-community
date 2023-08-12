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
import eu.heliumiot.console.jpa.repository.TenantRepository;
import eu.heliumiot.console.mqtt.api.HeliumDeviceActDeactItf;
import eu.heliumiot.console.mqtt.api.HeliumDeviceStatItf;
import eu.heliumiot.console.service.HeliumDeviceStatService;
import eu.heliumiot.console.service.HeliumTenantService;
import fr.ingeniousthings.tools.DateConverters;
import fr.ingeniousthings.tools.HexaConverters;
import fr.ingeniousthings.tools.Now;
import fr.ingeniousthings.tools.RandomString;
import io.chirpstack.json.JoinEvent;
import io.chirpstack.json.UplinkEvent;
import org.eclipse.paho.client.mqttv3.*;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;
import org.postgresql.shaded.com.ongres.scram.common.bouncycastle.base64.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
public class MqttSender implements MqttCallback {
        private final Logger log = LoggerFactory.getLogger(this.getClass());

        protected static final int MQTT_QOS = 2;

        @Autowired
        private ConsoleConfig mqttConfig;

        private MqttConnectOptions connectionOptions;
        private MemoryPersistence persistence;
        private MqttClient mqttClient;

        @PostConstruct
        public MqttClient initMqtt() {

                String clientId = mqttConfig.getMqttId() + RandomString.getRandomString(4);
                this.persistence = new MemoryPersistence();
                this.connectionOptions = new MqttConnectOptions();
                try {
                        log.info("MQTT S Url :" + mqttConfig.getMqttServer());
                        log.info("MQTT S User :" + mqttConfig.getMqttLogin());
                        //log.info("Password :"+mqttConfig.getPassword());
                        log.info("MQTT S Id : "+clientId);
                        this.mqttClient = new MqttClient(mqttConfig.getMqttServer(), clientId, persistence);
                        this.connectionOptions.setCleanSession(true);
                        this.connectionOptions.setAutomaticReconnect(false); // managed manually
                        this.connectionOptions.setConnectionTimeout(5);
                        this.connectionOptions.setKeepAliveInterval(30);
                        this.connectionOptions.setUserName(mqttConfig.getMqttLogin());
                        this.connectionOptions.setPassword(mqttConfig.getMqttPassword().toCharArray());
                        this.connect();
                        log.info("MQTT S Starting Mqtt listener");
                } catch (MqttException me) {
                        log.error("MQTT S ERROR : "+me.getMessage());
                }
                return this.mqttClient;
        }

        public void connect() throws MqttException {
                log.debug("MQTT S - Connect");
                this.mqttClient.connect(this.connectionOptions);
                this.mqttClient.setCallback(this);
        }

        public void stop() {
                try {
                        this.mqttClient.disconnect();
                        this.mqttClient.close();
                } catch (MqttException me) {
                        log.error("MQTT S ERROR :"+me.getMessage());
                }
        }


        private boolean pendingReconnection = false;

        @Override
        public void connectionLost(Throwable arg0) {
                log.error("MQTT S - Connection Lost");
                try {
                        // immediate retry, then will be async
                        this.connect();
                } catch (MqttException me) {
                        pendingReconnection = true;
                }
        }

        @Scheduled(fixedDelayString = "${helium.mqtt.reconnect.scanPeriod}", initialDelay = 30_000) // default 10s
        protected void autoReconnect() {
                if ( ! pendingReconnection ) return;
                try {
                        if ( mqttClient.isConnected() ) {
                                log.info("MQTT S - reconnected");
                                pendingReconnection = false;
                        }
                        this.connect();
                        log.info("MQTT S - reconnected");
                        pendingReconnection = false;
                } catch (MqttException me) {
                        log.warn("MQTT S - reconnection failed - "+me.getMessage());
                }
        }


        public void publishMessage(String topic, String message, int qos) {

                try {
                        MqttMessage mqttmessage = new MqttMessage(message.getBytes());
                        mqttmessage.setQos(qos);
                        //log.info("MQTT : Topic("+topic+") Mess("+mqttmessage+")");
                        this.mqttClient.publish(topic, mqttmessage);
                } catch (MqttException me) {
                        log.error("MQTT S - MessagePublish Error", me);
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

        @Override
        public void messageArrived(String topicName, MqttMessage message) throws Exception {

        }

}