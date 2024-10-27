package com.reactive.trial.reactive.service;

import com.reactive.trial.reactive.dto.ProductDto;
import com.reactive.trial.reactive.entity.Product;
import reactor.core.publisher.Mono;

public interface ProductService {
    public Mono<ProductDto> createProduct(Mono<ProductDto> productDto);

    Mono<Product> getProduct(String id);
}
