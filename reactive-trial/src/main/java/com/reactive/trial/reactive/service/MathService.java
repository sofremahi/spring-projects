package com.reactive.trial.reactive.service;

import com.reactive.trial.reactive.common.ResponseDto;
import com.reactive.trial.reactive.exception.InvalidInputException;
import com.reactive.trial.reactive.pojo.MultiplyClass;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

import java.time.Duration;

@Service
public class MathService {
    public Flux<ResponseDto<Integer>> mathTable(int i) {
        return Flux.generate(() -> 0, (integer, responseDtoSynchronousSink) -> {

                            if (i > 99 || i < -100) {
                                responseDtoSynchronousSink.error(new InvalidInputException("number must be above -100 and below 99", 400, "invalid input"));
                            }
                            responseDtoSynchronousSink.next(integer * i);
                            if (integer == 10) {
                                responseDtoSynchronousSink.complete();
                            }
                            integer++;
                            return integer;
                        }

                ).map(d -> {
                    ResponseDto<Integer> responseDto = new ResponseDto<>();
                    responseDto.setData((Integer) d);
                    return responseDto;

                })
                .publishOn(Schedulers.parallel())
                .doOnNext(System.out::println)
                .delayElements(Duration.ofSeconds(1))
                .doOnError((err) -> System.out.println("error: " + err.getMessage()));
    }

    public Mono<ResponseDto<Integer>> multiply(Mono<MultiplyClass> multiplyClass)  {
        return multiplyClass.map(dto -> dto.getFirstNo() * dto.getSecondNo())
                .log()
                .map(d -> {
                    ResponseDto<Integer> responseDto = new ResponseDto<>();
                    responseDto.setData(d);
                    return responseDto;
                });

    }
}
