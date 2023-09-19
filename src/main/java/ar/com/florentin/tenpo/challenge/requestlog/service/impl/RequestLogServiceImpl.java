package ar.com.florentin.tenpo.challenge.requestlog.service.impl;

import ar.com.florentin.tenpo.challenge.requestlog.dto.RequestLogDto;
import ar.com.florentin.tenpo.challenge.requestlog.entity.RequestLog;
import ar.com.florentin.tenpo.challenge.requestlog.repository.RequestLogRepository;
import ar.com.florentin.tenpo.challenge.requestlog.service.RequestLogService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Slf4j
@Service
public class RequestLogServiceImpl implements RequestLogService {
    private final RequestLogRepository repository;

    public RequestLogServiceImpl(RequestLogRepository repository) {
        this.repository = repository;
    }

    @Override
    public Mono<RequestLogDto> save(RequestLogDto requestLogDto) {
        final RequestLog entity = RequestLog.builder()
                .path(requestLogDto.getPath())
                .jsonResponse(requestLogDto.getJsonResponse())
                .dateTime(requestLogDto.getDateTime())
                .build();

        return repository.save(entity).map(requestLog -> requestLogDto);
    }
}
