package ar.com.florentin.tenpo.challenge.percentage.service;

import reactor.core.publisher.Mono;

import java.math.BigDecimal;

public interface PercentageService {
    Mono<BigDecimal> addPercentage(BigDecimal value);
}
