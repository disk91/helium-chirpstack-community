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
import eu.heliumiot.console.service.*;
import fr.ingeniousthings.tools.*;
import io.chirpstack.api.gw.UplinkFrame;
import io.chirpstack.json.JoinEvent;
import io.chirpstack.json.UplinkEvent;
import io.chirpstack.json.sub.UplinkEventRxInfo;
import org.postgresql.shaded.com.ongres.scram.common.bouncycastle.base64.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.eclipse.paho.client.mqttv3.*;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;

import javax.annotation.PostConstruct;
import java.util.*;
import java.util.stream.Collectors;

import static eu.heliumiot.console.service.HeliumParameterService.PARAM_MQTT_CLIENT_ID;

@Component
public class MqttListener implements MqttCallback {
    private final Logger log = LoggerFactory.getLogger(this.getClass());

    protected static final int MQTT_QOS = 2;

    protected static final long HPR_PACKET_WINDOW_TIMEOUT = 30 * Now.ONE_MINUTE;
    protected static final long HPR_PACKET_FULL_TIMEOUT = 2 * Now.ONE_MINUTE;
    protected static final long PACKET_CACHE_TIMEOUT = 10_000;
    protected static final long PACKET_DEDUP_MAXSZ = 50_000;

    @Autowired
    private ConsoleConfig mqttConfig;

    @Autowired
    protected PrometeusService prometeusService;

    private MqttConnectOptions connectionOptions;
    private MemoryPersistence persistence;
    private MqttClient mqttClient = null;
    private String clientId;

    @Autowired
    protected HeliumParameterService heliumParameterService;


    protected String[] _topics = {"application/#","+/gateway/+/event/up"};
    protected int[] _qos = { MQTT_QOS,MQTT_QOS };

    ObjectMapper mapper = new ObjectMapper();

    protected boolean requestToStop = false;
    protected volatile int scheduleRunning = 0;
    protected boolean stopped = false;

    public void stopMqttListener() {
        this.requestToStop = true;
        this.stop();
        long start = Now.NowUtcMs();
        while ( this.scheduleRunning > 0 && (Now.NowUtcMs() - start) < 10_000 ) {
            Tools.sleep(100);
        }
        // Clear the pending Join
        for ( DeviceDedup _d : dedupHashMap.values() ) {
            heliumTenantService.invoiceJoin(_d.devEui, _d.count);
        }
        this.stopped = true;
    }

    @PostConstruct
    public MqttClient initMqtt() {

        HeliumParameter mqttClientId = heliumParameterService.getParameter(PARAM_MQTT_CLIENT_ID);
        this.clientId = mqttConfig.getMqttId()+mqttClientId.getStrValue();
        this.persistence = new MemoryPersistence();
        this.connectionOptions = new MqttConnectOptions();
        try {
            log.info("MQTT L Url :"+mqttConfig.getMqttServer());
            log.info("MQTT L User :"+mqttConfig.getMqttLogin());
            //log.info("Password :"+mqttConfig.getPassword());
            log.info("MQTT L Id : "+clientId);
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

            log.info("MQTT L Starting Mqtt listener");
        } catch (MqttException me) {
            log.error("MQTT L Init ERROR : "+me.getMessage());
        }
        return this.mqttClient;
    }

    private boolean inConnect = false;
    public void connect() throws MqttException {
        inConnect = true;
        try {
            log.debug("MQTT L - Connect");
            if (this.mqttClient != null) this.mqttClient.close();
            this.mqttClient = new MqttClient(mqttConfig.getMqttServer(), this.clientId, persistence);
            this.mqttClient.connect(this.connectionOptions);
            this.mqttClient.setCallback(this);
            this.mqttClient.subscribe(_topics, _qos);
        } finally {
            inConnect = false;
        }
    }

    // stop the listener once we request a stop of the application
    public void stop() {
        try {
            this.mqttClient.unsubscribe(_topics);
            this.mqttClient.disconnect();
            this.mqttClient.close();
        } catch (MqttException me) {
            log.error("MQTT L ERROR :"+me.getMessage());
        }
    }


