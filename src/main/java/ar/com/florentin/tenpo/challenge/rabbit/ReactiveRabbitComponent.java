package ar.com.florentin.tenpo.challenge.rabbit;

import ar.com.florentin.tenpo.challenge.config.property.RabbitProperty;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.utils.SerializationUtils;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.rabbitmq.OutboundMessage;
import reactor.rabbitmq.QueueSpecification;
import reactor.rabbitmq.Sender;

@Slf4j
@Component
public class ReactiveRabbitComponent {
    private final Sender sender;
    private final ObjectMapper objectMapper;
    private final RabbitProperty rabbitProperty;

    public ReactiveRabbitComponent(Sender sender, ObjectMapper objectMapper, RabbitProperty rabbitProperty) {
        this.sender = sender;
        this.objectMapper = objectMapper;
        this.rabbitProperty = rabbitProperty;
    }

    public Mono<Boolean> sendMessage(Object message) {
        String json;
        try {

            // Serialize object to json
            json = objectMapper.writeValueAsString(message);

            // Serialize json to bytes
            byte[] orderSerialized = SerializationUtils.serialize(json);

            // Outbound Message that will be sent by the Sender
            Flux<OutboundMessage> outbound = Flux.just(new OutboundMessage("", rabbitProperty.getQueueName(), orderSerialized));

            // Declare the queue then send the flux of messages
            sender.declareQueue(QueueSpecification.queue(rabbitProperty.getQueueName()))
                    .thenMany(sender.sendWithPublishConfirms(outbound))
                    .doOnError(e -> log.error("Send failed", e))
                    .subscribe(m -> {
                        log.info("Message has been sent...");
                    });

        } catch (Exception e) {
            e.printStackTrace();
        }


        // Return posted object to user (Client)
        return Mono.just(true);

    }
}
