package ar.com.florentin.tenpo.challenge.percentage.client.impl;

import ar.com.florentin.tenpo.challenge.config.property.PercentageClientProperty;
import ar.com.florentin.tenpo.challenge.exception.PercentageClientException;
import ar.com.florentin.tenpo.challenge.percentage.client.PercentageClient;
import ar.com.florentin.tenpo.challenge.percentage.dto.PercentageResponseDto;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import static ar.com.florentin.tenpo.challenge.util.Constants.X_REQUEST_ID;
@Slf4j
@Component
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
    public Mono<PercentageResponseDto> getPercentage() {
        log.info("Sending HTTP Request: GET {}", percentageClientProperty.getUri());
        return webClient.get()
                .header(X_REQUEST_ID, MDC.get(X_REQUEST_ID))
                .retrieve()
                .bodyToMono(PercentageResponseDto.class)
                .onErrorResume(e -> Mono.error(new PercentageClientException("Percentage service unavailable", e)));
    }
}
