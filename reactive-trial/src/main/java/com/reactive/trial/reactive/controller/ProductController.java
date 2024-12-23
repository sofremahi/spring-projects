package com.reactive.trial.reactive.controller;

import com.reactive.trial.reactive.dto.ProductDto;
import com.reactive.trial.reactive.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/product")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;


    @PostMapping("/create")
    public Mono<ProductDto> createProduct(@RequestBody Mono<ProductDto> product) {
        return productService.createProduct(product);

    }

    @GetMapping("/get/{id}")
    public Mono<ProductDto> getProduct(@PathVariable String id) {
        return productService.getProduct(id);
    }

    @GetMapping("/get-all")
    public Flux<ProductDto> getAllProducts(@RequestParam(required = false) String currency) {
        return productService.getAllProducts(currency);
    }

}
