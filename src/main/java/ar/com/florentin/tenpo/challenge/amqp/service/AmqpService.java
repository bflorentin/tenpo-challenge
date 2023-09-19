package ar.com.florentin.tenpo.challenge.amqp.service;

import reactor.core.publisher.Mono;

public interface AmqpService {
    Mono<Boolean> sendMessage(Object message);
}
