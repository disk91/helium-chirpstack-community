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
import eu.heliumiot.console.service.HeliumDeviceStatService;
import eu.heliumiot.console.service.HeliumTenantService;
import fr.ingeniousthings.tools.DateConverters;
import fr.ingeniousthings.tools.HexaConverters;
import fr.ingeniousthings.tools.Now;
import fr.ingeniousthings.tools.RandomString;
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

        private MqttConnectOptions connectionOptions;
        private MemoryPersistence persistence;
        private MqttClient mqttClient;

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
                        this.mqttClient.subscribe("#",2);
                        log.info("Starting Mqtt listener");
                } catch (MqttException me) {
                        log.error("MQTT ERROR", me);
                }
                return this.mqttClient;
        }

        public void publishMessage(String topic, String message, int qos) {

                try {
                        MqttMessage mqttmessage = new MqttMessage(message.getBytes());
                        mqttmessage.setQos(qos);
                        //log.info("MQTT : Topic("+topic+") Mess("+mqttmessage+")");
                        this.mqttClient.publish(topic, mqttmessage);
                } catch (MqttException me) {
                        log.error("MessagePublish Error", me);
                }

        }

        /*
         * (non-Javadoc)
         * @see org.eclipse.paho.client.mqttv3.MqttCallback#connectionLost(java.lang.Throwable)
         */
        @Override
        public void connectionLost(Throwable arg0) {
                log.info("MQTT - Connection Lost");
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
        protected TenantRepository tenantRepository;

        @Autowired
        protected HeliumTenantService heliumTenantService;

        @Autowired
        protected HeliumDeviceStatService heliumDeviceStatService;

        @Override
        public void messageArrived(String topicName, MqttMessage message) throws Exception {
                // Leave it blank for Publisher
                long start = Now.NowUtcMs();
                log.info("MQTT - MessageArrived on "+topicName);
                ObjectMapper mapper = new ObjectMapper();
                mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
                mapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
                mapper.configure(SerializationFeature.FAIL_ON_SELF_REFERENCES, false);

                // some of the messages are protobuf format
                if ( topicName.matches(".*/gateway/.*/event/up$") ) {
                        // uplink
                        /*
                        log.info("MQTT - uplink");
                        try {
                                UplinkFrame m = UplinkFrame.parseFrom(message.getPayload());
                                log.info("ts :"+m.getRxInfo().getTime().getSeconds()+" :"+ HexaConverters.byteToHexString(m.getPhyPayload().toByteArray()));
                        } catch (Exception e) { e.printStackTrace();}
                        */
                } else if ( topicName.matches(".*/gateway/.*/state/conn$") ) {
                        // Connection State
                        /*
                        log.info("MQTT - Connection State");
                        try {
                                ConnState m= ConnState.parseFrom(message.getPayload());
                                log.info(m.getGatewayId()+":"+m.getState());
                        } catch (Exception e) { e.printStackTrace();}
                        */
                } else if ( topicName.matches(".*/gateway/.*/event/stats$") ) {
                        // Connection State
                        /*
                        log.info("MQTT - Gw State");
                        try {
                                GatewayStats m= GatewayStats.parseFrom(message.getPayload());
                                log.info(m.getGatewayId()+" Rx:"+m.getRxPacketsReceived());
                        } catch (Exception e) { e.printStackTrace();}
                        */
                } else if ( topicName.matches("application/.*/event/up$") ) {
                        // Application message .. this is not protobuf but json
                        //log.info("MQTT - App uplink");
                        try {
                                UplinkEvent e = mapper.readValue(message.toString(), UplinkEvent.class);
                                log.info("Dev: " + e.getDeviceInfo().getDeviceName() + " Adr:" + e.getDevAddr() + " timestamp :" + DateConverters.StringDateToMs(e.getTime()));
                                //Tenant t = tenantRepository.findOneTenantById(UUID.fromString(e.getDeviceInfo().getTenantId()));
                                //if (t != null) {
                                        //log.info("Tenant " + t.getName() + " with " + t.getMax_device_count());
                                        //HeliumTenant ht = heliumTenantService.getHeliumTenant(e.getDeviceInfo().getTenantId());
                                        //log.info("Tenant DCs is " + ht.getDcBalance());
                                        HeliumDeviceStatItf i = heliumTenantService.processUplink(
                                                e.getDeviceInfo().getTenantId(),
                                                e.getDeviceInfo().getDevEui(),
                                                Base64.decode(e.getData()).length,
                                                e.getRxInfo().size() - 1
                                        );
                                        // Async publishing for device stats
                                        if ( ! i.isEmpty() ) {
                                                try {
                                                        this.publishMessage(
                                                                "helium/device/stats/" + e.getDeviceInfo().getDevEui(),
                                                                mapper.writeValueAsString(i),
                                                                2
                                                        );
                                                } catch (Exception x) {
                                                        log.error("Something went wrong with publishing on MQTT");
                                                        log.error(x.getMessage());
                                                }
                                        }
                                        /*
                                } else {
                                        log.error("Tenant not found");
                                }
                                */
                        } catch (JsonProcessingException x) {
                                log.error("MQTT - failed to parse App uplink - " + x.getMessage());
                                x.printStackTrace();
                        } catch (Exception x) {
                                x.printStackTrace();
                        }
                        //log.info("" + message);
                        //log.info(""+ Base64.encodeBytes(message.getPayload()));
                        //log.info(""+HexaConverters.byteToHexStringWithSpace(message.getPayload()));

                } else if ( topicName.matches("application/.*/event/join$") ) {
                        try {
                                JoinEvent e = mapper.readValue(message.toString(), JoinEvent.class);
                                log.info("Dev: " + e.getDeviceInfo().getDeviceName() + " Adr:" + e.getDevAddr() + " timestamp :" + DateConverters.StringDateToMs(e.getTime()));
                                HeliumDeviceStatItf i = heliumTenantService.processJoin(
                                        e.getDeviceInfo().getTenantId(),
                                        e.getDeviceInfo().getDevEui()
                                );
                                // Async publishing for device stats
                                if (!i.isEmpty()) {
                                        try {
                                                this.publishMessage(
                                                        "helium/device/stats/" + e.getDeviceInfo().getDevEui(),
                                                        mapper.writeValueAsString(i),
                                                        2
                                                );
                                        } catch (Exception x) {
                                                log.error("Something went wrong with publishing on MQTT");
                                                log.error(x.getMessage());
                                        }
                                }
                        } catch (JsonProcessingException x) {
                                log.error("MQTT - failed to parse App uplink - " + x.getMessage());
                                x.printStackTrace();
                        } catch (Exception x) {
                                x.printStackTrace();
                        }
                } else if ( topicName.matches(".*/gateway/.*/event/ack$") ) {
                        // Downlink case, this is the best event we have
                        log.info(""+ Base64.encode(message.getPayload()));
                        log.info(""+ HexaConverters.byteToHexStringWithSpace(message.getPayload()));

                        /*
                        try {
                                Down m = DownlinkFrame.parseFrom(message.getPayload());
                                log.info("ts :"+m..getTime().getSeconds()+" :"+ HexaConverters.byteToHexString(m.getPhyPayload().toByteArray()));
                        } catch (Exception e) { e.printStackTrace();}
*/
                } else if ( topicName.matches(".*/gateway/.*/command/down$") ) {
                        // Downlink case, this is the best event we have
                        log.info(""+ Base64.encode(message.getPayload()));
                        log.info(""+ HexaConverters.byteToHexStringWithSpace(message.getPayload()));
                        /*
                        try {
                                Down m = DownlinkFrame.parseFrom(message.getPayload());
                                log.info("ts :"+m..getTime().getSeconds()+" :"+ HexaConverters.byteToHexString(m.getPhyPayload().toByteArray()));
                        } catch (Exception e) { e.printStackTrace();}
*/

                } else if ( topicName.matches("helium/device/stats/.*") ) {
                        HeliumDeviceStatItf e = mapper.readValue(message.toString(), HeliumDeviceStatItf.class);
                        heliumDeviceStatService.updateDeviceStat(e);
                } else if ( topicName.matches("helium/device/deactivate/.*") ) {
                        HeliumDeviceActDeactItf e = mapper.readValue(message.toString(), HeliumDeviceActDeactItf.class);
                        // @Todo process the deactivation message
                        log.error("Need to process device deactivation message");
                } else if ( topicName.matches("helium/device/activate/.*") ) {
                        HeliumDeviceActDeactItf e = mapper.readValue(message.toString(), HeliumDeviceActDeactItf.class);
                        // @Todo process the activation message
                        log.error("Need to process device activation message");
                } else if ( topicName.matches("helium/tenant/deactivate/.*") ) {
                        //HeliumDeviceActDeactItf e = mapper.readValue(message.toString(), HeliumDeviceActDeactItf.class);
                        // @Todo process the tenant deactivation message (out of DCs)
                        log.error("Need to process tenant activation message");
                } else if ( topicName.matches("helium/tenant/reactivate/.*") ) {
                        //HeliumDeviceActDeactItf e = mapper.readValue(message.toString(), HeliumDeviceActDeactItf.class);
                        // @Todo process the tenant reactivation message (DCs amount increased)
                        log.error("Need to process tenant activation message");
                } else {
                        // standard json messages
                        log.info("MQTT - message "+message);
                }
                log.debug("MQTT processing time "+(Now.NowUtcMs()-start)+"ms");
        }


}
