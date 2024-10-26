package com.reactive.trial.reactive.exception;

import com.reactive.trial.reactive.common.ErrorResponseDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;

@ControllerAdvice
public class ExceptionHandler {
    @org.springframework.web.bind.annotation.ExceptionHandler(InvalidInputException.class)
    public ResponseEntity<ErrorResponseDto> handleInvalidInputException(InvalidInputException e) {
        ErrorResponseDto response = new ErrorResponseDto();
        response.setErrorCode(e.getErrorCode());
        response.setError(e.getData());
        response.setMessage(e.getMessage());
        return ResponseEntity.badRequest().body(response);
    }
}
