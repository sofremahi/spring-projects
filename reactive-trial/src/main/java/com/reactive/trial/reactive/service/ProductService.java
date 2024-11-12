package com.reactive.trial.reactive.service;

import com.reactive.trial.reactive.dto.ProductDto;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;
@Service
public interface ProductService {
     Mono<ProductDto> createProduct(Mono<ProductDto> productDto);

     Mono<ProductDto> getProduct(String id);
}
