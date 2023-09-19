package ar.com.florentin.tenpo.challenge.calc.controller;

import ar.com.florentin.tenpo.challenge.calc.dto.OperationRequest;
import ar.com.florentin.tenpo.challenge.calc.service.CalcService;
import ar.com.florentin.tenpo.challenge.rabbit.ReactiveRabbitComponent;
import ar.com.florentin.tenpo.challenge.requestlog.dto.RequestLogDto;
import ar.com.florentin.tenpo.challenge.requestlog.service.RequestLogService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@RestController
@RequestMapping("/api/v1/calc")
@Slf4j
public class CalcController {
    private final CalcService calcService;

    @Autowired
    private ReactiveRabbitComponent reactiveRabbitComponent;

    @Autowired
    private RequestLogService requestLogService;

    public CalcController(CalcService calcService) {
        this.calcService = calcService;
    }

    @PostMapping("/sum")
    public Mono<BigDecimal> sum(@RequestBody OperationRequest request){
        return calcService.sum(request.getA(), request.getB());
    }

    @GetMapping("/rabbit")
    public Mono<Boolean> send() {
        return reactiveRabbitComponent.sendMessage("Hello world");
    }

    @GetMapping("/postgres")
    public Mono<RequestLogDto> save() {
        return requestLogService.save(RequestLogDto.builder()
                .path("/postgres").jsonResponse("{json}").dateTime(LocalDateTime.now()).build());
    }
}
