package com.spring.tour.service;

import com.spring.tour.entity.JobPostActivity;
import com.spring.tour.entity.JobSeekerProfile;
import com.spring.tour.entity.JobSeekerSave;

import java.util.List;

public interface JobSeekerSaveService {
    public List<JobSeekerSave> getCandidatesJob(JobSeekerProfile userAccountId);

    public List<JobSeekerSave> getJobCandidates(JobPostActivity job);
}
