package com.learning.reactivespring.fluxandmono;

import org.junit.jupiter.api.Test;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

import java.time.Duration;

public class FluxAndMonoErrorTest {
    @Test
    public void fluxErrorHandling(){
        Flux<String> alphaFlux = Flux.just("A","B","C")
                .concatWith(Flux.error(new RuntimeException("Error Occurred")))
                .concatWith(Flux.just("D"));

        StepVerifier.create(alphaFlux.log())
                .expectSubscription()
                .expectNext("A","B","C")
                .expectError()
                .verify();
    }

    @Test
    public void fluxErrorHandling_1(){
        Flux<String> alphaFlux = Flux.just("A","B","C")
                .concatWith(Flux.error(new RuntimeException("Error Occurred")))
                .concatWith(Flux.just("D"))
                .onErrorResume((e) ->  {
                    System.out.println("Error Occured : "+e.getMessage());
                    return Flux.just("Default");
                })
                ;

        StepVerifier.create(alphaFlux.log())
                .expectSubscription()
                .expectNext("A","B","C")
                .expectNext("Default")
                .verifyComplete();
    }

    @Test
    public void fluxErrorHandling_2(){
        Flux<String> alphaFlux = Flux.just("A","B","C")
                .concatWith(Flux.error(new RuntimeException("Error Occurred")))
                .concatWith(Flux.just("D"))
                .onErrorReturn("Default")
                ;
        StepVerifier.create(alphaFlux.log())
                .expectSubscription()
                .expectNext("A","B","C")
                .expectNext("Default")
                .verifyComplete();
    }

    @Test
    public void fluxErrorHandling_3(){
        Flux<String> alphaFlux = Flux.just("A","B","C")
                .concatWith(Flux.error(new RuntimeException("Error Occurred")))
                .concatWith(Flux.just("D"))
                .onErrorMap((e) -> new ArrayIndexOutOfBoundsException(e.getMessage()))
                ;
        StepVerifier.create(alphaFlux.log())
                .expectSubscription()
                .expectNext("A","B","C")
                .expectError(ArrayIndexOutOfBoundsException.class)
                .verify();
    }

    @Test
    public void fluxErrorHandling_4(){
        Flux<String> alphaFlux = Flux.just("A","B","C")
                .concatWith(Flux.error(new RuntimeException("Error Occurred")))
                .concatWith(Flux.just("D"))
                .onErrorMap((e) -> new ArrayIndexOutOfBoundsException(e.getMessage()))
                .retry(2)
                ;
        StepVerifier.create(alphaFlux.log())
                .expectSubscription()
                .expectNext("A","B","C")
                .expectNext("A","B","C")
                .expectNext("A","B","C")
                .expectError(ArrayIndexOutOfBoundsException.class)
                .verify();
    }
}
