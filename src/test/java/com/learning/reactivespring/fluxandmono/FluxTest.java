package com.learning.reactivespring.fluxandmono;

import org.junit.jupiter.api.Test;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

public class FluxTest {

    public static void afterFluxComplete(){
        System.out.println("Flux has emitted all the available data. Process Complete.");
    }
    @Test
    public void fluxTest(){
        Flux<String> stringFlux = Flux.just("Spring","Springboot","Reactive Spring")
                //.concatWith(Flux.error(new RuntimeException("Intentional Error")))
                .concatWith(Flux.just("After Error"))
                //.log()
                ;
        stringFlux.subscribe(System.out::println ,
                (e)->System.err.println( e.getMessage() ),
                FluxTest::afterFluxComplete
        );
    }

    @Test
    public void test_fluxElements_withoutError(){
        Flux<String> stringFlux= Flux.just("Spring","Java","Reactive Java")
            .log();
        //stringFlux.subscribe(System.out::println);

        StepVerifier.create(stringFlux)
            .expectNext("Spring")
            .expectNext("Java")
            .expectNext("Reactive Java")
            .verifyComplete()
        ;
    }

    @Test
    public void test_fluxElements_withError(){
        Flux<String> stringFlux= Flux.just("Spring","Java","Reactive Java")
                .concatWith(Flux.error(new ArithmeticException("Un-Verifiable Error Occurred")))
                 .log()
                ;
        StepVerifier.create(stringFlux)
                .expectNext("Spring")
                .expectNext("Java")
                .expectNext("Reactive Java")
                .expectErrorMessage("Un-Verifiable Error Occurred")
                //.expectError(ArrayIndexOutOfBoundsException.class)
                .verify()
        ;
    }


    @Test
    public void test_fluxElements_count_WithoutError(){
        Flux<String> stringFlux= Flux.just("Spring","Java","Reactive Java")
                .log()
                ;
        StepVerifier.create(stringFlux)
                .expectNextCount(3)
                .verifyComplete()
        ;
    }

    @Test
    public void test_fluxElements_count_WithError(){
        Flux<String> stringFlux= Flux.just("Spring","Java","Reactive Java")
                .concatWith(Flux.error(new Exception("Count Error")))
                .log()
                ;
        StepVerifier.create(stringFlux)
                .expectNextCount(3)
                .expectError()
                .verify();
    }
}
