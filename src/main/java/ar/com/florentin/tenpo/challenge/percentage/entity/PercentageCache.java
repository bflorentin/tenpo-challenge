package ar.com.florentin.tenpo.challenge.percentage.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PercentageCache {
    private BigDecimal lastValue;
}
