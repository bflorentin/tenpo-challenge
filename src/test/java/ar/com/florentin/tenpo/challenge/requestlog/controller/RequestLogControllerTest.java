package ar.com.florentin.tenpo.challenge.requestlog.controller;

import ar.com.florentin.tenpo.challenge.amqp.service.AmqpService;
import ar.com.florentin.tenpo.challenge.requestlog.dto.RequestLogDto;
import ar.com.florentin.tenpo.challenge.requestlog.service.RequestLogService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
@WebFluxTest(RequestLogController.class)
public class RequestLogControllerTest {
    @Autowired
    WebTestClient webClient;

    @MockBean
    RequestLogService requestLogService;

    @MockBean
    AmqpService amqpService;

    private final String URI = "/api/v1/requestlog/history";

    @BeforeEach
    public void setup() {
        when(amqpService.sendMessage(any())).thenReturn(Mono.just(true));
    }

    @Test
    public void whenValidRequest_thenReturns200() {
        when(requestLogService.findAllBy(any())).thenReturn(Flux.just(RequestLogDto.builder().build()));
        when(requestLogService.count()).thenReturn(Mono.just(0L));

        webClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path("/api/v1/requestlog/history")
                        .queryParam("page", 0)
                        .queryParam("size", 10)
                        .build())
                .exchange()
                .expectStatus().isOk();

        Mockito.verify(requestLogService, times(1)).findAllBy(any());
    }
}
