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
import eu.heliumiot.console.jpa.db.HeliumParameter;
import eu.heliumiot.console.mqtt.api.HeliumDeviceActDeactItf;
import eu.heliumiot.console.mqtt.api.HeliumDeviceStatItf;
import eu.heliumiot.console.jpa.repository.TenantRepository;
import eu.heliumiot.console.mqtt.api.HeliumTenantActDeactItf;
import eu.heliumiot.console.service.*;
import fr.ingeniousthings.tools.DateConverters;
import fr.ingeniousthings.tools.HexaConverters;
import fr.ingeniousthings.tools.Now;
import fr.ingeniousthings.tools.RandomString;
import io.chirpstack.api.gw.UplinkFrame;
import io.chirpstack.json.DownlinkEvent;
import io.chirpstack.json.JoinEvent;
import io.chirpstack.json.UplinkEvent;
import org.postgresql.shaded.com.ongres.scram.common.bouncycastle.base64.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.eclipse.paho.client.mqttv3.*;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;

import javax.annotation.PostConstruct;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Set;

import static eu.heliumiot.console.service.HeliumParameterService.PARAM_MQTT_CLIENT_ID;

@Component
public class MqttListener implements MqttCallback {
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

    protected String[] _topics = {"application/#","helium/#","+/gateway/+/event/up"};
    protected int[] _qos = { MQTT_QOS,MQTT_QOS,MQTT_QOS };

    ObjectMapper mapper = new ObjectMapper();

    @PostConstruct
    public MqttClient initMqtt() {

        HeliumParameter mqttClientId = heliumParameterService.getParameter(PARAM_MQTT_CLIENT_ID);
        String clientId = mqttConfig.getMqttId()+mqttClientId.getStrValue();
        this.persistence = new MemoryPersistence();
        this.connectionOptions = new MqttConnectOptions();
        try {
            log.info("MQTT Url :"+mqttConfig.getMqttServer());
            log.info("MQTT User :"+mqttConfig.getMqttLogin());
            //log.info("Password :"+mqttConfig.getPassword());
            log.info("MQTT Id : "+clientId);
            this.mqttClient = new MqttClient(mqttConfig.getMqttServer(), clientId, persistence);
            this.connectionOptions.setCleanSession(false);          // restart by processing pending events
            this.connectionOptions.setAutomaticReconnect(false);    // reconnect managed manually
            this.connectionOptions.setConnectionTimeout(5);         // do not wait more than 5s to reconnect
            this.connectionOptions.setKeepAliveInterval(30);
            this.connectionOptions.setUserName(mqttConfig.getMqttLogin());
            this.connectionOptions.setPassword(mqttConfig.getMqttPassword().toCharArray());
            this.connect();

            this.mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
            this.mapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
            this.mapper.configure(SerializationFeature.FAIL_ON_SELF_REFERENCES, false);

            log.info("MQTT Starting Mqtt listener");
        } catch (MqttException me) {
            log.error("MQTT Init ERROR : "+me.getMessage());
        }
        return this.mqttClient;
    }

