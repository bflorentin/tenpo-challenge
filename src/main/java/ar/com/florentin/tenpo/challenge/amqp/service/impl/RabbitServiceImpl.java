package ar.com.florentin.tenpo.challenge.amqp.service.impl;

import ar.com.florentin.tenpo.challenge.amqp.service.AmqpService;
import ar.com.florentin.tenpo.challenge.rabbit.component.ReactiveRabbitComponent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Slf4j
@Service
public class RabbitServiceImpl implements AmqpService {

    private final ReactiveRabbitComponent reactiveRabbitComponent;

    public RabbitServiceImpl(ReactiveRabbitComponent reactiveRabbitComponent) {
        this.reactiveRabbitComponent = reactiveRabbitComponent;
    }

    @Override
    public Mono<Boolean> sendMessage(Object message) {
        return reactiveRabbitComponent.sendMessage(message);
    }
}
