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
import eu.heliumiot.console.mqtt.api.HeliumDeviceActDeactItf;
import eu.heliumiot.console.mqtt.api.HeliumDeviceStatItf;
import eu.heliumiot.console.jpa.repository.TenantRepository;
import eu.heliumiot.console.mqtt.api.HeliumTenantActDeactItf;
import eu.heliumiot.console.service.HeliumDeviceService;
import eu.heliumiot.console.service.HeliumDeviceStatService;
import eu.heliumiot.console.service.HeliumTenantService;
import eu.heliumiot.console.service.PrometeusService;
import fr.ingeniousthings.tools.DateConverters;
import fr.ingeniousthings.tools.HexaConverters;
import fr.ingeniousthings.tools.Now;
import fr.ingeniousthings.tools.RandomString;
import io.chirpstack.json.DownlinkEvent;
import io.chirpstack.json.JoinEvent;
import io.chirpstack.json.UplinkEvent;
import org.postgresql.shaded.com.ongres.scram.common.bouncycastle.base64.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.eclipse.paho.client.mqttv3.*;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;

import javax.annotation.PostConstruct;

@Component
public class MqttListener implements MqttCallback {
        private final Logger log = LoggerFactory.getLogger(this.getClass());

        protected static final int MQTT_QOS = 2;

        @Autowired
        private ConsoleConfig mqttConfig;

        @Autowired
        protected PrometeusService prometeusService;


        private MqttConnectOptions connectionOptions;
        private MemoryPersistence persistence;
        private MqttClient mqttClient;

        protected String _topics[] = {"application/#","helium/#"};
        protected int _qos[] = { MQTT_QOS,MQTT_QOS };

        @PostConstruct
        public MqttClient initMqtt() {

                String clientId = mqttConfig.getMqttId()+RandomString.getRandomString(4);
                this.persistence = new MemoryPersistence();
                this.connectionOptions = new MqttConnectOptions();
                try {
                        log.info("Url :"+mqttConfig.getMqttServer());
                        log.info("User :"+mqttConfig.getMqttLogin());
                        //log.info("Password :"+mqttConfig.getPassword());
                        //log.info("Id : "+clientId);
                        this.mqttClient = new MqttClient(mqttConfig.getMqttServer(), clientId, persistence);
                        this.connectionOptions.setCleanSession(true);
                        this.connectionOptions.setAutomaticReconnect(true);
                        this.connectionOptions.setUserName(mqttConfig.getMqttLogin());
                        this.connectionOptions.setPassword(mqttConfig.getMqttPassword().toCharArray());
                        this.connectionOptions.setConnectionTimeout(10);
                        this.mqttClient.connect(this.connectionOptions);
                        this.mqttClient.setCallback(this);
                        this.mqttClient.subscribe(_topics,_qos);
                        log.info("Starting Mqtt listener");
                } catch (MqttException me) {
                        log.error("MQTT ERROR", me);
                }
                return this.mqttClient;
        }

        // stop the listener once we request a stop of the application
        public void stopListner() {
                try {
                        this.mqttClient.unsubscribe (_topics);
                } catch (MqttException me) {
                        log.error("MQTT ERROR", me);
                }
        }


