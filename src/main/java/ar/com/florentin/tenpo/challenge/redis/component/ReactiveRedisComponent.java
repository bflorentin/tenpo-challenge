package ar.com.florentin.tenpo.challenge.redis.component;

import ar.com.florentin.tenpo.challenge.config.property.RedisConfigProperty;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.ReactiveRedisOperations;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.time.Duration;
import java.util.concurrent.TimeUnit;

@Slf4j
@Component
public class ReactiveRedisComponent {

    private final ReactiveRedisOperations<String, Object> redisOperations;
    private final RedisConfigProperty redisConfigProperty;

    public ReactiveRedisComponent(ReactiveRedisOperations<String, Object> redisOperations, RedisConfigProperty redisConfigProperty) {
        this.redisOperations = redisOperations;
        this.redisConfigProperty = redisConfigProperty;
    }

    public Mono<Object> set(String key, Object value) {
        return redisOperations.opsForValue().set(key, value, Duration.ofMinutes(redisConfigProperty.getTtl()))
                .map(b -> value);
    }

    public Mono<Object> get(String key) {
        return redisOperations.opsForValue().get(key);
    }
}
