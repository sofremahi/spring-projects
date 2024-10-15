package com.spring.tour.service;

import com.spring.tour.entity.JobPostActivity;
import com.spring.tour.entity.RecruiterJobsDto;

import java.util.List;

public interface JobPostActivityService {
    JobPostActivity addNEw(JobPostActivity jobPostActivity);
    List<RecruiterJobsDto> getRecruiterJobs(int recruiter);

    JobPostActivity getById(int id);
}
