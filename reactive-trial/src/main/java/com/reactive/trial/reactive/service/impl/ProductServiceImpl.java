package com.reactive.trial.reactive.service.impl;

import com.reactive.trial.reactive.dto.ProductDto;
import com.reactive.trial.reactive.entity.Product;
import com.reactive.trial.reactive.repo.ProductRepo;
import com.reactive.trial.reactive.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.r2dbc.core.R2dbcEntityTemplate;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {
    private final ProductRepo productRepo;
    private final R2dbcEntityTemplate r2dbcEntityTemplate;

    public Mono<ProductDto> createProduct(Mono<ProductDto> productDto) {
        return productDto
                .map(item -> {
                    Product product = new Product();
                    product.setId(UUID.randomUUID().toString());
                    System.out.println("product id: " + product.getId());
                    product.setName(item.getName());
                    product.setPrice(item.getPrice());
                    return product;
                })
                .flatMap(r2dbcEntityTemplate::insert)
                .map(item -> {
                    ProductDto pDto = new ProductDto();
                    pDto.setName(item.getName());
                    pDto.setPrice(item.getPrice());
                    return pDto;
                });
    }

    public Mono<ProductDto> getProduct(String id) {
        return productRepo.findById(id).map(i->{
            ProductDto pDto = new ProductDto();
            pDto.setName(i.getName());
            pDto.setPrice(i.getPrice());
            return pDto;
        });
    }
}
