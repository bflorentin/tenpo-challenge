package ar.com.florentin.tenpo.challenge.exception.handler;

import ar.com.florentin.tenpo.challenge.exception.PercentageClientException;
import ar.com.florentin.tenpo.challenge.exception.dto.ErrorResponseDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.support.WebExchangeBindException;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@ControllerAdvice
public class RestExceptionHandler {
    @ExceptionHandler(WebExchangeBindException.class)
    public ResponseEntity<ErrorResponseDto> handleException(WebExchangeBindException e, ServerHttpRequest serverHttpRequest) {
        log.error("BadRequest: {}",e.getMessage(), e);

        final List<String> errors = e.getBindingResult()
                .getAllErrors()
                .stream()
                .map(DefaultMessageSourceResolvable::getDefaultMessage)
                .collect(Collectors.toList());

        final ErrorResponseDto errorResponseDto = ErrorResponseDto.builder()
                .path(serverHttpRequest.getPath().value())
                .errorMessage(e.getReason())
                .errors(errors)
                .timestamp(LocalDateTime.now())
                .httpStatus(HttpStatus.BAD_REQUEST)
                .build();

        return ResponseEntity.badRequest().body(errorResponseDto);
    }

    @ExceptionHandler(PercentageClientException.class)
    public ResponseEntity<ErrorResponseDto> handleException(PercentageClientException e, ServerHttpRequest serverHttpRequest) {
        log.error("PercentageClientError: {}",e.getMessage(), e);

        final ErrorResponseDto errorResponseDto = ErrorResponseDto.builder()
                .path(serverHttpRequest.getPath().value())
                .errorMessage(e.getMessage())
                .timestamp(LocalDateTime.now())
                .httpStatus(HttpStatus.INTERNAL_SERVER_ERROR)
                .build();
        return ResponseEntity.internalServerError().body(errorResponseDto);
    }
}
