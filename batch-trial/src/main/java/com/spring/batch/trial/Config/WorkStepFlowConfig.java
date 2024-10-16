package com.spring.batch.trial.Config;

import com.spring.batch.trial.decider.MyJobExecutionDecider;
import com.spring.batch.trial.listener.MyStepExecutionListener;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.StepExecutionListener;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.job.flow.JobExecutionDecider;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;

@Configuration
@RequiredArgsConstructor
@Slf4j
public class WorkStepFlowConfig {

    @Bean
    public StepExecutionListener stepExecutionListener() {
        return new MyStepExecutionListener();
    }

    @Bean
    public JobExecutionDecider decider() {
        return new MyJobExecutionDecider();
    }

    @Bean
    public Step firstStep(JobRepository jobRepository, PlatformTransactionManager transactionManager) {
        return new StepBuilder("step1", jobRepository).tasklet((contribution, chunkContext) -> {
            System.out.println("Hello world from step 1");
            return RepeatStatus.FINISHED;
        }, transactionManager).build();
    }

    @Bean
    public Step secondStep(JobRepository jobRepository, PlatformTransactionManager transactionManager) {
        return new StepBuilder("step2", jobRepository).tasklet((contribution, chunkContext) -> {
            //todo: take out to see the result
            // throw new RuntimeException("step failed");
            System.out.println("Hello world from step 2");
            return RepeatStatus.FINISHED;
        }, transactionManager)
//                todo:add listener for custom exit status be stored in db
//                 .listener(stepExecutionListener())
                .build();
    }

    @Bean
    public Step thirdStep(JobRepository jobRepository, PlatformTransactionManager transactionManager) {
        return new StepBuilder("step3", jobRepository).tasklet((contribution, chunkContext) -> {
            System.out.println("Hello world from step 3");
            return RepeatStatus.FINISHED;
        }, transactionManager).build();
    }


    @Bean
    public Step fourthStep(JobRepository jobRepository, PlatformTransactionManager transactionManager) {
        return new StepBuilder("step4", jobRepository).tasklet((contribution, chunkContext) -> {
            System.out.println("Hello world from step 4");
            return RepeatStatus.FINISHED;
        }, transactionManager).build();
    }

    @Bean
    public Step fithStep(JobRepository jobRepository, PlatformTransactionManager transactionManager) {
        return new StepBuilder("step5", jobRepository).tasklet((contribution, chunkContext) -> {
            System.out.println("Hello world from step 5");
            return RepeatStatus.FINISHED;
        }, transactionManager).build();
    }

    @Bean
    public Job firstJob(
            JobRepository jobRepository, Step firstStep, Step secondStep, Step thirdStep, Step fourthStep
            , Step fithStep
    ) {
        return new JobBuilder("job1", jobRepository)
//                .preventRestart()
//                .start(firstStep).next(secondStep).next(thirdStep)
                .start(firstStep)
                .on("COMPLETED").to(secondStep)
                .from(secondStep)
                .on("COMPLETED").to(thirdStep)
                .from(secondStep)
                .on("FAILED").to(fourthStep)
                .from(secondStep)
                .on("TEST_STATUS")
//                .to(decider()).on("TEST_STATUS")
                .to(fithStep)
//                .from(decider()).on("*").to(fourthStep)
                .end()
                .build();
    }

}

