package eu.heliumiot.console.redis;

import com.google.protobuf.InvalidProtocolBufferException;
import eu.heliumiot.console.service.HeliumTenantService;
import fr.ingeniousthings.tools.HexaConverters;
import fr.ingeniousthings.tools.Now;
import io.chirpstack.api.DownlinkFrameLog;
import io.chirpstack.api.internal.Internal;
import io.lettuce.core.*;
import io.lettuce.core.api.StatefulRedisConnection;
import io.lettuce.core.api.sync.RedisCommands;
import io.lettuce.core.codec.ByteArrayCodec;
import io.lettuce.core.codec.RedisCodec;
import io.lettuce.core.codec.StringCodec;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Service
public class RedisDeviceStreamListener {

    @Autowired
    private RedisConfiguration redisConfiguration;
    private final Logger log = LoggerFactory.getLogger(this.getClass());

    private RedisCommands<String, byte[]> syncCommands;

    private StatefulRedisConnection<String, byte[]> redisConnection;
    private RedisClient redisClient;

    protected int runningJobs;
    protected boolean serviceEnable; // false to stop the services


    @PostConstruct
    public void setupRedisStreamMetaListener() {
        log.info("Init setupRedisStreamMetaListener");
        String connectionString = "redis://";
        if (redisConfiguration.getRedisUsername().length() > 0) {
            connectionString += redisConfiguration.getRedisUsername() + ":" + redisConfiguration.getRedisPassword() + "@";
        }
        connectionString += redisConfiguration.getRedisHost() + ":" + redisConfiguration.getRedisPort();
        redisClient = RedisClient.create(connectionString);
        RedisCodec<String, byte[]> codec = RedisCodec.of(new StringCodec(), new ByteArrayCodec());
        redisConnection = redisClient.connect(codec);
        syncCommands = redisConnection.sync();

        try {
            syncCommands.xgroupCreate(
                    XReadArgs.StreamOffset.from(
                            redisConfiguration.getStreamMetaKey(),
                            "0-0"),
                    redisConfiguration.getRedisCGroup()
            );
        } catch (RedisBusyException redisBusyException) {
            // consumer group exist ... basically a normal situation
        } catch (Exception x) {
            // the stream does not exist yet ... until the first device it seems we don't have it or it's name
            // has changed ... not sure ...
            // by the way, skip it for now
            log.error("### TODO Manage the stream question ...");
        }

        this.runningJobs = 0;
        this.serviceEnable = true;
    }

    // request to stop the service properly
    public void stopService() {
        this.serviceEnable = false;
        this.redisConnection.close();
        this.redisClient.shutdown();
    }

    // return true when the service has stopped all the running jobs
    public boolean hasStopped() {
        return (this.serviceEnable == false && this.runningJobs == 0);
    }

    @Autowired
    protected HeliumTenantService heliumTenantService;

    @Scheduled(fixedRateString = "${spring.redis.metaRefreshRate}", initialDelay = 2_000)
    void ListenOnRedisStreamMeta() {

        if ( ! this.serviceEnable ) return;
        this.runningJobs++;
        long start = Now.NowUtcMs();
        int processed = 0;
        try {

            @SuppressWarnings("unchecked")
            List<StreamMessage<String, byte[]>> messages = syncCommands.xreadgroup(
                    Consumer.from(redisConfiguration.getRedisCGroup(), redisConfiguration.getRedisConsumer()),
                    XReadArgs.StreamOffset.lastConsumed(redisConfiguration.getStreamMetaKey())
            );

            if (!messages.isEmpty()) {
                for (StreamMessage<String, byte[]> message : messages) {
                    Map<String, byte[]> m = message.getBody();
                    for (String k : m.keySet()) {
                        processed++;
                        if (k.compareToIgnoreCase("up") == 0) {
                            // @Todo - see how REDIS can be use later
                            // Redis would be the right way to get information but the payload size
                            // is not secured here. MQTT message are safer
                            /*
                            log.debug("REDIS - Receiving uplink");
                            byte[] byteData = message.getBody().get(k);
                            try {
                                UplinkFrameLog up = UplinkFrameLog.parseFrom(byteData);
                                // The field is not the payload but the frame size ...
                                // we can assume the size if for real more dynamic, unfortunately
                                // at this point we have nothing more precise
                                int receivedBytes = up.getPhyPayload().size()-13;
                                if (up != null) {
                                    log.debug("Dev: " + up.getDevEui() + " Adr:" + up.getDevAddr() + " duplicates:" + up.getRxInfoList().size() + " size: "+receivedBytes);
                                    heliumTenantService.processUplink(
                                            null,
                                            up.getDevEui(),
                                            receivedBytes,
                                            up.getRxInfoList().size() - 1
                                    );
                                }
                            } catch (InvalidProtocolBufferException x) {
                                log.error("Impossible to parse stream type up with " + x.getMessage());
                                log.info(HexaConverters.byteToHexStringWithSpace(byteData));
                            }
                            */
                        } else if (k.compareToIgnoreCase("down") == 0) {
                                log.debug("REDIS - Receiving downlink/ack/join accept");
                                byte[] byteData = message.getBody().get(k);
                                try {
                                    DownlinkFrameLog dwn = DownlinkFrameLog.parseFrom(byteData);
                                    if (dwn != null) {
                                        int downlinkSize =  dwn.getPhyPayload().size();
                                        if ( downlinkSize <= 12 ) downlinkSize = 0;
                                        else downlinkSize -= 13;

                                        log.debug("Dev: " + dwn.getDevEui() + " Adr:" + dwn.getDevAddr() + " size: "+downlinkSize);
                                        heliumTenantService.processDownlink(
                                                null,
                                                dwn.getDevEui(),
                                                downlinkSize
                                        );
                                    }
                                } catch (InvalidProtocolBufferException x) {
                                    log.error("Impossible to parse stream type up with " + x.getMessage());
                                    log.info(HexaConverters.byteToHexStringWithSpace(byteData));
                                }
                        } else {
                            log.warn("## Found a new key on stream:meta " + k);
                            byte[] byteData = message.getBody().get(k);
                            log.info(HexaConverters.byteToHexStringWithSpace(byteData));
                        }
                    }
                    // Confirm that the message has been processed using XACK
                    syncCommands.xack(
                            redisConfiguration.getStreamMetaKey(),
                            redisConfiguration.getRedisCGroup(),
                            message.getId()
                    );
                }
            }
        } finally {
          this.runningJobs--;
        }
        log.debug("ListenOnRedisStreamMeta - duration "+(Now.NowUtcMs()-start)+" ms, process "+processed+" new entries");

    }

    //syncCommands.xgroupDestroy("stream:meta", CONSUMER_GROUP );

}
