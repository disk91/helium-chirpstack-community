/*
 * Copyright (c) - Paul Pinault (aka disk91) - 2024.
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
import com.google.protobuf.InvalidProtocolBufferException;
import eu.heliumiot.console.ConsoleConfig;
import eu.heliumiot.console.jpa.db.HeliumParameter;
import eu.heliumiot.console.service.HeliumParameterService;
import eu.heliumiot.console.service.HeliumTenantService;
import eu.heliumiot.console.service.PrometeusService;
import eu.heliumiot.console.service.RoamingService;
import fr.ingeniousthings.tools.*;
import io.chirpstack.api.gw.UplinkFrame;
import io.chirpstack.json.JoinEvent;
import io.chirpstack.json.LogEvent;
import io.chirpstack.json.UplinkEvent;
import io.chirpstack.json.sub.UplinkEventRxInfo;
import org.eclipse.paho.client.mqttv3.*;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;
import org.postgresql.shaded.com.ongres.scram.common.bouncycastle.base64.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import jakarta.annotation.PostConstruct;

import java.util.*;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import static eu.heliumiot.console.service.HeliumParameterService.PARAM_MQTT_CLIENT_ID;

@Component
public class MqttLoRaListener implements MqttCallback {

    private final Logger log = LoggerFactory.getLogger(this.getClass());

    @Autowired
    protected PrometeusService prometeusService;

    ObjectMapper mapper = new ObjectMapper();

    // =================================================================
    // Queuing
    // =================================================================
    public static final int MSG_TYPE_BRIDGE = 1;
    public static final int MSG_TYPE_CHIPSTACK = 2;
    protected static class MqttEvent {
        public long arrivalTime;
        public MqttMessage message;
        public int messageType;
        public String topic;
    }
    protected ConcurrentLinkedQueue<MqttEvent> bridgeQueue = new ConcurrentLinkedQueue<MqttEvent>();
    protected int bridgeQSz = 0;
    protected final Object bridgeQSzLock = new Object();
    protected ConcurrentLinkedQueue<MqttEvent> chipstackQueue = new ConcurrentLinkedQueue<MqttEvent>();
    protected int chirpstackQSz = 0;
    protected final Object chirpstackQSzLock = new Object();


    // =================================================================
    // Service management
    // =================================================================
    protected boolean requestToStop = false;
    protected volatile int scheduleRunning = 0;
    protected final Object scheduleRunningLock = new Object();
    protected boolean stopped = false;

    public void stopMqttListener() {
        this.stop();
        this.requestToStop = true;
        long start = Now.NowUtcMs();
        while (this.scheduleRunning > 0 && (Now.NowUtcMs() - start) < 10_000) {
            Tools.sleep(100);
        }
        // Clear the pending Join
        for (DeviceDedup _d : dedupHashMap.values()) {
            heliumTenantService.processJoinRequest(_d.devEui, _d.count);
        }
        this.stopped = true;
    }


    // ==================================================================
    // Manage MQTT listener
    // ==================================================================
    protected static final int MQTT_QOS = 2;
    protected String[] _topics = {"application/#", "+/gateway/+/event/up"};
    protected int[] _qos = {MQTT_QOS, MQTT_QOS};
    protected IMqttMessageListener[] _listeners = new IMqttMessageListener[2];

    @Autowired
    private ConsoleConfig mqttConfig;

    @Autowired
    protected HeliumParameterService heliumParameterService;

    private MqttConnectOptions connectionOptions;
    private MemoryPersistence persistence;
    private MqttClient mqttClient = null;
    private String clientId;
    private boolean pendingReconnection = false;

    private Pattern matchUplink;
    private Pattern matchJoin;
    private Pattern matchLog;

    // other interesting patterns
    // application/.*/event/log$
    // application/.*/event/down$

    @PostConstruct
    public MqttClient initMqtt() {

        // precompile some regex
        matchUplink = Pattern.compile("application/.*/event/up$");
        matchJoin = Pattern.compile("application/.*/event/join$");
        matchLog = Pattern.compile("application/.*/event/log$");


        // manage MQTT
        HeliumParameter mqttClientId = heliumParameterService.getParameter(PARAM_MQTT_CLIENT_ID);
        this.clientId = mqttConfig.getMqttId() + mqttClientId.getStrValue();
        this.persistence = new MemoryPersistence();
        this.connectionOptions = new MqttConnectOptions();
        try {
            log.info("MQTT LL Url :" + mqttConfig.getMqttServer());
            log.info("MQTT LL User :" + mqttConfig.getMqttLogin());
            //log.info("Password :"+mqttConfig.getPassword());
            log.info("MQTT LL Id : " + clientId);
            this.connectionOptions.setCleanSession(false);          // restart by processing pending events
            this.connectionOptions.setAutomaticReconnect(false);    // reconnect managed manually
            this.connectionOptions.setConnectionTimeout(5);         // do not wait more than 5s to reconnect
            this.connectionOptions.setKeepAliveInterval(60);
            this.connectionOptions.setUserName(mqttConfig.getMqttLogin());
            this.connectionOptions.setPassword(mqttConfig.getMqttPassword().toCharArray());

            // topics application/#
            this._listeners[0] = new IMqttMessageListener() {
                @Override
                public void messageArrived(String s, MqttMessage mqttMessage) throws Exception {
                    MqttEvent e = new MqttEvent();
                    e.arrivalTime = Now.NowUtcMs();
                    e.message = mqttMessage;
                    e.messageType = MSG_TYPE_CHIPSTACK;
                    e.topic = s;
                    chipstackQueue.add(e);
                    synchronized(chirpstackQSzLock) {
                        chirpstackQSz++;
                    }
                    prometeusService.chirpstackQueueSet(chirpstackQSz);
                    log.debug("MQTT LL Add one message from chirpstack, queue size "+chirpstackQSz);
                }
            };

            // topics +/gateway/+/event/up
            this._listeners[1] = new IMqttMessageListener() {
                @Override
                public void messageArrived(String s, MqttMessage mqttMessage) throws Exception {
                    MqttEvent e = new MqttEvent();
                    e.arrivalTime = Now.NowUtcMs();
                    e.message = mqttMessage;
                    e.messageType = MSG_TYPE_BRIDGE;
                    e.topic = s;
                    bridgeQueue.add(e);
                    synchronized(bridgeQSzLock) {
                        bridgeQSz++;
                    }
                    prometeusService.bridgeQueueSet(bridgeQSz);
                    log.debug("MQTT LL Add one message from bridge, queue size "+bridgeQSz);
                }
            };
            this.connect();

            this.mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
            this.mapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
            this.mapper.configure(SerializationFeature.FAIL_ON_SELF_REFERENCES, false);

            log.info("MQTT LL Starting Mqtt listener");
        } catch (MqttException me) {
            log.error("MQTT LL Init ERROR : " + me.getMessage());
        }
        return this.mqttClient;
    }

    private boolean inConnect = false;

    public void connect() throws MqttException {
        inConnect = true;
        try {
            log.debug("MQTT LL - Connect");
            if (this.mqttClient != null) this.mqttClient.close();
            this.mqttClient = new MqttClient(mqttConfig.getMqttServer(), this.clientId, persistence);
            this.mqttClient.connect(this.connectionOptions);
            this.mqttClient.subscribe(_topics, _qos, _listeners);
        } finally {
            inConnect = false;
        }
    }

    @Scheduled(fixedDelayString = "${helium.mqtt.reconnect.scanPeriod}", initialDelay = 30_000) // default 10s
    protected void autoReconnect() {
        if (!pendingReconnection || inConnect) return;
        try {
            if (mqttClient.isConnected()) {
                log.info("MQTT LL - reconnected");
            } else {
                log.info("MQTT LL - reconnecting");
                this.connect();
                log.info("MQTT LL - reconnected");
            }
            pendingReconnection = false;
        } catch (MqttException me) {
            log.warn("MQTT LL - reconnection failed - " + me.getMessage());
        }
    }

    public void stop() {
        try {
            this.mqttClient.unsubscribe(_topics);
            this.mqttClient.disconnect();
            this.mqttClient.close();
        } catch (MqttException me) {
            log.error("MQTT LL ERROR :" + me.getMessage());
        }
    }


    @Override
    public void deliveryComplete(IMqttDeliveryToken arg0) {
        // log.info("MQTT LL - delivery completed");
    }

    @Override
    public void connectionLost(Throwable arg0) {
        log.error("MQTT LL - Connection Lost");
        try {
            // immediate retry, then will be async
            log.error("MQTT LL - Direct reconnecting");
            this.connect();
            log.error("MQTT LL - Direct reconnected");
        } catch (MqttException me) {
            log.warn("MQTT LL - Direct reconnection failed - " + me.getMessage());
            pendingReconnection = true;
        }
        prometeusService.addMqttConnectionLoss();
    }

    @Override
    public void messageArrived(String topicName, MqttMessage message) throws Exception {
    }


    // =================================================================
    // Service management
    // =================================================================
    private long lastErrorLog = 0;
    @Scheduled(fixedDelay = 20) // forever until all queue empty
    protected void processQueues() {
        if ( requestToStop && chirpstackQSz == 0 && bridgeQSz == 0 ) return;
        synchronized (scheduleRunningLock) {
            scheduleRunning++;
        }
        try {
            //
            // We want to give a priority on the bridge processing but in the same time not
            // having the Chiprstack messages not proceeded, so let's consider we want a maximum
            // late for a Chirpstack message of 5s after arrival and bridge fresher than the Chirpstack messages
            //
            while ( chirpstackQSz > 0 || bridgeQSz > 0 ) {
                long now = Now.NowUtcMs();
                MqttEvent c = chipstackQueue.peek();
                MqttEvent b = bridgeQueue.peek();
                MqttEvent e;
                if ( b == null && c == null ) break;
                if ( b == null ) {
                    //synchronized(bridgeQSzLock) {bridgeQSz=0;} // not sure as it is asynchronous
                    if ( c.arrivalTime < (now - 100) ) {
                        // we want to make sure bridge had time to process events
                        // not a problem to delay a bit the chirpstack queue processing
                        // for getting stuff in the right order, even if it should ...
                        e = chipstackQueue.poll();
                        synchronized(chirpstackQSzLock) {chirpstackQSz--;}
                        prometeusService.chirpstackQueueSet(chirpstackQSz);
                    } else {
                        Tools.sleep(10);
                        continue; // try again
                    }
                } else if ( c == null ) {
                    // synchronized(chirpstackQSzLock) {chirpstackQSz=0;}
                    e = bridgeQueue.poll();
                    synchronized(bridgeQSzLock) {bridgeQSz--;}
                    prometeusService.bridgeQueueSet(bridgeQSz);
                } else {
                    if (b.arrivalTime < (c.arrivalTime + 200)) {
                        // give priority on bridge processing based on arrival time with 200ms advantage
                        e = bridgeQueue.poll();
                        synchronized(bridgeQSzLock) {bridgeQSz--;}
                        prometeusService.bridgeQueueSet(bridgeQSz);
                    } else {
                        // no risk of deadlock as C is processed when older than bridge + 100ms
                        e = chipstackQueue.poll();
                        synchronized(chirpstackQSzLock){chirpstackQSz--;}
                        prometeusService.chirpstackQueueSet(chirpstackQSz);
                        if ( (now - e.arrivalTime ) > 5_000 && (now - lastErrorLog) > 30_000 ) {
                            log.error("MQTT LL Chirpstack event processing delayed by +5s");
                            lastErrorLog = now;
                        }
                    }
                }
                // now "e" is what we want to process
                if ( e.messageType == MSG_TYPE_BRIDGE ) {
                    processBridgeMessage(now,e);
                } else if ( e.messageType == MSG_TYPE_CHIPSTACK ) {
                    processChirpstackMessage(now,e);
                } else {
                    log.error("MQTT LL invalid type of message (should not be here)");
                }
                // some post checks
                if ( chirpstackQSz > 500 || bridgeQSz > 500 ) {
                    if ( (now - lastErrorLog) > 30_000 || (lastErrorLog == now)) {
                        log.error("MQTT LL Queues are becoming too big (chirp: "+chirpstackQSz+") (bridg: "+bridgeQSz+")");
                        lastErrorLog = now;
                    }
                }
            }
            //log.debug("MQTT LL - All proceeded");
        } catch (Exception x) {
            log.error("MQTT LL Exception in message processing "+x.getMessage());
            x.printStackTrace();
        } finally {
            synchronized (scheduleRunningLock) {
                scheduleRunning--;
            }
        }
    }

    // ===============================================================
    // Keep track of strange hotspot
    // ===============================================================

    protected static final int HS_NOT_INVOICED_MAX_DEVICES = 10;
    protected static final int HS_NOT_INVOICED_MAX_HS = 100;

    protected static final long HS_CLEAR_AFTER = 6*Now.ONE_HOUR;

    // Track some hotspot where we see not invoiced traffic and want to
    // understand why and who they are.
    protected static class HotspotNotInvoiced {
        public String eui;
        public String id;
        public long seenFrom;
        public long seenLast;

        public long invoicedPackets;
        public long nonInvoicedPackets;
        public ArrayList<String> seeDevices;

        // ---
        public HotspotNotInvoiced(String eui) {
            this.eui = eui;
            this.id = null;
            this.seenFrom = Now.NowUtcMs();
            this.seenLast = this.seenFrom;
            this.invoicedPackets = 0;
            this.nonInvoicedPackets = 0;
            this.seeDevices = new ArrayList<>();
        }

        public void addNonInvoicedPacket() {
            this.seenLast = Now.NowUtcMs();
            this.nonInvoicedPackets++;
        }

        public void addInvoicedPacket(String device) {
            this.seenLast = Now.NowUtcMs();
            this.invoicedPackets++;
            boolean found = false;
            for ( String dev : this.seeDevices ) {
                if ( dev.compareTo(device) == 0 ) { found = true; break; };
            }
            if ( !found && this.seeDevices.size() < HS_NOT_INVOICED_MAX_DEVICES ) {
                this.seeDevices.add(device);
            }
        }

    }

    protected final Object lockHotspotHash =  new Object();
    protected HashMap<String,HotspotNotInvoiced> hotspotHash = new HashMap<>();


    // ===============================================================
    // Keep track of packets
    // ===============================================================

    protected static final long HPR_PACKET_WINDOW_TIMEOUT = 30 * Now.ONE_MINUTE;
    protected static final long HPR_PACKET_FULL_TIMEOUT = 2 * Now.ONE_MINUTE;
    protected static final long PACKET_CACHE_TIMEOUT = 10_000;
    protected static final long PACKET_DEDUP_MAXSZ = 50_000;

    protected static class DeviceDedup {
        public String devEui;
        public int count;
        public long lastSeen;
    }

    private final Object lockJoinDedup = new Object();
    protected HashMap<String, DeviceDedup> dedupHashMap = new HashMap<>();

    private final Object lockPacketDedup = new Object();
    private final Object lockRecentPacketDedup = new Object();
    private final Object lockPreprocessedPacketDedup = new Object();

    protected static class ToDedup {
        public long firstArrivalTime;       // server time for first of the packets
        public long firstRxTime;            // hotspot time, this to eventually clean the data
        public boolean isJoin;              // true when a join packet
        public String key;                  // entry key
        public String firstGatewayId;       // for identification
        public String secondGatewayId;      // backup if exists
        public String devAddr;              // device devaddr for identification
        public int fCnt;                    // for identification
        public int dataSz;                  // data size for invoicing
        public int duplicates;              // number of copy received
        public int duplicatesInvoiced;      // what chirpstack received
        public String deviceEui;            // who is to be invoiced
        public String tenantId;
        public byte[] _deviceEui;           // raw devEui
    }

    protected HashMap<String, ToDedup> packetDedup = new HashMap<>();
    protected LinkedList<ToDedup> recentPacketDedup = new LinkedList<>();
    protected ArrayList<ToDedup> preprocessedPacketDedup = new ArrayList<>();

    protected static class ToInvoice {
        public String devEui;
        public int dcs;
    }

    private long lastCleanJoinDedup = 0;
    private long lastCleanLateDedup = 0;

    // ===============================================================
    // Process raw messages from bridge
    // ===============================================================

    @Autowired
    protected RoamingService roamingService;

    public void processBridgeMessage(long now, MqttEvent e) {
        try {
            UplinkFrame uf = UplinkFrame.parseFrom(e.message.getPayload());
            // 00 9A2E3DD7EFF98160 9861BFC396F98160 75AB   D683 EED2
            //       app eui (rev)   dev eui (rev)  nonce  MIC  CRC

            byte[] payload = uf.getPhyPayload().toByteArray();

            // Here we have a problem: the timestamp is a second and not ms, so we have an average offset of 500ms whatever
            // As a reminder for the next time, the nsTime we see in the front end is "network server time" so the reception time
            // on the network server, this one is in ns but is not the precise time from the hotspot and this is also why, here the
            // value is 0 (not yet enriched)
            // potential fields time_since_gps_epoch / fine_time_since_gps_epoch could be more interesting but filled when the GW as a GPS or TDOA
            long rx = (uf.getRxInfo().getGwTime().getSeconds() * 1000) + (uf.getRxInfo().getGwTime().getNanos() / 1_000_000);
            
            boolean isJoin = ((payload[0] & 0xE0) == 0 && payload.length == 23);
            if ( !isJoin ) {
                // join packets are free, other invoiced
                prometeusService.addHeliumInvoicedPacket();
            }

            String spayload = HexaConverters.byteToHexString(payload);
            // Manage arrival time for the first frame
            ToDedup dedup;
            synchronized (lockPacketDedup) {
                dedup = packetDedup.get(spayload);
            }
            if (dedup == null) {
                // *** FIRST
                // first time we see this packet
                dedup = new ToDedup();
                dedup.firstGatewayId = uf.getRxInfo().getGatewayId().toLowerCase();
                dedup.secondGatewayId = null;
                dedup.firstArrivalTime = e.arrivalTime;
                dedup.firstRxTime = rx;
                dedup.key = spayload;
                dedup.duplicates = 1;
                dedup.duplicatesInvoiced = 0;
                dedup.isJoin = isJoin;
                if (!isJoin) {
                    // Packet is
                    // 40 XX XX XX XX YY ZZ ZZ K..K FF P..P MM MM MM MM
                    // MHDR 1B 0x40 here => type = 2 (up unconf) 4 = (un conf) 6 = RFU 7 = Prop 0 = join Req
                    // where XX are DevAddr reversed byte order
                    // YY FCtrl (last 4b = FOpts Sz)
                    // ZZ FCnt
                    // K..K FOpts 0..120b 0..15B
                    // FF FPort 1B (only if Payload > 1B) else it's not here
                    // P..P Payload
                    // MM MIC : 4B

                    if ( (payload[0] & 0xE0) != 0x40 && (payload[0] & 0xE0) != 0x80 ) {
                        // not a Uplink packet
                        log.warn(">> Receiving a packet with Frame Type "+((payload[0] & 0xE0) >> 5)+" from "+dedup.firstGatewayId);
                    }

                    dedup.deviceEui = null;
                    dedup.tenantId = null;
                    dedup.devAddr = HexaConverters.byteToHexString(payload[4]) +
                        HexaConverters.byteToHexString(payload[3]) +
                        HexaConverters.byteToHexString(payload[2]) +
                        HexaConverters.byteToHexString(payload[1]);
                    dedup.fCnt = Stuff.getIntFromByte(payload[7]) * 256 + Stuff.getIntFromByte(payload[6]);
                    // dataSz is not yet known, get an estimate to monitor the unassociated packets
                    // Sz will be corrected on chirpstack event reception
                    int pLen = spayload.length() / 2;
                    if ( payload.length != pLen ) {
                        log.warn(">> payload len: "+payload.length+" / pLen: "+pLen+" with: "+spayload);
                        log.warn(">> payload "+HexaConverters.byteToHexString(payload[0])+", "+HexaConverters.byteToHexString(payload[1])
                            +", "+HexaConverters.byteToHexString(payload[2])+", "+HexaConverters.byteToHexString(payload[3]));
                    }
                    if ( pLen >= 13 ) {
                        // 13B minimum payload structure - FOpts Size
                        dedup.dataSz = (pLen - 13) - ( Stuff.getIntFromByte(payload[5]) & 0x0F );
                        if ( dedup.dataSz == -1 ) dedup.dataSz = 0; // this is an empty payload, FPort absent
                        if ( dedup.dataSz < 0 ) {
                            log.error("LoRa Payload Incorrect size computation "+spayload);
                            dedup.dataSz = 0;
                        }
                    } else {
                        // when payload len is 0, FPort is not transmitted, frame is 12B
                        if ( pLen < 12 ) {
                            log.info("Detect a short LoRa frame under minimum "+spayload);
                        }
                        dedup.dataSz = 0;
                    }
                    synchronized (lockRecentPacketDedup) {
                        recentPacketDedup.addLast(dedup);
                        // clear the old one
                        while ((e.arrivalTime - recentPacketDedup.getFirst().firstArrivalTime) > PACKET_CACHE_TIMEOUT) {
                            recentPacketDedup.removeFirst();
                        }
                    }
                    log.debug("First uplink arriving for devaddr " + dedup.devAddr + " with fCnt " + dedup.fCnt + " after " + (now - dedup.firstArrivalTime) + "ms from " + uf.getRxInfo().getGatewayId());
                } else {
                    // The join case
                    dedup._deviceEui = new byte[8]; // reverse the bytes of the address
                    for (int i = 0; i < 8; i++) {
                        dedup._deviceEui[i] = payload[(9 + 8 - 1) - i];
                    }
                    dedup.deviceEui = HexaConverters.byteToHexString(dedup._deviceEui);
                    dedup.tenantId = null;
                    dedup.devAddr = null;
                    dedup.fCnt = 0;
                    dedup.dataSz = 23;
                }
                synchronized (lockPacketDedup) {
                    packetDedup.put(spayload, dedup);
                }
                if (isJoin) {
                    log.debug("New join detected from " + dedup.firstGatewayId + " for device " + dedup.deviceEui);
                } else {
                    log.debug("New Uplink detected from " + dedup.firstGatewayId + " for devaddr " + dedup.devAddr + " with fCnt " + dedup.fCnt);
                }
                if ((e.arrivalTime - rx) < 1_000) {
                    // consider it as a gateway time error more than the reality of the travel time.
                    // we could also measure the mqtt latency to correct the internal process duration
                    if ( (e.arrivalTime - rx ) > 0 ) {
                        // gateway time looks acceptable
                        prometeusService.addLoRaFirstUplink(e.arrivalTime - rx);
                    }
                }
            } else {
                // *** COPY
                // Update the stat on existing packet
                dedup.duplicates++;
                if ( dedup.secondGatewayId == null ) dedup.secondGatewayId = uf.getRxInfo().getGatewayId().toLowerCase();
                // update the late stats
                if ((e.arrivalTime - dedup.firstArrivalTime) > mqttConfig.getChirpstackDedupDelayMs()) {
                    if ( !isJoin) {
                        prometeusService.addLoRaLateUplink(e.arrivalTime - dedup.firstArrivalTime);
                        log.debug("Late uplink arriving for devaddr " + dedup.devAddr + " with fCnt " + dedup.fCnt + " after " + (e.arrivalTime - dedup.firstArrivalTime) + "ms from " + uf.getRxInfo().getGatewayId());
                    } else {
                        // join
                        log.debug("Late join arriving for devEui " + dedup.deviceEui + " after " + (now - dedup.firstArrivalTime) + "ms from " + uf.getRxInfo().getGatewayId());
                    }
                } else {
                    if ( !isJoin ) {
                        log.debug("OnTime uplink arriving for devaddr " + dedup.devAddr + " with fCnt " + dedup.fCnt);
                    } else {
                        log.debug("OnTime join arriving for deveui " + dedup.deviceEui);
                    }
                }
            }

            // count packets received at gateway level (invoiced by HPR, potentially rejected by LNS)
            if (isJoin) {
                prometeusService.addLoRaJoinRequest(e.arrivalTime - rx);
            } else {
                if ( rx >= (dedup.firstRxTime-100) ) {
                    // if a frame arrives after the first one but in the past with more than
                    // 100 ms, we can consider the clock as invalid, first clock or next clocks
                    prometeusService.addLoRaGatewayUplink(e.arrivalTime - rx);

                    // Measure uplink confirmed processing time
                    // Based on GW time, so it's an approximation
                    if ((payload[0] & 0xE0) == 0x80 && rx > 0) {
                        // header for confirmed frame - compute elapse time in ms
                        prometeusService.addLoRaUplinkConf(
                            e.arrivalTime - rx
                        );
                    }
                }
            }

            // Manage zone switch on Join Requests
            // Manage DC count for Join Requests
            if (isJoin) {
                // possible Join Request
                DeviceDedup d;
                synchronized (lockJoinDedup) {
                    d = dedupHashMap.get(dedup.deviceEui);
                }
                if (d == null || (e.arrivalTime - d.lastSeen) > Now.ONE_MINUTE) {
                    // found a new join for that device
                    if (d == null) {
                        d = new DeviceDedup();
                        d.count = 1;
                        d.devEui = dedup.deviceEui;
                        d.lastSeen = e.arrivalTime;

                        // Only new join session are processed for zone change
                        if (mqttConfig.getHeliumZoneDetectionEnable()) {
                            String region = e.topic.substring(0, e.topic.indexOf("/"));
                            // ... push to process, this is synchronous action, can be long
                            // and delay the rest of the timing, rare but could be optimized.
                            log.debug("Found a join request for " + d.devEui + " for region " + region);
                            roamingService.processJoinMessage(dedup._deviceEui, d.devEui, region);
                        }
                    } else {
                        // new session but we keep the structure
                        d.lastSeen = e.arrivalTime;
                        d.count++;
                    }
                    synchronized (lockJoinDedup) {
                        dedupHashMap.put(dedup.deviceEui, d);
                    }
                } else {
                    // count a new Join to invoice
                    d.count++;
                }
            }
            log.debug("MQTT LL processing time "+(Now.NowUtcMs()-now)+"ms for "+e.topic);
            prometeusService.addLoRaMessageProcessing(Now.NowUtcMs()-now);
        } catch (InvalidProtocolBufferException x) {
            log.error("MQTT LL - Impossible to parse raw message from bridge");
        } catch (Exception x) {
            log.error("MQTT LL - Exception in processing bridge message "+x.getMessage());
            x.printStackTrace();
        }
    }

    // ===============================================================
    // Process message from chirpstack
    // ===============================================================

    @Autowired
    protected HeliumTenantService heliumTenantService;

    public void processChirpstackMessage(long now, MqttEvent e) {
        try {
            if (matchUplink.matcher(e.topic).matches()) {
                try {
                    UplinkEvent up = mapper.readValue(e.message.toString(), UplinkEvent.class);
                    int dataSz = Base64.decode(up.getData()).length;
                    prometeusService.addLoRaUplink(
                        e.arrivalTime - DateConverters.StringDateToMs(up.getTime()),
                        dataSz,
                        true,
                        up.getRxInfo().size() - 1
                    );
                    log.debug("UPLINK Dev: " + up.getDeviceInfo().getDevEui() + " Adr:" + up.getDevAddr() + " Fcnt:"+up.getfCnt()+"("+(up.getfCnt()&0xFFFF)+") duplicates:" + up.getRxInfo().size() + " size: " + Base64.decode(up.getData()).length);
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
                        theDedup = recentPacketDedup.parallelStream().filter(dedup -> {
                            if (dedup.fCnt == (up.getfCnt() & 0xFFFF) && !dedup.isJoin && dedup.devAddr.compareToIgnoreCase(up.getDevAddr()) == 0) {
                                if (dedup.deviceEui != null && dedup.deviceEui.compareToIgnoreCase(up.getDeviceInfo().getDevEui().toLowerCase()) == 0) {
                                    return true;
                                } else {
                                    for (UplinkEventRxInfo gw : up.getRxInfo()) {
                                        String gwId = gw.getGatewayId().toLowerCase();
                                        if (  gwId.compareTo(dedup.firstGatewayId) == 0 || ( dedup.secondGatewayId !=null && gwId.compareTo(dedup.secondGatewayId) == 0) )
                                            return true;
                                    }
                                }
                            }
                            return false;
                        }).findFirst();
                    }
                    // Eventually a deep search on the whole history
                    // This is mostly the case for the late packets to reattach the right one
                    // But also happen when chirpstack event comes before the up event.
                    if (theDedup.isEmpty()) {
                        log.debug("Uplink not found in the recent history... deep search");
                        theDedup = packetDedup.values().parallelStream().filter(dedup -> {
                            if (dedup.fCnt == (up.getfCnt() & 0xFFFF) && !dedup.isJoin && dedup.devAddr.compareToIgnoreCase(up.getDevAddr()) == 0) {
                                if (dedup.deviceEui != null && dedup.deviceEui.compareToIgnoreCase(up.getDeviceInfo().getDevEui()) == 0) {
                                    return true;
                                } else {
                                    for (UplinkEventRxInfo gw : up.getRxInfo()) {
                                        String gwId = gw.getGatewayId().toLowerCase();
                                        if (  gwId.compareTo(dedup.firstGatewayId) == 0 || ( dedup.secondGatewayId !=null && gwId.compareTo(dedup.secondGatewayId) == 0) )
                                            return true;
                                    }
                                }
                            }
                            return false;
                        }).findFirst();
                        //if (theDedup.isPresent()) log.warn("Uplink found in recent histo");
                    }

                    // give the information for late packet invoice later
                    if (theDedup.isPresent()) {
                        ToDedup d = theDedup.get();
                        if (d.deviceEui == null) {
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
                        log.warn("Found a packet invoiced with no dedup reference " + up.getDeviceInfo().getDevEui() + " with fCnt " + up.getfCnt() + " and devAddr " + up.getDevAddr());
                        ToDedup d = new ToDedup();
                        d.deviceEui = up.getDeviceInfo().getDevEui();
                        d.fCnt = up.getfCnt() & 0xFFFF;
                        d.devAddr = up.getDevAddr();
                        d.tenantId = up.getDeviceInfo().getTenantId();
                        d.duplicatesInvoiced = up.getRxInfo().size(); // this is a total invoiced ( first + duplicate )
                        d.dataSz = dataSz;
                        d.firstGatewayId = up.getRxInfo().get(0).getGatewayId().toLowerCase();
                        if ( up.getRxInfo().size() > 1 ) {
                            d.secondGatewayId = up.getRxInfo().get(1).getGatewayId().toLowerCase();
                        }
                        d.firstArrivalTime = Now.NowUtcMs();
                        d.key = null;
                        d.duplicates = 0;
                        d.isJoin = false;
                        synchronized (lockPreprocessedPacketDedup) {
                            if (preprocessedPacketDedup.size() < 1000) this.preprocessedPacketDedup.add(d);
                            else log.warn("preprocessedPacketDedup is full");
                        }
                    }

                    // Process the Hotspot Not Invoiced update
                    // if one of the device hotspot is under monitoring, we identify it and associate the devices
                    if ( HS_NOT_INVOICED_MAX_HS > 0 ) {
                        for ( UplinkEventRxInfo rx : up.getRxInfo() ) {
                            HotspotNotInvoiced hni = null;
                            synchronized (lockHotspotHash) {
                                hni = hotspotHash.get(rx.getGatewayId().toLowerCase());
                            }
                            if ( hni != null ) {
                                if ( hni.id == null ) hni.id = rx.getMetadata().getGateway_id();
                                hni.addInvoicedPacket(up.getDeviceInfo().getDevEui());
                            }
                        }
                    }

                } catch (JsonProcessingException x) {
                    log.error("MQTT LL - failed to parse App uplink - " + x.getMessage());
                    x.printStackTrace();
                } catch (Exception x) {
                    x.printStackTrace();
                }
            } else if (matchJoin.matcher(e.topic).matches()) {
                try {
                    JoinEvent je = mapper.readValue(e.message.toString(), JoinEvent.class);
                    log.debug("JOIN - Dev: " + je.getDeviceInfo().getDeviceName() + " Adr:" + je.getDevAddr() + " timestamp :" + DateConverters.StringDateToMs(je.getTime()));
                    heliumTenantService.processJoinAccept(
                        je.getDeviceInfo().getTenantId(),
                        je.getDeviceInfo().getDevEui(),
                        je.getDevAddr()
                    );
                    prometeusService.addLoRaJoin();
                } catch (JsonProcessingException x) {
                    log.error("MQTT LL - failed to parse App JOIN - " + x.getMessage());
                    x.printStackTrace();
                } catch (Exception x) {
                    x.printStackTrace();
                }
            } else if (matchLog.matcher(e.topic).matches()) {
                try {
                    LogEvent le = mapper.readValue(e.message.toString(), LogEvent.class);
                    log.debug("LOG - Dev: "+ le.getDeviceInfo().getDeviceName() + " Eui: "+ le.getDeviceInfo().getDevEui() + " Tenant: "+le.getDeviceInfo().getTenantId());
                    if ( le.getCode().compareToIgnoreCase("UPLINK_F_CNT_RESET") == 0 ) {
                        log.info("Found an UPLINK_F_CNT_RESET for devEui: "+le.getDeviceInfo().getDevEui());
                        // anormal behavior use as an attack, pay 100 Uplink equivalent
                        heliumTenantService.punish(
                            le.getDeviceInfo().getTenantId(),
                            le.getDeviceInfo().getDevEui()
                        );
                    }
                } catch (Exception x) {
                    x.printStackTrace();
                }
            } else {
                // standard json messages
                log.debug("MQTT LL - MessageArrived on "+e.topic);
                //log.info("MQTT LL - message "+message);
            }
            log.debug("MQTT LL processing time "+(Now.NowUtcMs()-now)+"ms for "+e.topic);
            prometeusService.addLoRaMessageProcessing(Now.NowUtcMs()-now);

        } catch (Exception x) {
            log.error("MQTT LL - Exception in processing chirpstack message "+x.getMessage());
            x.printStackTrace();
        }
    }

    // ===============================================================
    // Background cleaning tasks
    // ===============================================================

    @Scheduled(fixedDelayString = "${helium.mqtt.dedup.scanPeriod}", initialDelay = 120_000) // default 10m
    protected void cleanDedupCache() {
        if ( this.requestToStop ) return;
        synchronized (scheduleRunningLock) {
            this.scheduleRunning++;
        }
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
                    heliumTenantService.processJoinRequest(t.devEui, t.dcs);
                }
                this.lastCleanJoinDedup = now;
            }

            int postInvoiced = 0;
            int notInvoicable = 0;
            int cost = 0;
            // clean the dedup packets
            if (packetDedup.size() > PACKET_DEDUP_MAXSZ || (now - lastCleanLateDedup) > 8 * Now.ONE_MINUTE) {
                boolean isFull = (packetDedup.size() > PACKET_DEDUP_MAXSZ);
                if (isFull) log.warn("MQTT LL - PacketDedup is running full " + packetDedup.size());

                // identify what to be removed
                List<ToDedup> toRemove;
                synchronized (lockPacketDedup) {
                    toRemove = packetDedup.values().parallelStream().filter(dedup -> (!isFull && (now - dedup.firstArrivalTime) > HPR_PACKET_WINDOW_TIMEOUT)
                        || (isFull && (now - dedup.firstArrivalTime) > HPR_PACKET_FULL_TIMEOUT)).collect(Collectors.toList());
                }
                log.debug("cleanDedupCache - Found " + toRemove.size() + " packets to clean");

                // process invoicing
                for (ToDedup d : toRemove) {
                    if ( !d.isJoin ) {
                        if (d.duplicatesInvoiced < d.duplicates && d.deviceEui != null && d.tenantId != null) {
                            log.debug("cleanDedupCache - Found device to invoice late packets " + d.deviceEui + " (" + (d.duplicates - d.duplicatesInvoiced) + ") fCnt " + d.fCnt + " devAdr " + d.devAddr);
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
                                if (_d.devAddr != null) {
                                    if (d.fCnt == _d.fCnt && d.devAddr.compareToIgnoreCase(_d.devAddr) == 0) {
                                        if (Math.abs(d.firstArrivalTime - _d.firstArrivalTime) < 30_000) {
                                            // it may be the same, process it
                                            log.debug("cleanDedupCache - Searched device to invoice late packets " + _d.deviceEui + " (" + (_d.duplicates - _d.duplicatesInvoiced) + ") fCnt " + _d.fCnt + " devAdr " + _d.devAddr);
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
                                        } else {
                                            log.warn("cleanDedupCache - Unattributed packet " + d.devAddr + " / " + d.fCnt + " with long delay: " + Math.abs(d.firstArrivalTime - _d.firstArrivalTime));
                                        }
                                    }
                                } else {
                                    log.error("### Got a null devaddr ?? " + _d.fCnt + " " + _d.firstArrivalTime + " ");
                                }
                            }
                            if (!found) {
                                // trace Hotspot information
                                HotspotNotInvoiced hni = hotspotHash.get(d.firstGatewayId);
                                if ( hni != null ) {
                                    hni.addNonInvoicedPacket();
                                } else {
                                    if ( hotspotHash.size() >= HS_NOT_INVOICED_MAX_HS ) {
                                        // clean structure
                                        ArrayList<String> _toRemove = new ArrayList<>();
                                        for ( HotspotNotInvoiced _hni : hotspotHash.values() ) {
                                            if ( _hni.seenLast < ( now - HS_CLEAR_AFTER ) ) {
                                                _toRemove.add(_hni.eui);
                                            }
                                        }
                                        synchronized (lockHotspotHash) {
                                            for ( String s : _toRemove ) hotspotHash.remove(s);
                                        }
                                    }
                                    if ( hotspotHash.size() < HS_NOT_INVOICED_MAX_HS ) {
                                        hni = new HotspotNotInvoiced(d.firstGatewayId);
                                        hni.addNonInvoicedPacket();
                                        hotspotHash.put(d.firstGatewayId.toLowerCase(),hni);
                                    } else {
                                        log.warn("Hotspot "+d.firstGatewayId+" will not track, too many under monitoring already");
                                    }
                                }
                                // log
                                log.warn("Found a packetDedup without uplink event at "+DateConverters.msToStringDate(d.firstArrivalTime)+" for " + d.devAddr + " / " + d.fCnt +
                                    " from " + d.firstGatewayId + " sz "+d.dataSz+" with " + d.duplicates + " dup");
                                cost += d.duplicates * ( (d.dataSz/24) + 1);
                                notInvoicable += d.duplicates;
                            }
                        }
                    }
                }
                if ( notInvoicable > 0 ) {
                    log.warn("MQTT LL - cleanDedupCache - late packets invoiced "+postInvoiced+" not invoiced "+notInvoicable+" (cost "+cost+"DCs)");
                    prometeusService.addHeliumNotInvoicedPacket(cost);
                } else {
                    if ( postInvoiced > 0 ) log.debug("MQTT LL - cleanDedupCache - late packets invoiced " + postInvoiced);
                }

                // display information about hni
                for ( HotspotNotInvoiced hni : hotspotHash.values() ) {
                    if ( hni.seenLast > ( now - Now.ONE_HOUR ) && hni.nonInvoicedPackets > 30 ) {
                       log.info("HS Non Invoiced "+hni.eui+" ("+((hni.id!=null)?hni.id:"Unknown")+") "+hni.invoicedPackets+" invoiced vs "+hni.nonInvoicedPackets+" missed ");
                       String devs = "";
                       for ( String dev : hni.seeDevices ) devs = devs.concat("[").concat(dev).concat("], ");
                       log.info("  Device : "+devs);
                    }
                }

                // clean
                synchronized (lockPacketDedup) {
                    for (ToDedup d : toRemove) {
                        packetDedup.remove(d.key);
                    }
                }
                if (isFull) log.warn("MQTT LL - PacketDedup is running full, new size " + packetDedup.size());

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
            log.error("MQTT LL - Exception in cleanDedupCache "+x.getMessage());
            x.printStackTrace();
        } finally {
            synchronized (scheduleRunningLock) {
                --this.scheduleRunning;
            }
        }
    }


}