    public void connect() throws MqttException {
        log.debug("MQTT - Connect");
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
            log.error("MQTT ERROR :"+me.getMessage());
        }
    }


    private boolean pendingReconnection = false;
    /*
     * (non-Javadoc)
     * @see org.eclipse.paho.client.mqttv3.MqttCallback#connectionLost(java.lang.Throwable)
     */
    @Override
    public void connectionLost(Throwable arg0) {
        log.error("MQTT - Connection Lost");
        try {
            // immediate retry, then will be async
            this.connect();
        } catch (MqttException me) {
            pendingReconnection = true;
        }
        prometeusService.addMqttConnectionLoss();
    }

    @Scheduled(fixedDelayString = "${helium.mqtt.reconnect.scanPeriod}", initialDelay = 30_000) // default 10s
    protected void autoReconnect() {
        if ( ! pendingReconnection ) return;
        try {
            if ( mqttClient.isConnected() ) {
                log.info("MQTT - reconnected");
                pendingReconnection = false;
            }
            this.connect();
            log.info("MQTT - reconnected");
            pendingReconnection = false;
        } catch (MqttException me) {
            log.warn("MQTT - reconnection failed - "+me.getMessage());
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

    @Autowired
    protected RoamingService roamingService;

    protected class DeviceDedup {
        public String devEui;
        public long lastSeen;
    }

    private final Object lockJoinDedup = new Object();
    protected HashMap<String,DeviceDedup> dedupHashMap = new HashMap<>();

    private final Object lockPacketDedup = new Object();
    protected HashMap<String,Long> packetDedup = new HashMap<>();

    @Scheduled(fixedDelayString = "${helium.mqtt.dedup.scanPeriod}", initialDelay = 120_000) // default 10m
    protected void cleanDedupCache() {
        long now = Now.NowUtcMs();

        // clean the dedup storage
        if (dedupHashMap.size() > 200) {
            Set<String> obj = dedupHashMap.keySet();
            synchronized (lockJoinDedup) {
                for (String s : obj) {
                    DeviceDedup _d = dedupHashMap.get(s);
                    if (_d != null && (now - _d.lastSeen) > 2 * Now.ONE_MINUTE) {
                        dedupHashMap.remove(_d.devEui);
                    }
                }
            }
        }

        // clean the dedup packets
        if (packetDedup.size() > 500) {
            Set<String> obj = packetDedup.keySet();
            synchronized (lockPacketDedup) {
                for (String s : obj) {
                    long t = packetDedup.get(s);
                    if ((now - t) > 30 * Now.ONE_MINUTE) {
                        // use 30 minutes because HPR uses 30 minutes dedup windows
                        packetDedup.remove(s);
                    }
                }
            }
        }

    }


    @Override
    public void messageArrived(String topicName, MqttMessage message) throws Exception {
        // Leave it blank for Publisher
        long start = Now.NowUtcMs();
        //log.info("MQTT - MessageArrived on "+topicName);

        // some of the messages are protobuf format
        if ( topicName.matches("application/.*/event/up$") ) {
            // Prefer MQTT to get the uplink information because the data is decoded
            try {
                UplinkEvent up = mapper.readValue(message.toString(), UplinkEvent.class);
                prometeusService.addLoRaUplink(
                    start - DateConverters.StringDateToMs(up.getTime()),
                    Base64.decode(up.getData()).length
                );

                log.debug("UPLINK Dev: " + up.getDeviceInfo().getDevEui() + " Adr:" + up.getDevAddr() + " duplicates:" + up.getRxInfo().size() + " size: "+Base64.decode(up.getData()).length);
                heliumTenantService.processUplink(
                    up.getDeviceInfo().getTenantId(),
                    up.getDeviceInfo().getDevEui(),
                    Base64.decode(up.getData()).length,
                    up.getRxInfo().size() - 1
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
// UPSTREAM REGION HACK
// =================================================

        } else if (  topicName.matches(".*/gateway/.*/event/up$") ) {
            long now = Now.NowUtcMs();

            UplinkFrame uf = UplinkFrame.parseFrom(message.getPayload());
            // 00 9A2E3DD7EFF98160 9861BFC396F98160 75AB   D683 EED2
            //       app eui (rev)   dev eui (rev)  nonce  MIC  CRC

            byte [] payload = uf.getPhyPayload().toByteArray();
            long rx = (uf.getRxInfo().getTime().getSeconds() * 1000) + (uf.getRxInfo().getTime().getNanos() / 1_000_000);

            String spayload = HexaConverters.byteToHexString(payload);
            // Manage arrival time for the first frame
            Long packetTime;
            synchronized (lockPacketDedup) {
                packetTime = packetDedup.get(spayload);
            }
            if ( packetTime == null ) {
                // first time we see this packet
                synchronized (lockPacketDedup) {
                    packetDedup.put(spayload, now);
                }
                prometeusService.addLoRaFirstUplink( now - rx );
            } else if ( (now - rx) > 2_000 ) {
                // late packet
                prometeusService.addLoRaLateUplink( now - rx );
            }
            // count packets received at gateway level (invoiced by HPR, potentially rejected by LNS)
            prometeusService.addLoRaGatewayUplink();

            // Measure uplink confirmed processing time
            if ( (payload[0] & 0xC0) == 0x80 && uf.getRxInfo().getTime().getSeconds() > 0 ) {
                // header for confirmed frame - compute elapse time in ms
                prometeusService.addLoRaUplinkConf(
                    now - rx
                );
            }

            // Manage zone switch on Join Requests
            if ( mqttConfig.getHeliumZoneDetectionEnable()  ) {
                if (payload[0] == 0 && payload.length == 23) {
                    // possible Join Request
                    String devEUI = HexaConverters.byteToHexString(payload, 9, 8);
                    String region = topicName.substring(0, topicName.indexOf("/"));
                    DeviceDedup d;
                    synchronized (lockJoinDedup) {
                        d = dedupHashMap.get(devEUI);
                    }
                    if (d == null || (now - d.lastSeen) > Now.ONE_MINUTE) {
                        // found a new join for that device
                        d = new DeviceDedup();
                        d.devEui = devEUI;
                        d.lastSeen = now;
                        synchronized (lockJoinDedup) {
                            dedupHashMap.put(devEUI, d);
                        }

                        // ... push to process
                        byte[] _dev = new byte[8]; // reverse the bytes of the address
                        for (int i = 0; i < 8; i++) {
                            _dev[i] = payload[(9 + 8 - 1) - i];
                        }
                        log.debug("Found a join request for " + HexaConverters.byteToHexString(_dev) + " for region " + region);
                        roamingService.processJoinMessage(_dev, HexaConverters.byteToHexString(_dev), region);
                    }
                }
            }

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
            log.debug("MQTT - MessageArrived on "+topicName);
            //log.info("MQTT - message "+message);
        }
        log.debug("MQTT processing time "+(Now.NowUtcMs()-start)+"ms for "+topicName);
    }

}
