package com.reactive.trial.reactive.config;

import com.reactive.trial.reactive.common.ErrorResponseDto;
import com.reactive.trial.reactive.exception.InvalidInputException;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.*;
import reactor.core.publisher.Mono;

import java.util.function.BiFunction;

@Configuration
@RequiredArgsConstructor
public class RouterConfig {
    private final MathRequestHandler mathRequestHandler;

    @Bean
    public RouterFunction<ServerResponse> highLevelRouter() {
        return RouterFunctions.route()
                .path("/router", this::routerFunction)
                .build();
    }

    //   todo: this uri s will be having suffix as router/
//@Bean
    private RouterFunction<ServerResponse> routerFunction() {
        return RouterFunctions.route()
                .GET("table/{input}", RequestPredicates.all(), mathRequestHandler::tableHandler)
                .POST("multiply", mathRequestHandler::multiplyHandler)
                .GET("divide/{a}/{b}", mathRequestHandler::divideHandler)
                .GET("addition/{a}/{b}", mathRequestHandler::additionHandler)
                .onError(InvalidInputException.class, tableHandler())
                .build();
    }

    private BiFunction<Throwable, ServerRequest, Mono<ServerResponse>> tableHandler() {
        return (err, req) -> {
            InvalidInputException ex = (InvalidInputException) err;
            ErrorResponseDto responseDto = new ErrorResponseDto();
            responseDto.setMessage(ex.getMessage());
            responseDto.setErrorCode(((InvalidInputException) err).getErrorCode());
            responseDto.setError(ex.getData());
            return ServerResponse.badRequest().bodyValue(responseDto);
        };
    }
}
