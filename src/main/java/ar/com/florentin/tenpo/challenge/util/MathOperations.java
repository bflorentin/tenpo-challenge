package ar.com.florentin.tenpo.challenge.util;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class MathOperations {
    private MathOperations() {}

    public static BigDecimal sum(final BigDecimal a, BigDecimal b) {
        return a.add(b);
    }

    public static BigDecimal addPercentage(final BigDecimal value, BigDecimal percentage) {
        return value.multiply(percentage).divide(BigDecimal.valueOf(100), RoundingMode.UP).add(value);
    }
}
