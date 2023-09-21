package ar.com.florentin.tenpo.challenge.filter;

import ar.com.florentin.tenpo.challenge.amqp.service.AmqpService;
import ar.com.florentin.tenpo.challenge.requestlog.dto.RequestLogDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;
import java.util.Objects;

@Slf4j
@Component
public class RequestLogFilter implements WebFilter {
    private final AmqpService amqpService;

    public RequestLogFilter(AmqpService amqpService) {
        this.amqpService = amqpService;
    }

    @Override
    public Mono<Void> filter(ServerWebExchange serverWebExchange, WebFilterChain webFilterChain) {
        final String path = serverWebExchange.getRequest().getURI().toString();

        log.info("Incoming Request: {} {}", serverWebExchange.getRequest().getMethod().name(), serverWebExchange.getRequest().getURI());

        return webFilterChain.filter(serverWebExchange)
                .doOnSuccess(unused -> {
                    try {
                        final RequestLogDto requestLogDto = RequestLogDto.builder()
                                .path(path)
                                .httpStatus(Objects.requireNonNull(serverWebExchange.getResponse().getStatusCode()).toString())
                                .dateTime(LocalDateTime.now())
                                .build();

                        log.info("Record request: {}", requestLogDto);

                        amqpService.sendMessage(requestLogDto).then();
                    } catch (Exception e) {
                        log.error("error queuing requestLog", e);
                    }
                });
    }
}
