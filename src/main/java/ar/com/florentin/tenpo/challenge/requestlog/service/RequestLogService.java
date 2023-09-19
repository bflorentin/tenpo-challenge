package ar.com.florentin.tenpo.challenge.requestlog.service;

import ar.com.florentin.tenpo.challenge.requestlog.dto.RequestLogDto;
import reactor.core.publisher.Mono;

public interface RequestLogService {
    Mono<RequestLogDto> save(RequestLogDto requestLogDto);
}
