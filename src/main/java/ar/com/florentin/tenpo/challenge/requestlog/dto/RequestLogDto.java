package ar.com.florentin.tenpo.challenge.requestlog.dto;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class RequestLogDto {
    private Long id;
    private String path;
    private String jsonResponse;
    private LocalDateTime dateTime;
}
