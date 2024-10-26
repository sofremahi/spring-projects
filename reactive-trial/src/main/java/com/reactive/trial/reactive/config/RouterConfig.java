package com.reactive.trial.reactive.config;

import com.reactive.trial.reactive.common.ErrorResponseDto;
import com.reactive.trial.reactive.exception.InvalidInputException;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import java.util.function.BiFunction;

@Configuration
@RequiredArgsConstructor
public class RouterConfig {
    private final MathRequestHandler mathRequestHandler;


    @Bean
    public RouterFunction<ServerResponse> routerFunction() {
        return RouterFunctions.route()
                .GET("router/table/{input}", mathRequestHandler::tableHandler)
                .POST("router/multiply" ,mathRequestHandler::multiplyHandler )
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
