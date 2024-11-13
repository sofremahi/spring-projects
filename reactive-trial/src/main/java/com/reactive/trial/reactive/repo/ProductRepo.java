package com.reactive.trial.reactive.repo;

import com.reactive.trial.reactive.entity.Product;
import org.springframework.data.r2dbc.repository.R2dbcRepository;


public interface ProductRepo extends R2dbcRepository<Product, String> {
}
