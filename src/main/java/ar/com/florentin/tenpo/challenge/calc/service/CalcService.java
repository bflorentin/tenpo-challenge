package ar.com.florentin.tenpo.challenge.calc.service;

import reactor.core.publisher.Mono;

import java.math.BigDecimal;

public interface CalcService {
    Mono<BigDecimal> sum(BigDecimal a, BigDecimal b);
}
