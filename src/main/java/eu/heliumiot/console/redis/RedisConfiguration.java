package eu.heliumiot.console.redis;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RedisConfiguration {

    @Value("${spring.redis.metakey}")
    private String streamMetaKey;

    @Value("${spring.redis.eventkey:}")
    private String streamEventKey;

    @Value("${spring.redis.host}")
    private String redisHost;

    @Value("${spring.redis.port}")
    private int redisPort;

    @Value("${spring.redis.username}")
    private String redisUsername;

    @Value("${spring.redis.password}")
    private String redisPassword;

    @Value("${spring.redis.ssl:false}")
    private boolean redisSsl;

    @Value("${spring.redis.consumerGroup}")
    private String redisCGroup;

    @Value("${spring.redis.consumer}")
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