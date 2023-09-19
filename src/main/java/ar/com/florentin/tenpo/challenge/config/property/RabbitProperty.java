package ar.com.florentin.tenpo.challenge.config.property;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Data
@Configuration
@ConfigurationProperties(
        prefix = "spring.rabbitmq"
)
public class RabbitProperty {
    private String queueName;
}
