package com.spring.batch.trial.processor;

import com.spring.batch.trial.domain.Product;
import org.springframework.batch.item.ItemProcessor;

public class CustomNullProcessor implements ItemProcessor<Product, Product> {
    @Override
    public Product process(Product item) throws Exception {
        return null;
    }
}
