package com.learning.reactivespring.fluxandmono;

import org.junit.jupiter.api.Test;
import reactor.core.publisher.Flux;
import reactor.core.scheduler.Scheduler;
import reactor.core.scheduler.Schedulers;
import reactor.test.StepVerifier;

import java.util.Arrays;
import java.util.List;
import java.util.Locale;

public class FluxMonoTransformTest {
    List<String> names = Arrays.asList("John","Jenny","James","Adam","Danny","Ivy");
    List<String> alphabets = Arrays.asList("A","B","C","D","E","F");

    @Test
    public void test_transform_1(){
        Flux<String> nameFlux = Flux.fromIterable(names)
                .map(s -> s.toUpperCase())
                .log();

        StepVerifier.create(nameFlux)
                .expectNext("JOHN","JENNY","JAMES","ADAM","DANNY","IVY")
                .verifyComplete();
    }

    @Test
    public void test_transform_2(){
        Flux<Integer> nameFlux = Flux.fromIterable(names)
                .map(s -> s.length())
                .log();

        StepVerifier.create(nameFlux)
                .expectNext(4,5,5,4,5,3)
                .verifyComplete();
    }

    @Test
    public void test_transform_3(){
        Flux<Integer> nameFlux = Flux.fromIterable(names)
                .map(s -> s.length())
                .repeat(1)
                .log();

        StepVerifier.create(nameFlux)
                .expectNext(4,5,5,4,5,3,   4,5,5,4,5,3)
                .verifyComplete();
    }

    @Test
    public void test_transform_4(){
        Flux<String> nameFlux = Flux.fromIterable(names)
                .filter(s->s.length()>4)
                .map(s -> s.toUpperCase())
                .log();

        StepVerifier.create(nameFlux)
                .expectNext("JENNY","JAMES","DANNY")
                .verifyComplete();
    }


    /* FlatMap is used for DB call or External Service call which gives us Gives us Flux<?>*/
    @Test
    public void transform_using_flatMap_1(){
        Flux<String> alphaFlux = Flux.fromStream(alphabets.stream())
                .flatMap(s->{
                    return Flux.fromIterable(makeAServiceCall(s));
                })
                .log();

        StepVerifier.create(alphaFlux)
            .expectNextCount(alphabets.size()*3)
            .verifyComplete()
            ;

    }

    @Test
    public void transform_using_flatMap_faster() {
        Flux<String> alphaFlux = Flux.fromStream(alphabets.stream())
                .window(3)
                .flatMap((s) ->
                        s.map(this::makeAServiceCall).subscribeOn(Schedulers.parallel())
                )
                .flatMap(f -> Flux.fromIterable(f))
                .log();

        StepVerifier.create(alphaFlux)
                .expectNextCount(alphabets.size() * 3)
                .verifyComplete()
        ;
    }
        @Test
        public void transform_using_flatMap_faster_maintain_order(){
            Flux<String> alphaFlux = Flux.fromStream(alphabets.stream())
                    .window(3)
                    .concatMap((s)->
                    s.map(this::makeAServiceCall).subscribeOn(Schedulers.parallel())
                    )
                    /*.flatMapSequential((s)->
                            s.map(this::makeAServiceCall).subscribeOn(Schedulers.parallel())
                    )*/
                    .flatMap(f -> Flux.fromIterable(f))
                    .log();

            StepVerifier.create(alphaFlux)
                    .expectNextCount(alphabets.size()*3)
                    .verifyComplete()
            ;

        }


    private List<String> makeAServiceCall(String s) {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return Arrays.asList(s,s.toLowerCase(),s.toUpperCase());
    }


}
