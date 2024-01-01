package eu.heliumiot.console.mqtt;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.google.protobuf.InvalidProtocolBufferException;
import com.google.type.DateTime;
import eu.heliumiot.console.ConsoleConfig;
import eu.heliumiot.console.jpa.db.HeliumParameter;
import eu.heliumiot.console.service.HeliumParameterService;
import eu.heliumiot.console.service.HeliumTenantService;
import eu.heliumiot.console.service.PrometeusService;
import eu.heliumiot.console.service.RoamingService;
import fr.ingeniousthings.tools.*;
import io.chirpstack.api.gw.UplinkFrame;
import io.chirpstack.json.JoinEvent;
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

import javax.annotation.PostConstruct;

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
    protected ConcurrentLinkedQueue<MqttEvent> chipstackQueue = new ConcurrentLinkedQueue<MqttEvent>();


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
            heliumTenantService.invoiceJoin(_d.devEui, _d.count);
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

    // other interesting patterns
    // application/.*/event/log$
    // application/.*/event/down$

    @PostConstruct
    public MqttClient initMqtt() {

        // precompile some regex
        matchUplink = Pattern.compile("application/.*/event/up$");
        matchJoin = Pattern.compile("application/.*/event/join$");


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
                    prometeusService.chirpstackQueueSet(chipstackQueue.size());
                    log.debug("MQTT LL Add one message from chirpstack, queue size "+chipstackQueue.size());
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
                    prometeusService.bridgeQueueSet(bridgeQueue.size());
                    log.debug("MQTT LL Add one message from bridge, queue size "+bridgeQueue.size());
                }
            };
            this.connect();

            this.mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
            this.mapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
            this.mapper.configure(SerializationFeature.FAIL_ON_SELF_REFERENCES, false);

            log.info("MQTT L Starting Mqtt listener");
        } catch (MqttException me) {
            log.error("MQTT L Init ERROR : " + me.getMessage());
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
        // log.info("MQTT L - delivery completed");
    }

    @Override
    public void connectionLost(Throwable arg0) {
        log.error("MQTT LL - Connection Lost");
        try {
            // immediate retry, then will be async
            log.error("MQTT L - Direct reconnecting");
            this.connect();
            log.error("MQTT L - Direct reconnected");
        } catch (MqttException me) {
            log.warn("MQTT L - Direct reconnection failed - " + me.getMessage());
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
        if ( requestToStop && chipstackQueue.size() == 0 && bridgeQueue.size() == 0 ) return;
        synchronized (scheduleRunningLock) {
            scheduleRunning++;
        }
        try {
            //
            // We want to give a priority on the bridge processing but in the same time not
            // having the Chiprstack messages not proceeded, so let's consider we want a maximum
            // late for a Chirpstack message of 5s after arrival and bridge fresher than the Chirpstack messages
            //
            while ( chipstackQueue.size() > 0 || bridgeQueue.size() > 0 ) {
                long now = Now.NowUtcMs();
                MqttEvent c = chipstackQueue.peek();
                MqttEvent b = bridgeQueue.peek();
                MqttEvent e;
                if ( b == null && c == null ) break;
                if ( b == null ) {
                    if ( c.arrivalTime < (now - 100) ) {
                        // we want to make sure bridge had time to process events
                        // not a problem to delay a bit the chirpstack queue processing
                        // for getting stuff in the right order, even if it should ...
                        e = chipstackQueue.poll();
                        prometeusService.chirpstackQueueSet(chipstackQueue.size());
                    } else {
                        Tools.sleep(10);
                        continue; // try again
                    }
                } else if ( c == null ) {
                    e = bridgeQueue.poll();
                    prometeusService.bridgeQueueSet(bridgeQueue.size());
                } else {
                    if ( c.arrivalTime < ( now - 5_000) ) {
                        // security on processing the chirpstack event and billing (event if this situation should
                        // not be reached, but if bridge queue is going really slow)
                        e = chipstackQueue.poll();
                        prometeusService.chirpstackQueueSet(chipstackQueue.size());
                        if ( (now - lastErrorLog) > 30_000 ) {
                            log.error("MQTT LL Chirpstack event processing delayed by +5s");
                            lastErrorLog = now;
                        }
                    } else {
                        if (b.arrivalTime < (c.arrivalTime + 100)) {
                            // give priority on bridge processing based on arrival time with 100ms advantage
                            e = bridgeQueue.poll();
                            prometeusService.bridgeQueueSet(bridgeQueue.size());
                        } else {
                            e = chipstackQueue.poll();
                            prometeusService.chirpstackQueueSet(chipstackQueue.size());
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
                if ( chipstackQueue.size() > 500 || bridgeQueue.size() > 500 ) {
                    if ( (now - lastErrorLog) > 30_000 || (lastErrorLog == now)) {
                        log.error("MQTT LL Queues are becoming too big (chirp: "+chipstackQueue.size()+") (bridg: "+bridgeQueue.size()+")");
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
            long rx = (uf.getRxInfo().getTime().getSeconds() * 1000) + (uf.getRxInfo().getTime().getNanos() / 1_000_000);
            boolean isJoin = (payload[0] == 0 && payload.length == 23);

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
                dedup.firstGatewayId = uf.getRxInfo().getGatewayId();
                dedup.firstArrivalTime = e.arrivalTime;
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
                    dedup.fCnt = Stuff.getIntFromByte(payload[7]) * 256 + Stuff.getIntFromByte(payload[6]);
                    dedup.dataSz = 0;
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
                    prometeusService.addLoRaFirstUplink(now - rx);
                }
            } else {
                // *** COPY
                // Update the stat on existing packet
                dedup.duplicates++;
                // update the late stats
                if ((e.arrivalTime - dedup.firstArrivalTime) > mqttConfig.getChirpstackDedupDelayMs()) {
                    if ( !isJoin) {
                        prometeusService.addLoRaLateUplink(now - dedup.firstArrivalTime);
                        log.debug("Late uplink arriving for devaddr " + dedup.devAddr + " with fCnt " + dedup.fCnt + " after " + (now - dedup.firstArrivalTime) + "ms from " + uf.getRxInfo().getGatewayId());
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
                prometeusService.addLoRaGatewayUplink(e.arrivalTime - rx);

                // Measure uplink confirmed processing time
                // Based on GW time, so it's an approximation
                if ((payload[0] & 0xC0) == 0x80 && uf.getRxInfo().getTime().getSeconds() > 0) {
                    // header for confirmed frame - compute elapse time in ms
                    prometeusService.addLoRaUplinkConf(
                        e.arrivalTime - rx
                    );
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
            log.debug("MQTT L processing time "+(Now.NowUtcMs()-now)+"ms for "+e.topic);
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
                                if (dedup.deviceEui != null && dedup.deviceEui.compareToIgnoreCase(up.getDeviceInfo().getDevEui()) == 0) {
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
                    // This is mostly the case for the late packets to reattach the right one
                    // But also happen when chirpstack event comes before the up event.
                    if (theDedup.isEmpty()) {
                        log.warn("Uplink not found in the recent history... deep search");
                        theDedup = packetDedup.values().parallelStream().filter(dedup -> {
                            if (dedup.fCnt == (up.getfCnt() & 0xFFFF) && !dedup.isJoin && dedup.devAddr.compareToIgnoreCase(up.getDevAddr()) == 0) {
                                if (dedup.deviceEui != null && dedup.deviceEui.compareToIgnoreCase(up.getDeviceInfo().getDevEui()) == 0) {
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
                        if (theDedup.isPresent()) log.warn("Uplink found in recent histo");
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
                        d.firstGatewayId = up.getRxInfo().get(0).getGatewayId();
                        d.firstArrivalTime = Now.NowUtcMs();
                        d.key = null;
                        d.duplicates = 0;
                        d.isJoin = false;
                        synchronized (lockPreprocessedPacketDedup) {
                            if (preprocessedPacketDedup.size() < 1000) this.preprocessedPacketDedup.add(d);
                            else log.warn("preprocessedPacketDedup is full");
                        }
                    }

                } catch (JsonProcessingException x) {
                    log.error("MQTT L - failed to parse App uplink - " + x.getMessage());
                    x.printStackTrace();
                } catch (Exception x) {
                    x.printStackTrace();
                }
            } else if (matchJoin.matcher(e.topic).matches()) {
                try {
                    JoinEvent je = mapper.readValue(e.message.toString(), JoinEvent.class);
                    log.debug("JOIN - Dev: " + je.getDeviceInfo().getDeviceName() + " Adr:" + je.getDevAddr() + " timestamp :" + DateConverters.StringDateToMs(je.getTime()));
                    heliumTenantService.processJoin(
                        je.getDeviceInfo().getTenantId(),
                        je.getDeviceInfo().getDevEui(),
                        je.getDevAddr()
                    );
                    prometeusService.addLoRaJoin();
                } catch (JsonProcessingException x) {
                    log.error("MQTT L - failed to parse App JOIN - " + x.getMessage());
                    x.printStackTrace();
                } catch (Exception x) {
                    x.printStackTrace();
                }
            } else {
                // standard json messages
                log.debug("MQTT L - MessageArrived on "+e.topic);
                //log.info("MQTT L - message "+message);
            }
            log.debug("MQTT L processing time "+(Now.NowUtcMs()-now)+"ms for "+e.topic);
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
                    heliumTenantService.invoiceJoin(t.devEui, t.dcs);
                }
                this.lastCleanJoinDedup = now;
            }

            int postInvoiced = 0;
            int notInvoicable = 0;
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
                                            log.info("cleanDedupCache - one found for " + d.devAddr + " / " + d.fCnt + " with long delay: " + Math.abs(d.firstArrivalTime - _d.firstArrivalTime));
                                        }
                                    }
                                } else {
                                    log.error("### Got a null devaddr ?? " + _d.fCnt + " " + _d.firstArrivalTime + " ");
                                }
                            }
                            if (!found) {
                                log.warn("Found a packetDedup without uplink event for " + d.devAddr + " / " + d.fCnt +
                                    " from " + d.firstGatewayId + " with " + d.duplicates + " dup");
                                notInvoicable += d.duplicates;
                            }
                        }
                    }
                }
                if ( notInvoicable > 0 ) {
                    log.warn("MQTT LL - cleanDedupCache - late packets invoiced "+postInvoiced+" not invoiced "+notInvoicable);
                } else {
                    if ( postInvoiced > 0 ) log.info("MQTT LL - cleanDedupCache - late packets invoiced " + postInvoiced);
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

