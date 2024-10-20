package com.spring.tour.repository;

import com.spring.tour.entity.JobPostActivity;
import com.spring.tour.entity.JobSeekerApply;
import com.spring.tour.entity.JobSeekerProfile;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface JobSeekerApplyRepo extends JpaRepository<JobSeekerApply, Long> {
    List<JobSeekerApply> findByUserId(JobSeekerProfile userId);

    List<JobSeekerApply> findByJob(JobPostActivity job);

}
