package ar.com.florentin.tenpo.challenge.calc.controller;

import ar.com.florentin.tenpo.challenge.amqp.service.AmqpService;
import ar.com.florentin.tenpo.challenge.calc.dto.OperationRequest;
import ar.com.florentin.tenpo.challenge.calc.service.CalcService;
import ar.com.florentin.tenpo.challenge.exception.PercentageClientException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.math.BigDecimal;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
@WebFluxTest(CalcController.class)
public class CalcControllerTest {
    @Autowired
    WebTestClient webClient;

    @MockBean
    CalcService calcService;

    @MockBean
    AmqpService amqpService;

    @MockBean
    ServerWebExchange serverWebExchange;
    private final String URI = "/api/v1/calc/sum";

    @BeforeEach
    public void setup() {
        when(amqpService.sendMessage(any())).thenReturn(Mono.just(true));
    }

    @Test
    public void whenValidRequest_thenReturns200() {
        OperationRequest operationRequest = OperationRequest.builder()
                .a(BigDecimal.valueOf(5))
                .b(BigDecimal.valueOf(5))
                .build();

        webClient.post()
                .uri(URI)
                .contentType(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromValue(operationRequest))
                .exchange()
                .expectStatus().isOk();

        Mockito.verify(calcService, times(1)).sum(eq(operationRequest.getA()),eq(operationRequest.getB()));
    }

    @Test
    public void whenAIsNull_thenReturns400() {
        OperationRequest operationRequest = OperationRequest.builder()
                .b(BigDecimal.valueOf(5))
                .build();

        badRequest(operationRequest, "A can not be null");
    }

    @Test
    public void whenBIsNull_thenReturns400() {
        OperationRequest operationRequest = OperationRequest.builder()
                .a(BigDecimal.valueOf(5))
                .build();

        badRequest(operationRequest, "B can not be null");
    }

    @Test
    public void whenValidRequest_externalServiceUnavailable_thenReturns500() {
        final OperationRequest operationRequest = OperationRequest.builder()
                .a(BigDecimal.valueOf(5))
                .b(BigDecimal.valueOf(5))
                .build();
        final String errorMessage = "Percentage service unavailable";

        when(calcService.sum(any(), any())).thenThrow(new PercentageClientException(errorMessage));

        webClient.post()
                .uri(URI)
                .contentType(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromValue(operationRequest))
                .exchange()
                .expectStatus().is5xxServerError()
                .expectBody()
                .jsonPath("$.errorMessage").isEqualTo(errorMessage);
    }

    private void badRequest(OperationRequest operationRequest, String expectedError) {
        webClient.post()
                .uri(URI)
                .contentType(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromValue(operationRequest))
                .exchange()
                .expectStatus().is4xxClientError()
                .expectBody()
                .jsonPath("$.errors[0]").isEqualTo(expectedError);
    }
}
