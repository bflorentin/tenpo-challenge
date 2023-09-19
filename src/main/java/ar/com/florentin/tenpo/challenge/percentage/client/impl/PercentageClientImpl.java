package ar.com.florentin.tenpo.challenge.percentage.client.impl;

import ar.com.florentin.tenpo.challenge.percentage.client.PercentageClient;
import ar.com.florentin.tenpo.challenge.percentage.dto.PercentageResponseDto;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.math.BigDecimal;

@Component
public class PercentageClientImpl implements PercentageClient {
    @Override
    public Mono<PercentageResponseDto> getPercentage() {
        return Mono.just(PercentageResponseDto.builder().value(BigDecimal.TEN).build());
    }
}
