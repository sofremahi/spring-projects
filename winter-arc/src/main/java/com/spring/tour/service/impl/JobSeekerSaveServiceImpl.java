package com.spring.tour.service.impl;

import com.spring.tour.entity.JobPostActivity;
import com.spring.tour.entity.JobSeekerProfile;
import com.spring.tour.entity.JobSeekerSave;
import com.spring.tour.repository.JobSeekerSaveRepo;
import com.spring.tour.service.JobSeekerSaveService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class JobSeekerSaveServiceImpl implements JobSeekerSaveService {
    private final JobSeekerSaveRepo jobSeekerSaveRepo;

    public List<JobSeekerSave> getCandidatesJob(JobSeekerProfile userAccountId) {
        return jobSeekerSaveRepo.findByUserId(userAccountId);
    }

    public List<JobSeekerSave> getJobCandidates(JobPostActivity job) {
        return jobSeekerSaveRepo.findByJob(job);
    }
}
