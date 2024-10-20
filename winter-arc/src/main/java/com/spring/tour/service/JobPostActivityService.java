package com.spring.tour.service;

import com.spring.tour.entity.JobPostActivity;
import com.spring.tour.entity.RecruiterJobsDto;

import java.time.LocalDate;
import java.util.List;

public interface JobPostActivityService {
    JobPostActivity addNew(JobPostActivity jobPostActivity);

    List<RecruiterJobsDto> getRecruiterJobs(int recruiter);

    JobPostActivity getById(int id);

    List<JobPostActivity> getAll();

    public List<JobPostActivity> search(String job, String location, List<String> type, List<String> remote, LocalDate searchDate);
}
