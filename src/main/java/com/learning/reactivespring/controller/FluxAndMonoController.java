package com.learning.reactivespring.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

import java.time.Duration;

@RestController
@RequiredArgsConstructor
@Slf4j
@Validated
public class FluxAndMonoController {

    @GetMapping("/flux")
    public Flux<Integer> fetchIntegers(){
        return Flux.just(1,2,3)
                .delayElements(Duration.ofSeconds(1))
                .log();
    }
    @GetMapping(value = "/fluxstream" ,produces = MediaType.APPLICATION_STREAM_JSON_VALUE)
    public Flux<Integer> fetchIntegersStream(){
        return Flux.range(1,10)
                .delayElements(Duration.ofSeconds(1))
                .log();
    }
}
