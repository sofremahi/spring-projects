package com.reactive.trial.reactive.entity;

import lombok.Data;
import org.springframework.data.annotation.Id;

@Data
public class Product {
    @Id
    private String id ;
    private String name;
    private Integer price;

}
