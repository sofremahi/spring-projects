package com.spring.tour.service.impl;

import com.spring.tour.entity.JobPostActivity;
import com.spring.tour.entity.RecruiterJobsDto;
import com.spring.tour.repository.JobPostActivityRepo;
import com.spring.tour.service.JobPostActivityService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class JobPostActivityServiceImpl implements JobPostActivityService {
    private final JobPostActivityRepo jobPostActivityRepo;

    @Override
    public JobPostActivity addNew(JobPostActivity jobPostActivity) {
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
    public List<JobPostActivity> getAll() {
        return jobPostActivityRepo.findAll();
    }

    public List<JobPostActivity> search(String job, String location, List<String> type, List<String> remote, LocalDate searchDate) {
        return Objects.isNull(searchDate) ? jobPostActivityRepo.searchWithoutDate(job, location, remote,type) :
                jobPostActivityRepo.search(job, location, remote, type, searchDate);
    }
}
