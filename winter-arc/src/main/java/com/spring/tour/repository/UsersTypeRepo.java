package com.spring.tour.repository;

import com.spring.tour.entity.UsersType;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UsersTypeRepo extends JpaRepository<UsersType, Integer> {
}
