package ar.com.florentin.tenpo.challenge.percentage.dto;

import lombok.Builder;
import lombok.Getter;

import java.math.BigDecimal;

@Builder
@Getter
public class PercentageResponseDto {
    private BigDecimal value;
}
