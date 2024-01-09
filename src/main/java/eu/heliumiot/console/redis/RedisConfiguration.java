package eu.heliumiot.console.redis;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RedisConfiguration {

    @Value("${spring.data.redis.metakey}")
    private String streamMetaKey;

    @Value("${spring.data.redis.eventkey:}")
    private String streamEventKey;

    @Value("${spring.data.redis.host}")
    private String redisHost;

    @Value("${spring.data.redis.port}")
    private int redisPort;

    @Value("${spring.data.redis.username}")
    private String redisUsername;

    @Value("${spring.data.redis.password}")
    private String redisPassword;

    @Value("${spring.data.redis.ssl.enabled:false}")
    private boolean redisSsl;

    @Value("${spring.data.redis.consumerGroup}")
    private String redisCGroup;

    @Value("${spring.data.redis.consumer}")
    private String redisConsumer;


    // ---


    public String getStreamMetaKey() {
        return streamMetaKey;
    }

    public String getRedisHost() {
        return redisHost;
    }

    public int getRedisPort() {
        return redisPort;
    }

    public String getRedisUsername() {
        return redisUsername;
    }

    public String getRedisPassword() {
        return redisPassword;
    }

    public boolean isRedisSsl() {
        return redisSsl;
    }

    public String getRedisCGroup() {
        return redisCGroup;
    }

    public String getRedisConsumer() {
        return redisConsumer;
    }

    public String getStreamEventKey() {
        return streamEventKey;
    }
}