package com.learning.reactivespring.fluxandmono;

import org.junit.jupiter.api.Test;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;
import reactor.test.scheduler.VirtualTimeScheduler;

import java.time.Duration;

public class VirtualTimeTest {

    @Test
    public void withoutVirtualTimeTest(){
        Flux<Long> longFlux = Flux.interval(Duration.ofSeconds(1))
                .take(3);
        StepVerifier.create(longFlux.log())
                .expectSubscription()
                .expectNext(0L,1L,2L)
                .verifyComplete();
    }

    @Test
    public void withVirtualTimeTest_1(){
        VirtualTimeScheduler.getOrSet();
        Flux<Long> longFlux = Flux.interval(Duration.ofSeconds(1))
                .take(3);
        StepVerifier.withVirtualTime(() -> longFlux.log())
                .expectSubscription()
                .thenAwait(Duration.ofSeconds(3))
                .expectNext(0L,1L,2L)
                .verifyComplete();
    }
    @Test
    public void withVirtualTimeTest_2(){
        VirtualTimeScheduler.getOrSet();
        Flux<Long> longFlux = Flux.interval(Duration.ofSeconds(1))
                .take(3);
        StepVerifier.withVirtualTime(() -> longFlux.log())
                .expectSubscription()
                .thenAwait(Duration.ofSeconds(2))
                .expectNext(0L,1L)
                .thenAwait(Duration.ofSeconds(1))
                .expectNext(2L)
                .verifyComplete();
    }
}
