package ar.com.florentin.tenpo.challenge.filter;

import org.slf4j.MDC;
import org.springframework.core.annotation.Order;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Mono;

import java.util.UUID;

import static ar.com.florentin.tenpo.challenge.util.Constants.X_REQUEST_ID;
import static org.springframework.core.Ordered.HIGHEST_PRECEDENCE;

@Component
@Order(HIGHEST_PRECEDENCE)
public class XRequestIdFilter implements WebFilter {
    @Override
    public Mono<Void> filter(ServerWebExchange serverWebExchange, WebFilterChain webFilterChain) {
        final String xRequestId = getXRequestId(serverWebExchange.getRequest());
        MDC.put(X_REQUEST_ID, xRequestId);

        addHeaderToResponse(serverWebExchange.getResponse(), xRequestId);
        return webFilterChain.filter(serverWebExchange);
    }

    private String getXRequestId(ServerHttpRequest request ) {
        final String requestIdInHeader = request.getHeaders().getFirst(X_REQUEST_ID);
        if (ObjectUtils.isEmpty(requestIdInHeader)) {
            return getRandomXRequestId();
        }

        final String trimmedRequestId = requestIdInHeader.trim();
        if (trimmedRequestId.isEmpty()) {
            return getRandomXRequestId();
        }

        return trimmedRequestId;
    }

    private String getRandomXRequestId() {
        return UUID.randomUUID().toString().replace("-", "");
    }

    private void addHeaderToResponse(ServerHttpResponse response, String xRequestId) {
        final String requestIdInHeader = response.getHeaders().getFirst(X_REQUEST_ID);
        if (ObjectUtils.isEmpty(requestIdInHeader)) {
            response.getHeaders().add(X_REQUEST_ID, xRequestId);
        }
    }
}
