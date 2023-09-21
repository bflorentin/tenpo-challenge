package ar.com.florentin.tenpo.challenge.requestlog.entity;

import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.time.LocalDateTime;

@Data
@Builder
@Table("REQUEST_LOG")
public class RequestLog {
    @Id
    @Column("ID")
    private Long id;
    @Column("PATH")
    private String path;
    @Column("HTTP_STATUS")
    private String httpStatus;
    @Column("DATE_TIME")
    private LocalDateTime dateTime;
}
