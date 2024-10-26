package com.reactive.trial.reactive.controller;

import com.reactive.trial.reactive.common.ResponseDto;
import com.reactive.trial.reactive.service.MathService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;


@RestController
@RequiredArgsConstructor
public class MathController {
    private final MathService mathService;

    @GetMapping(value = "/table/{i}", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<ResponseDto<Integer>> table(@PathVariable int i) {
        return mathService.mathTable(i);


    }
}
