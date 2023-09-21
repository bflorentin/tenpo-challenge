package ar.com.florentin.tenpo.challenge.calc.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Builder
public class OperationRequest {
    @NotNull(message = "A can not be null")
    private BigDecimal a;
    @NotNull(message = "B can not be null")
    private BigDecimal b;
}
