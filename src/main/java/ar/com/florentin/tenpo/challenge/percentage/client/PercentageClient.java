package ar.com.florentin.tenpo.challenge.percentage.client;

import ar.com.florentin.tenpo.challenge.percentage.dto.PercentageResponseDto;
import reactor.core.publisher.Mono;

public interface PercentageClient {
    Mono<PercentageResponseDto> getPercentage();
}