        /*
         * (non-Javadoc)
         * @see org.eclipse.paho.client.mqttv3.MqttCallback#connectionLost(java.lang.Throwable)
         */
        @Override
        public void connectionLost(Throwable arg0) {
                log.info("MQTT - Connection Lost");
                prometeusService.addMqttConnectionLoss();
                // @TODO ... make it working differently to reconnect
                try {
                        this.mqttClient.connect(this.connectionOptions);
                        log.info("MQTT - reconnecting");
                } catch (MqttException me) {
                        log.error("MQTT ERROR", me);
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
        protected HeliumTenantService heliumTenantService;

        @Autowired
        protected HeliumDeviceStatService heliumDeviceStatService;

        @Autowired
        protected HeliumDeviceService heliumDeviceService;

        @Override
        public void messageArrived(String topicName, MqttMessage message) throws Exception {
                // Leave it blank for Publisher
                long start = Now.NowUtcMs();
                //log.info("MQTT - MessageArrived on "+topicName);
                ObjectMapper mapper = new ObjectMapper();
                mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
                mapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
                mapper.configure(SerializationFeature.FAIL_ON_SELF_REFERENCES, false);

                // some of the messages are protobuf format
                if ( topicName.matches("application/.*/event/up$") ) {
                        // Prefer MQTT to get the uplink information because the data is decoded
                        try {

                                UplinkEvent up = mapper.readValue(message.toString(), UplinkEvent.class);
                                log.debug("UPLINK Dev: " + up.getDeviceInfo().getDevEui() + " Adr:" + up.getDevAddr() + " duplicates:" + up.getRxInfo().size() + " size: "+Base64.decode(up.getData()).length);
                                heliumTenantService.processUplink(
                                        up.getDeviceInfo().getTenantId(),
                                        up.getDeviceInfo().getDevEui(),
                                        Base64.decode(up.getData()).length,
                                        up.getRxInfo().size() - 1
                                );
                                prometeusService.addLoRaUplink(
                                        Now.NowUtcMs() - DateConverters.StringDateToMs(up.getTime()),
                                        Base64.decode(up.getData()).length
                                );

                        } catch (JsonProcessingException x) {
                                log.error("MQTT - failed to parse App uplink - " + x.getMessage());
                                x.printStackTrace();
                        } catch (Exception x) {
                                x.printStackTrace();
                        }

                } else if ( topicName.matches("application/.*/event/down$") ) {
                        // REDIS will be prefered because MQTT Json does not have size
                        // And MQTT does not trace Uplink
                        /*
                        try {

                                DownlinkEvent down = mapper.readValue(message.toString(), DownlinkEvent.class);
                                log.debug("Dev: " + down.getDeviceInfo().getDevEui());
                                heliumTenantService.processDownlink(
                                        down.getDeviceInfo().getTenantId(),
                                        down.getDeviceInfo().getDevEui(),
                                        12,
                                );

                        } catch (JsonProcessingException x) {
                                log.error("MQTT - failed to parse App downlink - " + x.getMessage());
                                x.printStackTrace();
                        } catch (Exception x) {
                                x.printStackTrace();
                        }
                        */
                } else if ( topicName.matches("application/.*/event/join$") ) {
                        try {
                                JoinEvent e = mapper.readValue(message.toString(), JoinEvent.class);
                                log.debug("JOIN - Dev: " + e.getDeviceInfo().getDeviceName() + " Adr:" + e.getDevAddr() + " timestamp :" + DateConverters.StringDateToMs(e.getTime()));
                                heliumTenantService.processJoin(
                                        e.getDeviceInfo().getTenantId(),
                                        e.getDeviceInfo().getDevEui(),
                                        e.getDevAddr()
                                );
                                prometeusService.addLoRaJoin();
                                prometeusService.addLoRaUplink(
                                        Now.NowUtcMs() - DateConverters.StringDateToMs(e.getTime()),
                                        0
                                );

                        } catch (JsonProcessingException x) {
                                log.error("MQTT - failed to parse App JOIN - " + x.getMessage());
                                x.printStackTrace();
                        } catch (Exception x) {
                                x.printStackTrace();
                        }
                } else if ( topicName.matches("application/.*/event/log$") ) {
                        // just do nothing for this one

// =================================================
// INTERNAL ASYNCHRONOUS MESSAGES
// =================================================

                } else if ( topicName.matches("helium/device/stats/.*") ) {
                        HeliumDeviceStatItf e = mapper.readValue(message.toString(), HeliumDeviceStatItf.class);
                        heliumDeviceStatService.updateDeviceStat(e);
                        prometeusService.delDelayedStatUpdate();
                } else if ( topicName.matches("helium/device/deactivate/.*") ) {
                        HeliumDeviceActDeactItf e = mapper.readValue(message.toString(), HeliumDeviceActDeactItf.class);
                        heliumDeviceService.processDeviceDeactivation(e);
                        prometeusService.delDelayedStatUpdate();
                } else if ( topicName.matches("helium/device/activate/.*") ) {
                        HeliumDeviceActDeactItf e = mapper.readValue(message.toString(), HeliumDeviceActDeactItf.class);
                        heliumDeviceService.processDeviceReactivation(e);
                        prometeusService.delDelayedStatUpdate();
                } else if ( topicName.matches("helium/tenant/manage/.*") ) {
                        HeliumTenantActDeactItf e = mapper.readValue(message.toString(), HeliumTenantActDeactItf.class);
                        if( e.isActivateTenant() ) {
                                heliumDeviceService.processTenantReactivation(e.getTenantId());
                        } else if ( e.isDeactivateTenant() ) {
                                heliumDeviceService.processTenantDeactivation(e.getTenantId());
                        } else {
                                log.error("Invalid state for manage tenant request");
                        }
                        prometeusService.delDelayedStatUpdate();
                } else {
// =================================================
// OTHERS
// =================================================

                        // standard json messages
                        log.info("MQTT - MessageArrived on "+topicName);
                        log.info("MQTT - message "+message);
                }
                log.debug("MQTT processing time "+(Now.NowUtcMs()-start)+"ms for "+topicName);
        }


}
