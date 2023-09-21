package ar.com.florentin.tenpo.challenge.percentage.service.impl;

import ar.com.florentin.tenpo.challenge.exception.PercentageClientException;
import ar.com.florentin.tenpo.challenge.percentage.client.PercentageClient;
import ar.com.florentin.tenpo.challenge.percentage.dto.PercentageResponseDto;
import ar.com.florentin.tenpo.challenge.percentage.service.PercentageCacheService;
import ar.com.florentin.tenpo.challenge.percentage.service.PercentageService;
import ar.com.florentin.tenpo.challenge.util.MathOperations;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.math.BigDecimal;

@Slf4j
@Service
public class PercentageServiceImpl implements PercentageService {

    private final PercentageClient percentageClient;
    private final PercentageCacheService percentageCacheService;

    public PercentageServiceImpl(PercentageClient percentageClient, PercentageCacheService percentageCacheService) {
        this.percentageClient = percentageClient;
        this.percentageCacheService = percentageCacheService;
    }

    @Override
    public Mono<BigDecimal> addPercentage(BigDecimal value) {
        return percentageCacheService.get()
                .doOnNext(p -> log.info("Percentage found in cache"))
                .switchIfEmpty(Mono.fromCompletionStage(() -> this.getPercentageFromServiceAndCache().toFuture()))
                .map(percentageResponse -> MathOperations.addPercentage(value, percentageResponse));
    }

    private Mono<BigDecimal> getPercentageFromServiceAndCache() {
        log.info("Obtaining percentage from external service");
        return percentageClient.getPercentage()
                .doOnNext(dto -> log.info("Percentage obtained - {}%", dto.getValue()))
                .flatMap(dto -> this.percentageCacheService.save(dto.getValue())
                        .doOnNext(p -> log.info("Cached percentage")));
    }
}
