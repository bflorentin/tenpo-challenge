package ar.com.florentin.tenpo.challenge.amqp.service;

import ar.com.florentin.tenpo.challenge.amqp.service.impl.RabbitServiceImpl;
import ar.com.florentin.tenpo.challenge.rabbit.component.ReactiveRabbitComponent;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import reactor.core.publisher.Mono;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
public class RabbitServiceTest {
    @InjectMocks
    RabbitServiceImpl rabbitService;
    @Mock
    ReactiveRabbitComponent reactiveRabbitComponent;

    @Test
    public void sendMessage() {
        when(reactiveRabbitComponent.sendMessage(any())).thenReturn(Mono.just(true));

        rabbitService.sendMessage("message").block();
        Mockito.verify(reactiveRabbitComponent, times(1)).sendMessage(any());
    }
}
