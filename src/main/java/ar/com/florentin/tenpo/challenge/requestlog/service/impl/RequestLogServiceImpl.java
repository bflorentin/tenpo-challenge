package ar.com.florentin.tenpo.challenge.requestlog.service.impl;

import ar.com.florentin.tenpo.challenge.requestlog.dto.RequestLogDto;
import ar.com.florentin.tenpo.challenge.requestlog.entity.RequestLog;
import ar.com.florentin.tenpo.challenge.requestlog.repository.RequestLogRepository;
import ar.com.florentin.tenpo.challenge.requestlog.service.RequestLogService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
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
                .httpStatus(requestLogDto.getHttpStatus())
                .dateTime(requestLogDto.getDateTime())
                .build();

        return repository.save(entity).map(requestLog -> requestLogDto);
    }

    @Override
    public Flux<RequestLogDto> findAllBy(Pageable pageable) {
        return repository.findAllBy(pageable)
                .map(requestLog -> RequestLogDto.builder()
                        .id(requestLog.getId())
                        .path(requestLog.getPath())
                        .httpStatus(requestLog.getHttpStatus())
                        .dateTime(requestLog.getDateTime())
                        .build());
    }

    @Override
    public Mono<Long> count() {
        return repository.count();
    }
}
