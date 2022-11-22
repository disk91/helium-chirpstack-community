package eu.heliumiot.console.redis;

import com.google.protobuf.InvalidProtocolBufferException;
import fr.ingeniousthings.tools.HexaConverters;
import fr.ingeniousthings.tools.Now;
import io.chirpstack.api.meta.UplinkMeta;
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

@Service
public class RedisStreamMetaListener {

    @Autowired
    private RedisConfiguration redisConfiguration;
    private final Logger log = LoggerFactory.getLogger(this.getClass());

    private RedisCommands<String, byte[]> syncCommands;

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
        RedisClient redisClient = RedisClient.create(connectionString);
        RedisCodec<String, byte[]> codec = RedisCodec.of(new StringCodec(), new ByteArrayCodec());
        StatefulRedisConnection<String, byte[]> connection = redisClient.connect(codec);
        syncCommands = connection.sync();

        try {
            syncCommands.xgroupCreate(
                    XReadArgs.StreamOffset.from(
                            redisConfiguration.getStreamMetaKey(),
                            "0-0"),
                    redisConfiguration.getRedisCGroup()
            );
        } catch (RedisBusyException redisBusyException) {
            // consumer group exist ... basically a normal situation
        }

        this.runningJobs = 0;
        this.serviceEnable = true;
    }

    // request to stop the service properly
    public void stopService() {
        this.serviceEnable = false;
    }

    // return true when the service has stopped all the running jobs
    public boolean hasStopped() {
        return (this.serviceEnable == false && this.runningJobs == 0);
    }


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
                    //log.info(message.getStream());
                    //log.info(message.getId());
                    Map<String, byte[]> m = message.getBody();
                    for (String k : m.keySet()) {
                        processed++;
                        if (k.compareToIgnoreCase("up") == 0) {
                            byte[] byteData = message.getBody().get(k);
                            try {
                                UplinkMeta up = UplinkMeta.parseFrom(byteData);
                                if (up != null) {
                                    log.info("dev_eui: " + up.getDevEui() + " freq : " + up.getTxInfo().getFrequency());
                                }
                            } catch (InvalidProtocolBufferException x) {
                                log.error("Impossible to parse stream:meta type up with " + x.getMessage());
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

        /*
        connection.close();
redisClient.shutdown();
         */
}
