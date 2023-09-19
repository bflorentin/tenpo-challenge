package ar.com.florentin.tenpo.challenge.percentage.repository;

import ar.com.florentin.tenpo.challenge.percentage.entity.PercentageCache;
import reactor.core.publisher.Mono;

public interface PercentageCacheRepository {
    Mono<PercentageCache> save(PercentageCache percentageCache);
    Mono<PercentageCache> get(String key);
}
