package com.reactive.trial.reactive.common;

import lombok.Data;
import lombok.ToString;

import java.util.Date;

@Data
@ToString
public class ResponseDto<T> {
    private Date timestamp = new Date();
    private T data;

}
