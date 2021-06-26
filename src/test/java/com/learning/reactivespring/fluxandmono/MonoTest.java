package com.learning.reactivespring.fluxandmono;

import org.junit.jupiter.api.Test;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

public class MonoTest {

    @Test
    public void monoTest(){
        Mono<String> monoString = Mono.just("Gap.Inc");
            monoString.subscribe(System.out::println);
    }

    @Test
    public void test_monoExpectNext(){
        Mono<String> monoString = Mono.just("Gap.Inc");

        StepVerifier.create(monoString.log()).expectNext("Gap.Inc")
                .verifyComplete();
    }

    @Test
    public void test_mono_error(){

        StepVerifier.create(Mono.error(new Exception("Mono Error")).log())
                .expectErrorMessage("Mono Error")
                //.expectError(Exception.class);
                .verify();

    }
}
