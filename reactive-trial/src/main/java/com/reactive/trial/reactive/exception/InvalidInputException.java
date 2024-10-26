package com.reactive.trial.reactive.exception;

import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class InvalidInputException extends RuntimeException {
    private String data;
    private int ErrorCode;

    public InvalidInputException(String data, int errorCode, String errorMessage) {
        super(data);
        this.data = errorMessage;
        this.ErrorCode = errorCode;
    }

}
