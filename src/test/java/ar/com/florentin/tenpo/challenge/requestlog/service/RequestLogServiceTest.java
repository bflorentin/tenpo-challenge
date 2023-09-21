package ar.com.florentin.tenpo.challenge.requestlog.service;

import ar.com.florentin.tenpo.challenge.requestlog.dto.RequestLogDto;
import ar.com.florentin.tenpo.challenge.requestlog.entity.RequestLog;
import ar.com.florentin.tenpo.challenge.requestlog.repository.RequestLogRepository;
import ar.com.florentin.tenpo.challenge.requestlog.service.impl.RequestLogServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
public class RequestLogServiceTest {
    @InjectMocks
    RequestLogServiceImpl requestLogService;
    @Mock
    RequestLogRepository requestLogRepository;

    @Test
    public void save() {
        RequestLogDto requestLogDto = RequestLogDto.builder()
                .path("/test")
                .httpStatus("{}")
                .dateTime(LocalDateTime.now())
                .build();
        when(requestLogRepository.save(any())).thenReturn(Mono.just(RequestLog.builder().build()));

        requestLogService.save(requestLogDto).block();

        Mockito.verify(requestLogRepository, times(1)).save(any());
    }

    @Test
    public void findAll() {
        when(requestLogRepository.findAllBy(any())).thenReturn(Flux.just(RequestLog.builder().build()));

        requestLogService.findAllBy(PageRequest.of(0, 10)).collectList().block();

        Mockito.verify(requestLogRepository, times(1)).findAllBy(any());
    }
}
