package com.learning.reactivespring.fluxandmono;

import org.junit.jupiter.api.Test;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.util.Arrays;
import java.util.List;
import java.util.function.Supplier;

public class FluxAndMonoFactoryTest {

    List<String> names = Arrays.asList("John","Adam","Joey","Jenny");


    @Test
    public void test_fluxUsingIterable(){
        Flux<String> namesFlux =Flux.fromIterable(names).log();
        StepVerifier.create(namesFlux)
                .expectNextCount(names.size())
                .verifyComplete();
    }

    @Test
    public void test_fluxUsingArray(){
        String[] namesArr = new String[]{"John","Adam","Joey","Jenny"};
        Flux<String> namesFlux =Flux.fromArray(namesArr).log();
        StepVerifier.create(namesFlux)
                .expectNextCount(names.size())
                .verifyComplete();
    }

    @Test
    public void test_fluxUsingStream(){
        Flux<String> namesFlux =Flux.fromStream(names.stream()).log();
        StepVerifier.create(namesFlux)
                .expectNext("John","Adam","Joey","Jenny")
                .verifyComplete();
    }

    @Test
    public void test_monoUsingJustOrEmpty(){
        Mono<String> monoData =Mono.justOrEmpty(null);
        StepVerifier.create(monoData.log())
                .verifyComplete();
    }

    @Test
    public void test_monoUsingSupplier(){
        Supplier<String> stringSupplier = () -> {return "Java-9";};

        Mono<String> monoData =Mono.fromSupplier(stringSupplier);
        StepVerifier.create(monoData.log())
                .expectNext("Java-9")
                .verifyComplete();
    }

    @Test
    public void test_fluxUsingRange(){
        Flux<Integer> intFlux = Flux.range(1, 5);
        StepVerifier.create(intFlux.log())
                .expectNext(1,2,3,4,5)
                .verifyComplete();
    }

}
