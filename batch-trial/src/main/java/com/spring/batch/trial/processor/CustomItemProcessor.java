package com.spring.batch.trial.processor;

import com.spring.batch.trial.domain.OsProduct;
import com.spring.batch.trial.domain.Product;
import org.springframework.batch.item.ItemProcessor;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class CustomItemProcessor implements ItemProcessor<Product, OsProduct> {
    @Override
    public OsProduct process(Product item) throws Exception {
        OsProduct osProduct = new OsProduct();
        osProduct.setId(item.getId());
        osProduct.setDescription(item.getDescription());
        osProduct.setName(item.getName());
        osProduct.setPrice(item.getPrice());
        osProduct.setDiscount(item.getPrice() * 0.015);
        osProduct.setTaxPercent(3.25);
        osProduct.setTax(item.getPrice() * osProduct.getTaxPercent() * 0.01);
        BigDecimal roundedFinalPrice = BigDecimal.valueOf(osProduct.getPrice() + osProduct.getTax() - osProduct.getDiscount()).setScale(2, RoundingMode.HALF_UP);
        osProduct.setFinalPrice(roundedFinalPrice.doubleValue());
        return osProduct;
    }
}
