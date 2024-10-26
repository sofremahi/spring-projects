package com.reactive.trial.reactive.config;

import com.reactive.trial.reactive.common.ResponseDto;
import com.reactive.trial.reactive.exception.InvalidInputException;
import com.reactive.trial.reactive.pojo.MultiplyClass;
import com.reactive.trial.reactive.service.MathService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class MathRequestHandler {
    public final MathService mathService;

    public Mono<ServerResponse> tableHandler(ServerRequest request) {
        int input = Integer.parseInt(request.pathVariable("input"));
        if(input > 99 || input < -100){
            return Mono.error(new InvalidInputException("number must be above -100 and below 99", 400, "invalid input"));
        }
        Flux<ResponseDto<Integer>> responseFlux = mathService.mathTable(input);
        return ServerResponse.ok()
//                .contentType(MediaType.TEXT_EVENT_STREAM)
                .body(responseFlux, ResponseDto.class);
    }
    public Mono<ServerResponse> multiplyHandler(ServerRequest request)  {
        Mono<MultiplyClass> requestDto = request.bodyToMono(MultiplyClass.class);
        System.out.println(requestDto.getClass());
        Mono<ResponseDto<Integer>> responseMono = mathService.multiply(requestDto);
        return ServerResponse.ok()
                .body(responseMono, ResponseDto.class);
    }
}
