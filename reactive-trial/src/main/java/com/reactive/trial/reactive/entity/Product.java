package com.reactive.trial.reactive.entity;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

@Data
@Table("product")
public class Product {
    @Id
    private String  id ;
    private String name;
    private Integer price;

}