    private boolean pendingReconnection = false;
    /*
     * (non-Javadoc)
     * @see org.eclipse.paho.client.mqttv3.MqttCallback#connectionLost(java.lang.Throwable)
     */
    @Override
    public void connectionLost(Throwable arg0) {
        log.error("MQTT L - Connection Lost");
        try {
            // immediate retry, then will be async
            log.error("MQTT L - Direct reconnecting");
            this.connect();
            log.error("MQTT L - Direct reconnected");
        } catch (MqttException me) {
            log.warn("MQTT L - direct reconnection failed - "+me.getMessage());
            pendingReconnection = true;
        }
        prometeusService.addMqttConnectionLoss();
    }

    @Scheduled(fixedDelayString = "${helium.mqtt.reconnect.scanPeriod}", initialDelay = 30_000) // default 10s
    protected void autoReconnect() {
        if ( ! pendingReconnection || inConnect ) return;
        try {
            if ( mqttClient.isConnected() ) {
                log.info("MQTT L - reconnected");
            } else {
                log.info("MQTT L - reconnecting");
                this.connect();
                log.info("MQTT L - reconnected");
            }
            pendingReconnection = false;
        } catch (MqttException me) {
            log.warn("MQTT L - reconnection failed - "+me.getMessage());
        }
    }


    /*
     * (non-Javadoc)
     * @see org.eclipse.paho.client.mqttv3.MqttCallback#deliveryComplete(org.eclipse.paho.client.mqttv3.IMqttDeliveryToken)
     */
    @Override
    public void deliveryComplete(IMqttDeliveryToken arg0) {
        // log.info("MQTT L - delivery completed");
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
        public int count;
        public long lastSeen;
    }

    private final Object lockJoinDedup = new Object();
    protected HashMap<String,DeviceDedup> dedupHashMap = new HashMap<>();

    private final Object lockPacketDedup = new Object();
    private final Object lockRecentPacketDedup = new Object();
    private final Object lockPreprocessedPacketDedup = new Object();

    protected static class ToDedup {
        public long firstArrivalTime;       // server time for first of the packets
        public boolean isJoin;              // true when a join packet
        public String key;                  // entry key
        public String firstGatewayId;       // for identification
        public String devAddr;              // device devaddr for identification
        public int fCnt;                    // for identification
        public int dataSz;                  // data size for invoicing
        public int duplicates;              // number of copy received
        public int duplicatesInvoiced;      // what chirpstack received
        public String deviceEui;            // who is to be invoiced
        public String tenantId;
        public byte[] _deviceEui;           // raw devEui
    }

    protected HashMap<String,ToDedup> packetDedup = new HashMap<>();
    protected LinkedList<ToDedup> recentPacketDedup = new LinkedList<>();
    protected ArrayList<ToDedup> preprocessedPacketDedup = new ArrayList<>();

    protected class ToInvoice {
        public String devEui;
        public int dcs;
    }

    private long lastCleanJoinDedup = 0;
    private long lastCleanLateDedup = 0;

