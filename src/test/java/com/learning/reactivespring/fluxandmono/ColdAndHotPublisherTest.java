package com.learning.reactivespring.fluxandmono;

import org.junit.jupiter.api.Test;
import reactor.core.publisher.ConnectableFlux;
import reactor.core.publisher.Flux;

import java.time.Duration;

public class ColdAndHotPublisherTest {

    @Test
    public void coldPublisherTest() throws InterruptedException {
        Flux<String> alphaString = Flux.just("A","B","C","D","E","F")
                .delayElements(Duration.ofSeconds(1));

        alphaString.subscribe(e -> System.out.println("Sub-1: Element is :"+e));
        Thread.sleep(2000);
        alphaString.subscribe(e -> System.out.println("Sub-2: Element is :"+e));
        Thread.sleep(10000);
    }

    @Test
    public void hotPublisherTest() throws InterruptedException {
        Flux<String> alphaString = Flux.just("A","B","C","D","E","F")
                .delayElements(Duration.ofSeconds(1));

        ConnectableFlux<String> connectableFlux = alphaString.publish();
        connectableFlux.connect();
        connectableFlux.subscribe(e -> System.out.println("Sub-1: Element is :"+e));
        Thread.sleep(3000);
        connectableFlux.subscribe(e -> System.out.println("Sub-2: Element is :"+e));
        Thread.sleep(4000);
    }
}
