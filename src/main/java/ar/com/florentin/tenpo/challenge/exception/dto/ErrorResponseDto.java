package ar.com.florentin.tenpo.challenge.exception.dto;

import lombok.Builder;
import lombok.Data;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
public class ErrorResponseDto {
    private LocalDateTime timestamp;
    private String path;
    private HttpStatus httpStatus;
    private String errorMessage;
    private List<String> errors;
}
