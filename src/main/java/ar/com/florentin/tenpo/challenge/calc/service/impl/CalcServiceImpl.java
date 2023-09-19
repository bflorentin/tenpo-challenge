package ar.com.florentin.tenpo.challenge.calc.service.impl;

import ar.com.florentin.tenpo.challenge.calc.service.CalcService;
import ar.com.florentin.tenpo.challenge.percentage.service.PercentageService;
import ar.com.florentin.tenpo.challenge.util.MathOperations;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.math.BigDecimal;

@Slf4j
@Service
public class CalcServiceImpl implements CalcService {
    private final PercentageService percentageService;

    public CalcServiceImpl(PercentageService percentageService) {
        this.percentageService = percentageService;
    }

    @Override
    public Mono<BigDecimal> sum(BigDecimal a, BigDecimal b) {
        log.info("sum({}, {})", a, b);
        return Mono.just(MathOperations.sum(a, b))
                .flatMap(percentageService::addPercentage);
    }
}
