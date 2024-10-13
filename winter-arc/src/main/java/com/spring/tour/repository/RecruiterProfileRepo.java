package com.spring.tour.repository;

import com.spring.tour.entity.RecruiterProfile;
import org.springframework.data.repository.CrudRepository;

public interface RecruiterProfileRepo extends CrudRepository<RecruiterProfile, Long> {
}