    private boolean firstDedupRun = true;
    @Scheduled(fixedDelayString = "${helium.mqtt.dedup.scanPeriod}", initialDelay = 120_000) // default 10m
    protected void cleanDedupCache() {
        if ( this.requestToStop ) return;
        this.scheduleRunning++;
        try {
            long now = Now.NowUtcMs();

            // clean the dedup storage
            if (dedupHashMap.size() > 50 || (now - lastCleanJoinDedup) > 8 * Now.ONE_MINUTE) {
                Set<String> obj = dedupHashMap.keySet();
                ArrayList<String> toRemove = new ArrayList<>();
                ArrayList<ToInvoice> toInvoice = new ArrayList<>();
                synchronized (lockJoinDedup) {
                    for (String s : obj) {
                        DeviceDedup _d = dedupHashMap.get(s);
                        if (_d != null && (now - _d.lastSeen) > 2 * Now.ONE_MINUTE) {
                            log.debug("Join Commit " + _d.devEui + " packets " + _d.count);
                            ToInvoice ti = new ToInvoice();
                            ti.devEui = _d.devEui;
                            ti.dcs = _d.count;
                            toInvoice.add(ti);
                            toRemove.add(_d.devEui);
                        } else if (_d != null && _d.count > 100) {
                            // possible when the rate of Join is really fast and we have no end on the previous if
                            log.warn("Join Commit " + _d.devEui + " not committing " + _d.count + " packets");
                            ToInvoice ti = new ToInvoice();
                            ti.devEui = _d.devEui;
                            ti.dcs = _d.count;
                            toInvoice.add(ti);
                            _d.count = 0;
                        }
                    }
                    for (String s : toRemove) {
                        dedupHashMap.remove(s);
                    }
                }
                for (ToInvoice t : toInvoice) {
                    heliumTenantService.invoiceJoin(t.devEui, t.dcs);
                }
                this.lastCleanJoinDedup = now;
            }

            int postInvoiced = 0;
            int notInvoicable = 0;
            // clean the dedup packets
            if (packetDedup.size() > PACKET_DEDUP_MAXSZ || (now - lastCleanLateDedup) > 8 * Now.ONE_MINUTE) {
                boolean isFull = (packetDedup.size() > PACKET_DEDUP_MAXSZ);
                if (isFull) log.warn("PacketDedup is running full " + packetDedup.size());

                // identify what to be removed
                List<ToDedup> toRemove;
                synchronized (lockPacketDedup) {
                    toRemove = packetDedup.values().parallelStream().filter(dedup -> (!isFull && (now - dedup.firstArrivalTime) > HPR_PACKET_WINDOW_TIMEOUT)
                        || (isFull && (now - dedup.firstArrivalTime) > HPR_PACKET_FULL_TIMEOUT)).collect(Collectors.toList());
                }
                log.debug("cleanDedupCache - Found " + toRemove.size() + " packets to clean");

                // process invoicing
                for (ToDedup d : toRemove) {
                    if (!d.isJoin && d.duplicatesInvoiced < d.duplicates && d.deviceEui != null && d.tenantId != null) {
                        log.debug("cleanDedupCache - Found device to invoice late packets " + d.deviceEui + " (" + (d.duplicates - d.duplicatesInvoiced) + ") fCnt " + d.fCnt+ " devAdr "+d.devAddr);
                        prometeusService.addLoRaUplink(
                            0,
                            d.dataSz,
                            false,
                            d.duplicates - d.duplicatesInvoiced
                        );
                        heliumTenantService.processUplink(
                            d.tenantId,
                            d.deviceEui,
                            d.dataSz,
                            false,
                            d.duplicates - d.duplicatesInvoiced
                        );
                        postInvoiced += (d.duplicates - d.duplicatesInvoiced);
                    } else if (d.deviceEui == null) {
                        // search in the preprocessed
                        boolean found = false;
                        for (ToDedup _d : preprocessedPacketDedup) {
                            if ( _d.devAddr != null ) {
                                if (!d.isJoin && d.fCnt == _d.fCnt && d.devAddr.compareToIgnoreCase(_d.devAddr) == 0 && Math.abs(d.firstArrivalTime - _d.firstArrivalTime) < 30_000) {
                                    // it may be the same, process it
                                    log.debug("cleanDedupCache - Searched device to invoice late packets " + _d.deviceEui + " (" + (_d.duplicates - _d.duplicatesInvoiced) + ") fCnt " + _d.fCnt+ " devAdr "+_d.devAddr);
                                    prometeusService.addLoRaUplink(
                                        0,
                                        _d.dataSz,
                                        false,
                                        d.duplicates - _d.duplicatesInvoiced
                                    );
                                    heliumTenantService.processUplink(
                                        _d.tenantId,
                                        _d.deviceEui,
                                        _d.dataSz,
                                        false,
                                        d.duplicates - _d.duplicatesInvoiced
                                    );
                                    postInvoiced = (d.duplicates - _d.duplicatesInvoiced);
                                    found = true;
                                    break;
                                }
                            } else {
                                log.error("### Got a null devaddr ?? "+_d.fCnt+" "+_d.firstArrivalTime+" ");
                            }
                        }
                        if (!found && !firstDedupRun) {
                            // don't print on first run it's normal
                            log.warn("Found a packetDedup without uplink event for " + d.devAddr + " / " + d.fCnt + " from " + d.firstGatewayId + " with " + d.duplicates + " dup");
                            notInvoicable+=d.duplicates;
                        }
                    }
                }
                if ( notInvoicable > 0 ) {
                    log.warn("cleanDedupCache - late packets invoiced "+postInvoiced+" not invoiced "+notInvoicable);
                } else {
                    log.info("cleanDedupCache - late packets invoiced " + postInvoiced);
                }

                // clean
                synchronized (lockPacketDedup) {
                    for (ToDedup d : toRemove) {
                        packetDedup.remove(d.key);
                    }
                }
                if (isFull) log.warn("PacketDedup is running full, new size " + packetDedup.size());

                ArrayList<ToDedup> toRemovePre = new ArrayList<>();
                for (ToDedup _d : preprocessedPacketDedup) {
                    if ((now - _d.firstArrivalTime) > HPR_PACKET_WINDOW_TIMEOUT) {
                        toRemovePre.add(_d);
                    }
                }
                synchronized (lockPreprocessedPacketDedup) {
                    for (ToDedup _d : toRemovePre) {
                        preprocessedPacketDedup.remove(_d);
                    }
                }
                lastCleanLateDedup = now;
            }
        } catch ( Exception x) {
            log.error("Exception in cleanDedupCche "+x.getMessage());
            x.printStackTrace();
        } finally {
            --this.scheduleRunning;
            this.firstDedupRun = false;
        }
    }


