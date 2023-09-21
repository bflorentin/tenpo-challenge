package ar.com.florentin.tenpo.challenge.calc.controller;

import ar.com.florentin.tenpo.challenge.calc.dto.OperationRequest;
import ar.com.florentin.tenpo.challenge.calc.service.CalcService;
import io.github.resilience4j.ratelimiter.annotation.RateLimiter;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

import java.math.BigDecimal;
@RestController
@RequestMapping("/api/v1/calc")
public class CalcController {
    private final CalcService calcService;

    public CalcController(CalcService calcService) {
        this.calcService = calcService;
    }

    @RateLimiter(name = "calc")
    @PostMapping("/sum")
    public Mono<BigDecimal> sum(@RequestBody @Valid OperationRequest request){
        return calcService.sum(request.getA(), request.getB());
    }
}
