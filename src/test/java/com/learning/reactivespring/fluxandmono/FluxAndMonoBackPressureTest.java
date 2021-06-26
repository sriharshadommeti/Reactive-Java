package com.learning.reactivespring.fluxandmono;

import org.junit.jupiter.api.Test;
import org.reactivestreams.Subscription;
import reactor.core.publisher.BaseSubscriber;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

public class FluxAndMonoBackPressureTest {

    @Test
    public void backPressureTest_1(){
        Flux<Integer> intFlux = Flux.range(1, 10).log();
        StepVerifier.create(intFlux)
                .expectSubscription()
                .thenRequest(2)
                .expectNext(1,2)
                .thenRequest(1)
                .expectNext(3)
                .thenCancel()
                .verify();

    }

    @Test
    public void backPressureTest_2(){
        Flux<Integer> intFlux = Flux.range(1, 10).log();
        intFlux.subscribe(
                e ->  System.out.println("Element received : "+e),
                e ->  System.err.println("Exception Occurred"),
                () -> System.out.println("Processing Complete"),
                subscription -> subscription.request(2));
    }

    @Test
    public void backPressureTest_cancel(){
        Flux<Integer> intFlux = Flux.range(1, 10).log();
        intFlux.subscribe(
                e ->  System.out.println("Element received : "+e),
                e ->  System.err.println("Exception Occurred"),
                () -> System.out.println("Processing Complete"),
                subscription -> subscription.cancel());
    }

    @Test
    public void backPressureTest_custom(){
        Flux<Integer> intFlux = Flux.range(1, 10).log();
        intFlux.subscribe(new BaseSubscriber<Integer>() {
            @Override
            protected void hookOnNext(Integer value) {
                super.hookOnNext(value);
                request(1);
                System.out.println("Element is : "+value);
                if(value>5){
                    cancel();
                }
            }
        });
    }
}
