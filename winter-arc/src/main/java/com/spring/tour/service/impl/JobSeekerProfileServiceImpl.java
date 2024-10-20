package com.spring.tour.service.impl;

import com.spring.tour.entity.JobSeekerProfile;
import com.spring.tour.entity.Users;
import com.spring.tour.repository.JobSeekerProfileRepo;
import com.spring.tour.service.JobSeekerProfileService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class JobSeekerProfileServiceImpl implements JobSeekerProfileService {
    private final JobSeekerProfileRepo jobSeekerProfileRepo;

    public Optional<JobSeekerProfile> getOne(Long id) {
        return jobSeekerProfileRepo.findById(id);
    }

    @Override
    public JobSeekerProfile addNew(JobSeekerProfile jobSeekerProfile , Users user) {
        jobSeekerProfile.setUserId(user);
        return jobSeekerProfileRepo.save(jobSeekerProfile);
    }
}
