package com.spring.batch.trial.validator;

import com.spring.batch.trial.domain.Product;
import org.springframework.batch.item.validator.ValidationException;
import org.springframework.batch.item.validator.Validator;

public class ProductValidator implements Validator<Product> {
    @Override
    public void validate(Product value) throws ValidationException {
        if (value.getPrice() < 0) {
            throw new ValidationException("Price must be greater than 0");
        }
    }
}
