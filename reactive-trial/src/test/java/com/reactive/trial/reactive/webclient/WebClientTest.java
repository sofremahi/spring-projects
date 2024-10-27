package com.reactive.trial.reactive.webclient;


import com.reactive.trial.reactive.BaseTest;
import com.reactive.trial.reactive.common.ResponseDto;
import com.reactive.trial.reactive.pojo.MultiplyClass;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.util.HashMap;
import java.util.Map;


public class WebClientTest extends BaseTest {
    @Autowired
    private WebClient webClient;


    @Test
    public void MonoTest() {
        Map<String, Integer> variables = new HashMap<>();
        variables.put("a", 5);
        variables.put("b", 5);
        Mono<String> responseMono = webClient.get().uri("/router/addition/{a}/{b}", variables).retrieve().bodyToMono(String.class);

        StepVerifier.create(responseMono).expectNextMatches(r -> r.equals("10")).verifyComplete();
    }

    @Test
    public void FluxTest() {
        Flux<ResponseDto> responseFlux = webClient.get().uri("/router/table/{input}", 9).retrieve().bodyToFlux(ResponseDto.class);

        StepVerifier.create(responseFlux).expectNextCount(10).verifyComplete();
    }

    @Test
    public void postTest() {
        var response = webClient.post().uri("/router/multiply").bodyValue(createMultiplyClass(1, 8)).retrieve().bodyToMono(ResponseDto.class);
        StepVerifier.create(response).expectNextCount(1).verifyComplete();
    }


    private MultiplyClass createMultiplyClass(int first, int second) {
        MultiplyClass multiplyClass = new MultiplyClass();
        multiplyClass.setFirstNo(first);
        multiplyClass.setSecondNo(second);
        return multiplyClass;
    }
}
