package com.spring.batch.trial.Config;

import com.spring.batch.trial.domain.OsProduct;
import com.spring.batch.trial.domain.OsProductItemPreparedStatementSetter;
import com.spring.batch.trial.domain.Product;
import com.spring.batch.trial.domain.ProductRowMapper;
import com.spring.batch.trial.processor.CustomItemProcessor;
import com.spring.batch.trial.validator.ProductValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.job.flow.Flow;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.database.JdbcBatchItemWriter;
import org.springframework.batch.item.database.JdbcCursorItemReader;
import org.springframework.batch.item.support.CompositeItemProcessor;
import org.springframework.batch.item.validator.ValidatingItemProcessor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.List;

@Configuration
@RequiredArgsConstructor
public class ItemProcessorExConfig {
    private final DataSource dataSource;

    @Bean
    public ItemReader<Product> jdbcCurserItemReaderForChunk() {
        JdbcCursorItemReader<Product> itemReader = new JdbcCursorItemReader<>();
        itemReader.setDataSource(dataSource);
        itemReader.setSql("select * from product order by id");
        itemReader.setRowMapper(new ProductRowMapper());
        return itemReader;
    }

    @Bean
    public JdbcBatchItemWriter<OsProduct> jdbcBatchItemWriterForChunk() {
        JdbcBatchItemWriter<OsProduct> itemWriter = new JdbcBatchItemWriter<>();
        itemWriter.setDataSource(dataSource);
        itemWriter.setSql("insert into os_product values(?,?,?,?,?,?,?,?)");
        itemWriter.setItemPreparedStatementSetter(new OsProductItemPreparedStatementSetter());
        return itemWriter;
    }

    @Bean
    public ItemProcessor<Product, OsProduct> itemProcessorEx() {
        return new CustomItemProcessor();
    }

    @Bean
    public ValidatingItemProcessor<Product> validatingItemProcessorEx() {
        ValidatingItemProcessor<Product> processor = new ValidatingItemProcessor<>(new ProductValidator());
        processor.setFilter(true);
        return processor;
    }

    public CompositeItemProcessor<Product, OsProduct> compositeItemProcessorEx() {
        CompositeItemProcessor<Product, OsProduct> processor = new CompositeItemProcessor<>();
        List itemProcessors = new ArrayList<>();
        itemProcessors.add(validatingItemProcessorEx());
        itemProcessors.add(itemProcessorEx());
        processor.setDelegates(itemProcessors);
        return processor;
    }

    @Bean
    public Step step_01(JobRepository jobRepository, PlatformTransactionManager transactionManager) {
        return new StepBuilder("step_01", jobRepository).<Product, OsProduct>chunk(3, transactionManager)
                .reader(jdbcCurserItemReaderForChunk())
                .processor(compositeItemProcessorEx())
                .writer(jdbcBatchItemWriterForChunk()).build();
    }

    @Bean
    public Step jobStep(JobRepository jobRepository, Job chunkFirstJob) {
        return new StepBuilder("jobStep", jobRepository).job(chunkFirstJob).build();
    }

    @Bean
    public Job Job_01(JobRepository jobRepository, Step step_01, Step chunkStepThird, Flow firstFlow, Step jobStep) {
        return new JobBuilder("Job_01", jobRepository).start(step_01).
                on("COMPLETED").to(chunkStepThird)
                .from(chunkStepThird).on("COMPLETED").to(firstFlow).next(jobStep).end().build();
    }
}
