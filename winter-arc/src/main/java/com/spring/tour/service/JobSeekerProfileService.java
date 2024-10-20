package com.spring.tour.service;

import com.spring.tour.entity.JobSeekerProfile;
import com.spring.tour.entity.Users;

import java.util.Optional;

public interface JobSeekerProfileService {
    public Optional<JobSeekerProfile> getOne(Long id);

    public JobSeekerProfile addNew(JobSeekerProfile jobSeekerProfile, Users user);
}
