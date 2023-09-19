package ar.com.florentin.tenpo.challenge.requestlog.amqp;

import com.rabbitmq.client.Connection;
import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import org.springframework.amqp.utils.SerializationUtils;
import org.springframework.stereotype.Component;
import reactor.core.Disposable;
import reactor.core.publisher.Mono;
import reactor.rabbitmq.Receiver;

import java.io.IOException;

@Component
public class RequestLogListener {

    private final Mono<Connection> connMono;
    private final Receiver receiver;

    public RequestLogListener(Mono<Connection> connMono, Receiver receiver) {
        this.connMono = connMono;
        this.receiver = receiver;
    }

    // Listen to RabbitMQ as soon as this service is up
    @PostConstruct
    private void init() {
        consume();
    }

    // Make sure to close the connection before the program is finished
    @PreDestroy
    public void close() throws IOException {
        connMono.block().close();
    }

    // Consume message from the sender queue

    private Disposable consume() {
        return receiver.consumeAutoAck("requestLog")
                .subscribe(m -> {
                    Object json = SerializationUtils
                            .deserialize(m.getBody());

                    try {
                        System.out.println("message received " + json.toString());

                    } catch (Exception e) {

                        e.printStackTrace();
                    }
                });

    }
}
