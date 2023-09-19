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

@Slf4j
@Component
public class RequestLogFilter implements WebFilter {
    private final AmqpService amqpService;

    public RequestLogFilter(AmqpService amqpService) {
        this.amqpService = amqpService;
    }

    @Override
    public Mono<Void> filter(ServerWebExchange serverWebExchange, WebFilterChain webFilterChain) {
        final String path = serverWebExchange.getRequest().getPath().value();
        final RequestLogDto requestLogDto = RequestLogDto.builder().path(path)
                .jsonResponse("{json}")
                .dateTime(LocalDateTime.now())
                .build();

        return amqpService.sendMessage(requestLogDto)
                .flatMap(aBoolean -> webFilterChain.filter(serverWebExchange));
    }
}
