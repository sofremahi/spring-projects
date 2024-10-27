package com.reactive.trial.reactive.service.impl;

import com.reactive.trial.reactive.dto.ProductDto;
import com.reactive.trial.reactive.entity.Product;
import com.reactive.trial.reactive.repo.ProductRepo;
import com.reactive.trial.reactive.service.ProductService;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

import java.util.UUID;

@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {
    private final ProductRepo productRepo;


    public Mono<ProductDto> createProduct(Mono<ProductDto> productDto) {
        return productDto
                .map(item -> {
                    UUID uuid = UUID.randomUUID();
                    Product product = new Product();
                    product.setId(uuid.toString());
                    product.setName(item.getName());
                    product.setPrice(item.getPrice());
                    return product;
                })
                .flatMap(this.productRepo::insert)
                .map(item -> {
                    ProductDto pDto = new ProductDto();
                    pDto.setName(item.getName());
                    pDto.setPrice(item.getPrice());
                    return pDto;
                });
    }

    public Mono<Product> getProduct(String id) {
        return productRepo.findById(id);
    }
}
