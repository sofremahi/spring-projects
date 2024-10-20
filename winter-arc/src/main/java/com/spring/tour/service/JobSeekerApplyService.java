package com.spring.tour.service;

import com.spring.tour.entity.JobPostActivity;
import com.spring.tour.entity.JobSeekerApply;
import com.spring.tour.entity.JobSeekerProfile;

import java.util.List;

public interface JobSeekerApplyService {
    List<JobSeekerApply> getCandidatesJobs(JobSeekerProfile userAccountId);

    List<JobSeekerApply> getJobCandidates(JobPostActivity job);

    public void addNew(JobSeekerApply jobSeekerApply, JobSeekerProfile userAccountId);
}
