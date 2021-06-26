package com.learning.reactivespring.fluxandmono;

import org.junit.jupiter.api.Test;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

import java.util.Arrays;
import java.util.List;

public class FluxMonoFilterTest {

    List<String> names = Arrays.asList("John","Jenny","James","Adam","Danny","Ivy");
    @Test
    public void FLuxFilterTest(){
        Flux<String> namesFlux = Flux.fromIterable(names)
                .filter(s->s.startsWith("D"));
        StepVerifier.create(namesFlux.log())
                .expectNext("Danny")
                .verifyComplete();

    }

    @Test
    public void FLuxFilterTest2(){
        Flux<String> namesFlux = Flux.fromIterable(names)
                .filter(s->s.startsWith("D"));
        StepVerifier.create(namesFlux.log())
                .expectNext("Danny")
                .verifyComplete();

    }
}
