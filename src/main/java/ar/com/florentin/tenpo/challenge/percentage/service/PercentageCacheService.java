package ar.com.florentin.tenpo.challenge.percentage.service;

import ar.com.florentin.tenpo.challenge.percentage.entity.PercentageCache;
import reactor.core.publisher.Mono;

import java.math.BigDecimal;

public interface PercentageCacheService {
    Mono<BigDecimal> get();
    Mono<BigDecimal> save(BigDecimal value);
}
