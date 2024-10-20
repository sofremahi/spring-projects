package com.spring.tour.service.impl;

import com.spring.tour.entity.JobPostActivity;
import com.spring.tour.entity.JobSeekerApply;
import com.spring.tour.entity.JobSeekerProfile;
import com.spring.tour.repository.JobSeekerApplyRepo;
import com.spring.tour.service.JobSeekerApplyService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class JobSeekerApplyServiceImpl implements JobSeekerApplyService {
    private final JobSeekerApplyRepo jobSeekerApplyRepository;

    public List<JobSeekerApply> getCandidatesJobs(JobSeekerProfile userAccountId) {
        return jobSeekerApplyRepository.findByUserId(userAccountId);
    }

    public List<JobSeekerApply> getJobCandidates(JobPostActivity job) {
        return jobSeekerApplyRepository.findByJob(job);
    }

    public void addNew(JobSeekerApply jobSeekerApply ,JobSeekerProfile userAccountId ) {
        jobSeekerApply.setUserId(userAccountId);
        jobSeekerApplyRepository.save(jobSeekerApply);
    }
}
