package ar.com.florentin.tenpo.challenge.calc.service;

import ar.com.florentin.tenpo.challenge.calc.service.impl.CalcServiceImpl;
import ar.com.florentin.tenpo.challenge.percentage.service.PercentageService;
import ar.com.florentin.tenpo.challenge.util.MathOperations;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import reactor.core.publisher.Mono;

import java.math.BigDecimal;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
public class CalcServiceTest {
    @InjectMocks
    CalcServiceImpl calcService;
    @Mock
    PercentageService percentageService;

    @Test
    public void sum() {
        BigDecimal a = BigDecimal.valueOf(5);
        BigDecimal b = BigDecimal.valueOf(5);
        when(percentageService.addPercentage(any())).thenReturn(Mono.just(BigDecimal.ONE));

        calcService.sum(a,b).block();
        BigDecimal sum = MathOperations.sum(a,b);
        Mockito.verify(percentageService, times(1)).addPercentage(eq(sum));
    }
}
