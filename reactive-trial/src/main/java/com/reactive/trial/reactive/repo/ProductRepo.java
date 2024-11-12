package com.reactive.trial.reactive.repo;

import com.reactive.trial.reactive.entity.Product;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepo extends ReactiveCrudRepository<Product, String> {
}
