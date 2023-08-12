package eu.heliumiot.console.redis;

import com.google.protobuf.InvalidProtocolBufferException;
import fr.ingeniousthings.tools.HexaConverters;
import io.chirpstack.api.internal.Internal;
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
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Service
public class RedisDeviceRepository {

    @Autowired
    private RedisConfiguration redisConfiguration;
    private final Logger log = LoggerFactory.getLogger(this.getClass());

    private RedisCommands<String, byte[]> syncCommands;

    private StatefulRedisConnection<String, byte[]> redisConnection;
    private RedisClient redisClient;

    @PostConstruct
    public void setupRedisDeviceRepository() {
        log.info("Init setupRedisDeviceRepository");
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
    }

    // ================================================
    // Redis query
    // ================================================

    public List<String> getDevEuiByDevAddr(String devaddr) {
        devaddr = devaddr.toLowerCase();

        Set<byte[]> devIds =  syncCommands.smembers("devaddr:{"+devaddr+"}");
        ArrayList<String> devEuis = new ArrayList<>();
        for ( byte[] devId : devIds ) {
            devEuis.add(HexaConverters.byteToHexString(devId));
            log.debug("REDIS DEVADDR : Found "+HexaConverters.byteToHexString(devId));
        }
        return devEuis;
    }

    public Internal.DeviceSession getDeviceDetails(String devEUI) {
        devEUI = devEUI.toLowerCase();
        byte[] deviceInfo = syncCommands.get("device:{"+devEUI+"}:ds");
        if ( deviceInfo == null ) return null;

        try {
            Internal.DeviceSession devSession = Internal.DeviceSession.parseFrom(deviceInfo);
            log.debug("devEUI :"+HexaConverters.byteToHexString(devSession.getDevEui().toByteArray()));
            log.debug("devADDR :"+HexaConverters.byteToHexString(devSession.getDevAddr().toByteArray()));
            log.debug("NtwkSkey : "+HexaConverters.byteToHexString(devSession.getNwkSEncKey().toByteArray()));
            return devSession;
        } catch (InvalidProtocolBufferException x) {
            log.error("Impossible to parse deviceSession");
        }

        return null;
    }


    /**
     * Get from redis the list of devAddr enabled in chirpstack
     * @return
     */
    public List<String> getAllDevAddr() {
        List<String> ret = syncCommands.keys("devaddr:*");
        // format devaddr:{....}
        log.debug("Found "+ret.size()+" devAddr in use");
        ArrayList<String> devAddr = new ArrayList<>();
        for ( String r : ret ) {
            devAddr.add(r.substring(9,17));
        }
        return devAddr;
    }

}
