package ar.com.florentin.tenpo.challenge.percentage.repository.impl;

import ar.com.florentin.tenpo.challenge.redis.ReactiveRedisComponent;
import ar.com.florentin.tenpo.challenge.percentage.entity.PercentageCache;
import ar.com.florentin.tenpo.challenge.percentage.repository.PercentageCacheRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

import static ar.com.florentin.tenpo.challenge.util.Constants.PERCENTAGE_CACHE_KEY;

@Repository
public class PercentageCacheRepositoryImpl implements PercentageCacheRepository {
    private final ReactiveRedisComponent reactiveRedisComponent;
    private final ObjectMapper objectMapper;

    public PercentageCacheRepositoryImpl(ReactiveRedisComponent reactiveRedisComponent, ObjectMapper objectMapper) {
        this.reactiveRedisComponent = reactiveRedisComponent;
        this.objectMapper = objectMapper;
    }

    @Override
    public Mono<PercentageCache> save(PercentageCache percentageCache) {
        return reactiveRedisComponent.set(PERCENTAGE_CACHE_KEY,  percentageCache).map(p -> percentageCache);
    }

    @Override
    public Mono<PercentageCache> get(String key) {
        return reactiveRedisComponent.get(PERCENTAGE_CACHE_KEY).flatMap(p -> Mono.just(objectMapper.convertValue(p, PercentageCache.class)));
    }
}
