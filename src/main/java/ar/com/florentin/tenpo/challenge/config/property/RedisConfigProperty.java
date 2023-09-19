package ar.com.florentin.tenpo.challenge.config.property;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Data
@Configuration
@ConfigurationProperties(prefix = "spring.data.redis")
public class RedisConfigProperty {
    private String host;
    private Integer port;
    private Long ttl = 10000L;
}
