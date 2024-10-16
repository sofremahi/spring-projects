package com.spring.batch.trial.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class JobLunchController {
    private final JobLauncher jobLauncher;

    private Job job;

    @Qualifier("chunkJobOne")
    @Autowired
    public void setJob(Job job) {
        this.job = job;
    }

    @GetMapping("/lunchJob/{id}")
    public void handle(@PathVariable("id") String id) throws Exception {
        JobParameters jobParameters = new JobParametersBuilder().addString("param", id).toJobParameters();
        jobLauncher.run(job, jobParameters);

    }
}
