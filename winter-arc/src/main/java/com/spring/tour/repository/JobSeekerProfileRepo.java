package com.spring.tour.repository;

import com.spring.tour.entity.JobSeekerProfile;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JobSeekerProfileRepo extends JpaRepository<JobSeekerProfile, Long> {
}
