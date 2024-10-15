package com.spring.tour.service.impl;

import com.spring.tour.entity.JobPostActivity;
import com.spring.tour.entity.RecruiterJobsDto;
import com.spring.tour.repository.JobPostActivityRepo;
import com.spring.tour.service.JobPostActivityService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class JobPostActivityServiceImpl implements JobPostActivityService {
    private final JobPostActivityRepo jobPostActivityRepo;

    @Override
    public JobPostActivity addNEw(JobPostActivity jobPostActivity) {
        return jobPostActivityRepo.save(jobPostActivity);
    }

    @Override
    public List<RecruiterJobsDto> getRecruiterJobs(int recruiter) {
        return List.of();
    }

    @Override
    public JobPostActivity getById(int id) {
        return jobPostActivityRepo.findById((long) id).orElseThrow(() ->
                new RuntimeException("job not found"));

    }
}
