package com.spring.tour.repository;

import com.spring.tour.entity.JobPostActivity;
import com.spring.tour.entity.JobSeekerProfile;
import com.spring.tour.entity.JobSeekerSave;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface JobSeekerSaveRepo extends JpaRepository<JobSeekerSave, Long> {
     List<JobSeekerSave> findByUserId(JobSeekerProfile userAccountId);

    List<JobSeekerSave> findByJob(JobPostActivity job);

}
