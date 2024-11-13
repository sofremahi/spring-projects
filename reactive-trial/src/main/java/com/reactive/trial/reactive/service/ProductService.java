package com.reactive.trial.reactive.service;

import com.reactive.trial.reactive.dto.ProductDto;
import reactor.core.publisher.Mono;

public interface ProductService {
     Mono<ProductDto> createProduct(Mono<ProductDto> productDto);

     Mono<ProductDto> getProduct(String id);
}
