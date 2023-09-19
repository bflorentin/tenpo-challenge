package ar.com.florentin.tenpo.challenge.calc.dto;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class OperationRequest {
    private BigDecimal a;
    private BigDecimal b;
}
