package com.reactive.trial.reactive.common;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.Date;

@Data
@ToString
@NoArgsConstructor
public class ResponseDto<T> {
    private Date timestamp = new Date();
    private T data;

}
