package eu.heliumiot.console.redis;

import com.google.protobuf.InvalidProtocolBufferException;
import eu.heliumiot.console.service.HeliumDeviceService;
import eu.heliumiot.console.service.HeliumTenantService;
import eu.heliumiot.console.service.PrometeusService;
import fr.ingeniousthings.tools.HexaConverters;
import fr.ingeniousthings.tools.Now;
import io.chirpstack.api.DownlinkFrameLog;
import io.chirpstack.api.RequestLog;
import io.lettuce.core.*;
import io.lettuce.core.api.StatefulRedisConnection;
import io.lettuce.core.api.sync.RedisCommands;
import io.lettuce.core.codec.ByteArrayCodec;
import io.lettuce.core.codec.RedisCodec;
import io.lettuce.core.codec.StringCodec;
import io.lettuce.core.protocol.ProtocolVersion;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.Map;

@Service
public class RedisDeviceEventStreamListener {

    @Autowired
    private RedisConfiguration redisConfiguration;
    private final Logger log = LoggerFactory.getLogger(this.getClass());

    private RedisCommands<String, byte[]> syncCommands;

    private StatefulRedisConnection<String, byte[]> redisConnection;
    private RedisClient redisClient;

    protected int runningJobs;
    protected boolean serviceEnable; // false to stop the services

    // When the Chripstack has just been created, it seems we can have the stream not yet
    // existing and so we fail in creating the consumer group and fetch data
    // so we will monitor this on the running job.
    protected boolean streamExists = false; // true if the groups has been created correctly aka stream not existing
    protected long lastStreamTry = 0;

    @PostConstruct
    public void setupRedisStreamMetaListener() {
        log.info("Init setupRedisStreamEventListener");
        String connectionString = redisConfiguration.isRedisSsl() ? "rediss://" : "redis://";
        if (redisConfiguration.getRedisUsername().length() > 0) {
            connectionString += redisConfiguration.getRedisUsername() + ":" + redisConfiguration.getRedisPassword() + "@";
        }
        connectionString += redisConfiguration.getRedisHost() + ":" + redisConfiguration.getRedisPort();
        redisClient = RedisClient.create(connectionString);

        redisClient.setOptions(ClientOptions.builder()
            .pingBeforeActivateConnection(true)
            .socketOptions(SocketOptions.builder()
                .keepAlive(true)
                .build())
            .protocolVersion(ProtocolVersion.RESP3)
            .build());

        RedisCodec<String, byte[]> codec = RedisCodec.of(new StringCodec(), new ByteArrayCodec());
        redisConnection = redisClient.connect(codec);
        syncCommands = redisConnection.sync();

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
    protected HeliumDeviceService heliumDeviceService;

    @Autowired
    protected PrometeusService prometeusService;

    @Scheduled(fixedRateString = "${spring.redis.metaRefreshRate}", initialDelay = 1_000)
    void ListenOnRedisStreamEvent() {

        if ( ! this.serviceEnable ) return;
        this.runningJobs++;
        long start = Now.NowUtcMs();
        int processed = 0;

        // Let's create the group if possible / needed, no need to check faster than once a minute
        if ( ! this.streamExists && ( Now.NowUtcMs() - this.lastStreamTry ) > 60_000  ) {
            this.lastStreamTry = Now.NowUtcMs();
            try {
                syncCommands.xgroupCreate(
                        XReadArgs.StreamOffset.from(
                                redisConfiguration.getStreamEventKey(),
                                "0-0"),
                        redisConfiguration.getRedisCGroup()
                );
                this.streamExists = true;
            } catch (RedisBusyException redisBusyException) {
                // consumer group exist ... basically a normal situation
                this.streamExists = true;
            } catch (Exception x) {
                // the stream does not exist yet ... until the first device it seems we don't have it or it's name
            }
            if ( !this.streamExists ) log.warn("Impossible to create the redis stream reader, will try later");
        }

        if ( this.streamExists ) {
            try {

                @SuppressWarnings("unchecked")
                List<StreamMessage<String, byte[]>> messages = syncCommands.xreadgroup(
                        Consumer.from(redisConfiguration.getRedisCGroup(), redisConfiguration.getRedisConsumer()),
                        XReadArgs.StreamOffset.lastConsumed(redisConfiguration.getStreamEventKey())
                );

                if (!messages.isEmpty()) {
                    for (StreamMessage<String, byte[]> message : messages) {
                        Map<String, byte[]> m = message.getBody();
                        for (String k : m.keySet()) {
                            processed++;
                            //log.debug("Message on "+k);
                            if (k.compareToIgnoreCase("request") == 0) {
                                byte[] byteData = message.getBody().get(k);
                                try {
                                    RequestLog req = RequestLog.parseFrom(byteData);
                                    if (req != null) {
                                        if (  req.getMethod().compareToIgnoreCase("activate") == 0 ) {
                                            if ( req.getService().compareToIgnoreCase("api.DeviceService") == 0 && req.getMetadataCount() == 1 ) {
                                                // we have an activation update
                                                String devEui = req.getMetadataMap().get("dev_eui");
                                                if ( devEui != null ) {
                                                    log.debug("Found activation change for "+devEui);
                                                    heliumDeviceService.reportDeviceActivationOnMqtt(devEui);
                                                }
                                            }
                                        } else {
                                            log.debug("Req: " + req.getMethod() + " Srv:" + req.getService() + " Meta: " + req.getMetadataCount());
                                            //req.getMetadataMap().forEach( (s,l) -> { log.debug(s+" "+l); });
                                        }
                                    }
                                } catch (InvalidProtocolBufferException x) {
                                    log.error("Impossible to parse stream type up with " + x.getMessage());
                                    log.info(HexaConverters.byteToHexStringWithSpace(byteData));
                                }
                            } else {
                                log.warn("## Found a new key on device:stream:event " + k);
                                byte[] byteData = message.getBody().get(k);
                                log.info(HexaConverters.byteToHexStringWithSpace(byteData));
                            }
                        }
                        // Confirm that the message has been processed using XACK
                        syncCommands.xack(
                                redisConfiguration.getStreamEventKey(),
                                redisConfiguration.getRedisCGroup(),
                                message.getId()
                        );
                    }
                }
            } catch (RedisCommandExecutionException x) {
                log.error("### Stream not existing, we should not be here ");
                prometeusService.addRedisStreamError();
            } finally {
                this.runningJobs--;
            }
        }
        log.debug("ListenOnRedisStreamEvent - duration "+(Now.NowUtcMs()-start)+" ms, process "+processed+" new entries");
        if ( (Now.NowUtcMs()-start) > 1000 ) {
            log.warn("ListenOnRedisStreamEvent - duration "+(Now.NowUtcMs()-start)+" ms, process "+processed+" new entries");
        }
    }

}
