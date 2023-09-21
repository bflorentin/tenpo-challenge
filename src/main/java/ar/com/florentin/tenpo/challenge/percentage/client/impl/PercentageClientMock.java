package ar.com.florentin.tenpo.challenge.percentage.client.impl;

import ar.com.florentin.tenpo.challenge.percentage.client.PercentageClient;
import ar.com.florentin.tenpo.challenge.percentage.dto.PercentageResponseDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.math.BigDecimal;

@Slf4j
@Component
@ConditionalOnProperty(
        value="tenpo.percentage.mock.enable",
        havingValue = "true")
public class PercentageClientMock implements PercentageClient {
    @Override
    public Mono<PercentageResponseDto> getPercentage() {
        log.info("Get percentage from mock");
        return Mono.just(PercentageResponseDto.builder().value(BigDecimal.TEN).build());
    }
}