    @Override
    public void messageArrived(String topicName, MqttMessage message) throws Exception {
        // Leave it blank for Publisher
        long start = Now.NowUtcMs();
        //log.info("MQTT L - MessageArrived on "+topicName);

        // some of the messages are protobuf format
        if ( topicName.matches("application/.*/event/up$") ) {
            // Prefer MQTT L to get the uplink information because the data is decoded
            try {
                UplinkEvent up = mapper.readValue(message.toString(), UplinkEvent.class);
                int dataSz = Base64.decode(up.getData()).length;
                prometeusService.addLoRaUplink(
                    start - DateConverters.StringDateToMs(up.getTime()),
                    dataSz,
                    true,
                    up.getRxInfo().size()-1
                );
                log.debug("UPLINK Dev: " + up.getDeviceInfo().getDevEui() + " Adr:" + up.getDevAddr() + " duplicates:" + up.getRxInfo().size() + " size: "+Base64.decode(up.getData()).length);
                heliumTenantService.processUplink(
                    up.getDeviceInfo().getTenantId(),
                    up.getDeviceInfo().getDevEui(),
                    dataSz,
                    true,
                    up.getRxInfo().size() - 1
                );

                // Attach to dedup objects, try a fast search on last 10s
                Optional<ToDedup> theDedup;
                synchronized (lockRecentPacketDedup) {
                    theDedup = recentPacketDedup.parallelStream().filter( dedup -> {
                            if (dedup.fCnt == up.getfCnt() && !dedup.isJoin && dedup.devAddr.compareToIgnoreCase(up.getDevAddr()) == 0) {
                                if ( dedup.deviceEui != null && dedup.deviceEui.compareToIgnoreCase(up.getDeviceInfo().getDevEui()) == 0 ) {
                                    return true;
                                } else {
                                    for (UplinkEventRxInfo gw : up.getRxInfo()) {
                                        if (gw.getGatewayId().compareToIgnoreCase(dedup.firstGatewayId) == 0)
                                            return true;
                                    }
                                }
                            }
                            return false;
                    }).findFirst();
                }
                // Eventually a deep search on the whole history
                if (theDedup.isEmpty()) {
                    log.debug("Uplink not found in the recent history... deep search");
                    theDedup = packetDedup.values().parallelStream().filter(dedup -> {
                        if (dedup.fCnt == up.getfCnt() && !dedup.isJoin && dedup.devAddr.compareToIgnoreCase(up.getDevAddr()) == 0) {
                            if ( dedup.deviceEui != null && dedup.deviceEui.compareToIgnoreCase(up.getDeviceInfo().getDevEui()) == 0 ) {
                                return true;
                            } else {
                                for (UplinkEventRxInfo gw : up.getRxInfo()) {
                                    if (gw.getGatewayId().compareToIgnoreCase(dedup.firstGatewayId) == 0) return true;
                                }
                            }
                        }
                        return false;
                    }).findFirst();
                }

                // give the information for lte packet invoice later
                if ( theDedup.isPresent() ) {
                    ToDedup d = theDedup.get();
                    if ( d.deviceEui == null ) {
                        d.deviceEui = up.getDeviceInfo().getDevEui();
                        d.tenantId = up.getDeviceInfo().getTenantId();
                        d.duplicatesInvoiced = up.getRxInfo().size(); // this is a total invoiced ( first + duplicate )
                        d.dataSz = dataSz;
                        log.debug("Found packet for dedup " + up.getDeviceInfo().getDevEui() + " fCnt " + up.getfCnt() + " devAddr " + up.getDevAddr() + " invoiced " + d.duplicatesInvoiced);
                    } else {
                        // packet already known, chirpstack considered this late packet as a new packet
                        d.duplicatesInvoiced += up.getRxInfo().size();
                    }
                } else {
                    // It's possible the processing of the frames is late compared to uplink
                    // so the entry is not yet existing.
                    // we can have a reconciliation when cache is cleaned as the same entry exist with no association
                    // make a table of this and then in the case of not found association we can reconciliate it.
                    log.debug("Found a packet invoiced with no dedup reference "+up.getDeviceInfo().getDevEui()+" with fCnt "+up.getfCnt()+" and devAddr "+up.getDevAddr());
                    ToDedup d = new ToDedup();
                    d.deviceEui = up.getDeviceInfo().getDevEui();
                    d.devAddr = up.getDevAddr();
                    d.tenantId = up.getDeviceInfo().getTenantId();
                    d.duplicatesInvoiced = up.getRxInfo().size(); // this is a total invoiced ( first + duplicate )
                    d.dataSz = dataSz;
                    d.firstGatewayId = up.getRxInfo().get(0).getGatewayId();
                    d.firstArrivalTime = Now.NowUtcMs();
                    d.key = null;
                    d.duplicates = 0;
                    d.isJoin = false;
                    synchronized (lockPreprocessedPacketDedup) {
                        if ( preprocessedPacketDedup.size() < 1000 ) this.preprocessedPacketDedup.add(d);
                        else log.warn("preprocessedPacketDedup is full");
                    }
                }

            } catch (JsonProcessingException x) {
                log.error("MQTT L - failed to parse App uplink - " + x.getMessage());
                x.printStackTrace();
            } catch (Exception x) {
                x.printStackTrace();
            }

        } else if ( topicName.matches("application/.*/event/down$") ) {
            // REDIS will be prefered because MQTT L Json does not have size
            // And MQTT L does not trace Uplink
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
                                log.error("MQTT L - failed to parse App downlink - " + x.getMessage());
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
            } catch (JsonProcessingException x) {
                log.error("MQTT L - failed to parse App JOIN - " + x.getMessage());
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
            boolean isJoin = (payload[0] == 0 && payload.length == 23);

          //  long legTime = (uf.getRxInfoLegacy().getTime().getSeconds() * 1000) + (uf.getRxInfo().getTime().getNanos() / 1_000_000);

            String spayload = HexaConverters.byteToHexString(payload);
            // Manage arrival time for the first frame
            ToDedup dedup;
            synchronized (lockPacketDedup) {
                dedup = packetDedup.get(spayload);
            }
            if ( dedup == null ) {
                // first time we see this packet
                dedup = new ToDedup();
                dedup.firstGatewayId = uf.getRxInfo().getGatewayId();
                dedup.firstArrivalTime = now;
                dedup.key = spayload;
                dedup.duplicates = 1;
                dedup.duplicatesInvoiced = 0;
                dedup.isJoin = isJoin;
                if (!isJoin) {
                    // Packet is
                    // 40 XX XX XX XX YY ZZ ZZ
                    // where XX are DevAddr reversed byte order
                    // YY FCtrl
                    // ZZ FCnt
                    dedup.deviceEui = null;
                    dedup.tenantId = null;
                    dedup.devAddr = HexaConverters.byteToHexString(payload[4]) +
                        HexaConverters.byteToHexString(payload[3]) +
                        HexaConverters.byteToHexString(payload[2]) +
                        HexaConverters.byteToHexString(payload[1]);
                    dedup.fCnt= Stuff.getIntFromByte(payload[7])*256 + Stuff.getIntFromByte(payload[6]);
                    dedup.dataSz = 0;
                    synchronized (lockRecentPacketDedup) {
                        recentPacketDedup.addLast(dedup);
                        // clear the old one
                        while ( (now - recentPacketDedup.getFirst().firstArrivalTime) > PACKET_CACHE_TIMEOUT ) {
                            recentPacketDedup.removeFirst();
                        }
                    }
                    log.debug("First uplink arriving for devaddr "+dedup.devAddr+" with fCnt "+dedup.fCnt+" after "+(now - dedup.firstArrivalTime)+"ms from "+uf.getRxInfo().getGatewayId());
                } else {
                    dedup._deviceEui = new byte[8]; // reverse the bytes of the address
                    for (int i = 0; i < 8; i++) {dedup._deviceEui[i] = payload[(9 + 8 - 1) - i]; }
                    dedup.deviceEui = HexaConverters.byteToHexString(dedup._deviceEui);
                    dedup.tenantId = null;
                    dedup.devAddr = null;
                    dedup.fCnt = 0;
                    dedup.dataSz = 23;
                }
                synchronized (lockPacketDedup) {
                    packetDedup.put(spayload, dedup);
                }
                if ( isJoin ) {
                    log.debug("New join detected from "+dedup.firstGatewayId+" for device "+dedup.deviceEui);
                } else {
                    log.debug("New Uplink detected from "+dedup.firstGatewayId+" for devaddr "+dedup.devAddr+" with fCnt "+dedup.fCnt);
                }
                prometeusService.addLoRaFirstUplink( now - rx );
            } else {
                // update the stat on the packet
                dedup.duplicates++;
                // update the late stats
                if ( (now - dedup.firstArrivalTime) > mqttConfig.getChirpstackDedupDelayMs() ) {
                    prometeusService.addLoRaLateUplink(now - dedup.firstArrivalTime);
                    log.debug("Late uplink arriving for devaddr "+dedup.devAddr+" with fCnt "+dedup.fCnt+" after "+(now - dedup.firstArrivalTime)+"ms from "+uf.getRxInfo().getGatewayId());
                } else {
                    log.debug("OnTime uplink arriving for devaddr "+dedup.devAddr+" with fCnt "+dedup.fCnt);
                }
            }

            // count packets received at gateway level (invoiced by HPR, potentially rejected by LNS)
            if ( isJoin ) {
                prometeusService.addLoRaJoinRequest(now-rx);
            } else {
                prometeusService.addLoRaGatewayUplink(now-rx);
            }

            // Measure uplink confirmed processing time
            // Based on GW time, so it's an approximation
            if ( (payload[0] & 0xC0) == 0x80 && uf.getRxInfo().getTime().getSeconds() > 0 ) {
                // header for confirmed frame - compute elapse time in ms
                prometeusService.addLoRaUplinkConf(
                    now - rx
                );
            }

            // Manage zone switch on Join Requests
            // Manage DC count for Join Requests
            if ( isJoin ) {
                // possible Join Request
                String region = topicName.substring(0, topicName.indexOf("/"));
                DeviceDedup d;
                synchronized (lockJoinDedup) {
                    d = dedupHashMap.get(dedup.deviceEui);
                }
                if (d == null || (now - d.lastSeen) > Now.ONE_MINUTE) {
                    // found a new join for that device
                    if ( d == null ) {
                        d = new DeviceDedup();
                        d.count = 1;
                        d.devEui = dedup.deviceEui;
                        d.lastSeen = now;
                    } else {
                        // new session but we keep the structure
                        d.lastSeen = now;
                        d.count++;
                    }
                    synchronized (lockJoinDedup) {
                        dedupHashMap.put(dedup.deviceEui, d);
                    }

                    if ( mqttConfig.getHeliumZoneDetectionEnable()  ) {
                        // ... push to process, this is synchronous action, can be long
                        // and delay the rest of the timing, rare but could be optimized.
                        log.debug("Found a join request for " + d.devEui + " for region " + region);
                        roamingService.processJoinMessage(dedup._deviceEui, d.devEui, region);
                    }
                } else {
                    // count a new Join to invoice
                    d.count++;
                }
            }

// =================================================
// INTERNAL ASYNCHRONOUS MESSAGES
// =================================================
/* managed in the second listner to avoid impacting these topic processing
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
 */
// =================================================
// OTHERS
// =================================================
        } else {

            // standard json messages
            log.debug("MQTT L - MessageArrived on "+topicName);
            //log.info("MQTT L - message "+message);
        }
        log.debug("MQTT L processing time "+(Now.NowUtcMs()-start)+"ms for "+topicName);
    }

}
