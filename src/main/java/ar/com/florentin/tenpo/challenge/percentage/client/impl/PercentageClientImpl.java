package ar.com.florentin.tenpo.challenge.percentage.client.impl;

import ar.com.florentin.tenpo.challenge.config.property.PercentageClientProperty;
import ar.com.florentin.tenpo.challenge.exception.PercentageClientException;
import ar.com.florentin.tenpo.challenge.percentage.client.PercentageClient;
import ar.com.florentin.tenpo.challenge.percentage.dto.PercentageResponseDto;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import static ar.com.florentin.tenpo.challenge.util.Constants.X_REQUEST_ID;
@Slf4j
@Component
@ConditionalOnProperty(
        value="tenpo.percentage.mock.enable",
        havingValue = "false")
public class PercentageClientImpl implements PercentageClient {
    private final WebClient webClient;
    private final PercentageClientProperty percentageClientProperty;

    public PercentageClientImpl(PercentageClientProperty percentageClientProperty) {
        this.webClient = WebClient.builder()
                .baseUrl(percentageClientProperty.getUri())
                .build();
        this.percentageClientProperty = percentageClientProperty;
    }

    @Override
    @CircuitBreaker(name = "percentageService", fallbackMethod = "fallback")
    @Retry(name = "percentageServiceRetry")
    public Mono<PercentageResponseDto> getPercentage() {
        log.info("Sending HTTP Request: GET {}", percentageClientProperty.getUri());
        return webClient.get()
                .header(X_REQUEST_ID, MDC.get(X_REQUEST_ID))
                .retrieve()
                .bodyToMono(PercentageResponseDto.class)
                .doOnError(e -> log.error(e.getMessage()));
    }

    public Mono<PercentageResponseDto> fallback(Throwable e) {
        return Mono.error(new PercentageClientException("Percentage service unavailable", e));
    }
}
