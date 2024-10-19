package com.spring.batch.trial.Config;

import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.job.builder.FlowBuilder;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.job.flow.Flow;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.task.SimpleAsyncTaskExecutor;

@Configuration
@RequiredArgsConstructor
public class FlowExConfig {

    @Bean
    public org.springframework.batch.core.job.flow.Flow firstFlow(Step chunkFirstStep, Step chunkSecondStep) {
        FlowBuilder<Flow> flow = new FlowBuilder<>("firstFlow");
        flow.start(chunkFirstStep)
                .next(chunkSecondStep).end();
        return flow.build();
    }

    @Bean
    public org.springframework.batch.core.job.flow.Flow secondFlow(Step chunkThirdStep, Step chunkFourthStep) {
        FlowBuilder<Flow> flow = new FlowBuilder<>("secondFlow");
        flow.start(chunkThirdStep)
                .next(chunkFourthStep).end();
        return flow.build();
    }

    @Bean
    public Job jobParallelFlow(JobRepository jobRepository, Flow firstFlow, Flow secondFlow) {
        return new JobBuilder("JobParallelFlow", jobRepository).start(firstFlow).split(new SimpleAsyncTaskExecutor())
                .add(secondFlow
                        //todo: separate other flows with , here to add
                ).end().build();
    }
}
