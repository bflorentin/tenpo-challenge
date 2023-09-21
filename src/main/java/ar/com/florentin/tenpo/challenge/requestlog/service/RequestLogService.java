package ar.com.florentin.tenpo.challenge.requestlog.service;

import ar.com.florentin.tenpo.challenge.requestlog.dto.RequestLogDto;
import org.springframework.data.domain.Pageable;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface RequestLogService {
    Mono<RequestLogDto> save(RequestLogDto requestLogDto);
    Flux<RequestLogDto> findAllBy(Pageable pageable);
    Mono<Long> count();
}
