package com.learning.reactivespring.fluxandmono;

import org.junit.jupiter.api.Test;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

import java.time.Duration;

public class FluxAndMonoCombinedTest {

    @Test
    public void combinedUsingMerge(){
        Flux<String> alphaFlux = Flux.just("A","B","C");
        Flux<String> betaFlux = Flux.just("D","E","F");
        Flux<String> mergeFlux = Flux.merge(alphaFlux,betaFlux);
        StepVerifier.create(mergeFlux.log())
                .expectSubscription()
                .expectNext("A","B","C","D","E","F")
                .verifyComplete();

    }

    @Test
    public void combinedUsingMergeWithDelay(){
        Flux<String> alphaFlux = Flux.just("A","B","C").delayElements(Duration.ofSeconds(1));
        Flux<String> betaFlux = Flux.just("D","E","F");
        Flux<String> mergeFlux = Flux.merge(alphaFlux,betaFlux);
        StepVerifier.create(mergeFlux.log())
                .expectSubscription()
                //.expectNext("A","B","C","D","E","F")
                .expectNextCount(6)
                .verifyComplete();
    }

    @Test
    public void combinedUsingConcat(){
        Flux<String> alphaFlux = Flux.just("A","B","C");
        Flux<String> betaFlux = Flux.just("D","E","F");
        Flux<String> mergeFlux = Flux.concat(alphaFlux,betaFlux);
        StepVerifier.create(mergeFlux.log())
                .expectSubscription()
                .expectNext("A","B","C","D","E","F")
                //.expectNextCount(6)
                .verifyComplete();
    }

    @Test
    public void combinedUsingConcatWithDelay(){
        Flux<String> alphaFlux = Flux.just("A","B","C").delayElements(Duration.ofSeconds(1));
        Flux<String> betaFlux = Flux.just("D","E","F");
        Flux<String> mergeFlux = Flux.concat(alphaFlux,betaFlux);
        StepVerifier.create(mergeFlux.log())
                .expectSubscription()
                .expectNext("A","B","C","D","E","F")
                //.expectNextCount(6)
                .verifyComplete();
    }

    @Test
    public void combinedUsingZip(){
        Flux<String> alphaFlux = Flux.just("A","B","C");
        Flux<String> betaFlux = Flux.just("D","E","F");
        Flux<String> mergeFlux = Flux.zip(alphaFlux,betaFlux ,(t1,t2) ->t1.concat(t2));
        StepVerifier.create(mergeFlux.log())
                .expectSubscription()
                .expectNext("AD","BE","CF")
                //.expectNextCount(6)
                .verifyComplete();
    }
}
