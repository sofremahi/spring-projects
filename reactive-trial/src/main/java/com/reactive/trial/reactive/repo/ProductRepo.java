package com.reactive.trial.reactive.repo;

import com.reactive.trial.reactive.entity.Product;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepo extends ReactiveMongoRepository<Product, String> {
}
