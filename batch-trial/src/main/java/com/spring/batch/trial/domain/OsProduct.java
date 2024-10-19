package com.spring.batch.trial.domain;

import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class OsProduct extends Product {
    private double tax;
    private double discount;
    private double taxPercent;
    private double finalPrice;

}
