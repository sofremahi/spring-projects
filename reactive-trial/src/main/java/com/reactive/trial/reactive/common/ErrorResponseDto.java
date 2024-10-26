package com.reactive.trial.reactive.common;

import lombok.Data;

@Data
public class ErrorResponseDto {
    private String error;
    private String message;
    private int errorCode;
}
