package ar.com.florentin.tenpo.challenge.requestlog.amqp;

import ar.com.florentin.tenpo.challenge.config.property.RabbitProperty;
import ar.com.florentin.tenpo.challenge.requestlog.dto.RequestLogDto;
import ar.com.florentin.tenpo.challenge.requestlog.service.RequestLogService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.rabbitmq.client.Connection;
import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.utils.SerializationUtils;
import org.springframework.stereotype.Component;
import reactor.core.Disposable;
import reactor.core.publisher.Mono;
import reactor.rabbitmq.Receiver;

import java.io.IOException;

@Slf4j
@Component
public class RequestLogListener {
    private final Mono<Connection> connMono;
    private final Receiver receiver;
    private final RabbitProperty rabbitProperty;
    private final ObjectMapper objectMapper;
    private final RequestLogService requestLogService;

    public RequestLogListener(Mono<Connection> connMono, Receiver receiver, RabbitProperty rabbitProperty, ObjectMapper objectMapper, RequestLogService requestLogService) {
        this.connMono = connMono;
        this.receiver = receiver;
        this.rabbitProperty = rabbitProperty;
        this.objectMapper = objectMapper;
        this.requestLogService = requestLogService;
    }

    @PostConstruct
    private void init() {
        consume();
    }

    @PreDestroy
    public void close() throws IOException {
        connMono.block().close();
    }
    private Disposable consume() {
        return receiver.consumeAutoAck(rabbitProperty.getQueueName())
                .subscribe(m -> {
                    try {
                        log.info("Message received ({})", rabbitProperty.getQueueName());
                        Object json = SerializationUtils.deserialize(m.getBody());
                        RequestLogDto requestLogDto = objectMapper.readValue(json.toString(), RequestLogDto.class);
                        requestLogService.save(requestLogDto)
                                .doOnNext(r -> log.info("Request Log saved in database"))
                                .block();
                    } catch (Exception e) {
                        log.error("Error processing RequestLog", e);
                    }
                });
    }
}
