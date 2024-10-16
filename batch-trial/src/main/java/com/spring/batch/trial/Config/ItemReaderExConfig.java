package com.spring.batch.trial.Config;

import com.spring.batch.trial.domain.Product;
import com.spring.batch.trial.domain.ProductFieldSetMapper;
import com.spring.batch.trial.domain.ProductRowMapper;
import com.spring.batch.trial.reader.ProductNameItemReader;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.database.JdbcCursorItemReader;
import org.springframework.batch.item.database.JdbcPagingItemReader;
import org.springframework.batch.item.database.support.SqlPagingQueryProviderFactoryBean;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Configuration
@RequiredArgsConstructor
public class ItemReaderExConfig {

    private final DataSource dataSource;

    @Bean
    public ItemReader<String> customItemReader() {
        List<String> productList = new ArrayList<>();
        productList.add("Apple");
        productList.add("Banana");
        productList.add("Orange");
        productList.add("Pear");
        productList.add("Grape");
        productList.add("Water");
        return new ProductNameItemReader(productList);
    }

    @Bean
    public ItemReader<Product> productItemReader() {
        FlatFileItemReader flatFileItemReader = new FlatFileItemReader();
        flatFileItemReader.setLinesToSkip(1);
        flatFileItemReader.setResource(new ClassPathResource("/data/products.csv"));
        DefaultLineMapper<Product> lineMapper = new DefaultLineMapper<>();
        DelimitedLineTokenizer lineTokenizer = new DelimitedLineTokenizer();
        lineTokenizer.setNames("id", "name", "description", "price");
//        todo:flat file item reader and delimited tokenizer has this property by default (",")
        lineMapper.setLineTokenizer(lineTokenizer);
        lineMapper.setFieldSetMapper(new ProductFieldSetMapper());
        flatFileItemReader.setLineMapper(lineMapper);
        return flatFileItemReader;
    }

    @Bean
    public ItemReader<Product> jdbcCurserItemReader() throws Exception {
        JdbcCursorItemReader<Product> itemReader = new JdbcCursorItemReader<>();
        itemReader.setDataSource(dataSource);
        itemReader.setSql("select * from product order by id");
        itemReader.setRowMapper(new ProductRowMapper());
        return itemReader;
    }

    @Bean
    public ItemReader<Product> jdbcPagingItemReader() throws Exception {
        JdbcPagingItemReader<Product> itemReader = new JdbcPagingItemReader<>();
        itemReader.setDataSource(dataSource);
        SqlPagingQueryProviderFactoryBean factory = new SqlPagingQueryProviderFactoryBean();
        factory.setDataSource(dataSource);
        factory.setSelectClause("select id, name, description, price");
        factory.setFromClause("from product");
        factory.setSortKey("id");
        itemReader.setQueryProvider(Objects.requireNonNull(factory.getObject()));
        itemReader.setRowMapper(new ProductRowMapper());
        itemReader.setPageSize(3);
        return itemReader;
    }

    @Bean
    public Step chunkFirstStep(JobRepository jobRepository, PlatformTransactionManager transactionManager) {
        return new StepBuilder("chunkStep 2", jobRepository).<String, String>chunk(3, transactionManager).reader(customItemReader()).writer((chunk -> {
            System.out.println("chunk processing started");
            chunk.forEach(System.out::println);
            System.out.println("chunk processing ended");
        })).build();
    }

    @Bean
    public Step chunkSecondStep(JobRepository jobRepository, PlatformTransactionManager transactionManager) {
        return new StepBuilder("chunkStep 1", jobRepository).<Product, Product>chunk(3, transactionManager).reader(productItemReader()).writer((chunk -> {
                    System.out.println("chunk processing started");
                    chunk.forEach(System.out::println);
                    System.out.println("chunk processing ended");
                }))
//                .writer(new ItemWriter<String>() {
//
//                    @Override
//                    public void write(Chunk<? extends String> chunk) throws Exception {
//                        System.out.println("chunk processing started");
//                        chunk.forEach(System.out::println);
//                        System.out.println("chunk processing ended");
//                    }
//                })
                .build();
    }

    @Bean
    public Step chunkThirdStep(JobRepository jobRepository, PlatformTransactionManager transactionManager) throws Exception {
        return new StepBuilder("chunkStep 3", jobRepository).<Product, Product>chunk(3, transactionManager).reader(jdbcCurserItemReader()).writer((chunk -> {
            System.out.println("chunk processing started");
            chunk.forEach(System.out::println);
            System.out.println("chunk processing ended");
        })).build();
    }

    @Bean
    public Step chunkFourthStep(JobRepository jobRepository, PlatformTransactionManager transactionManager) throws Exception {
        return new StepBuilder("chunkStep 4", jobRepository).<Product, Product>chunk(3, transactionManager)
                .reader(jdbcPagingItemReader()).writer((chunk -> {
                    System.out.println("chunk processing started");
                    chunk.forEach(System.out::println);
                    System.out.println("chunk processing ended");
                })).build();
    }


    @Bean
    public Job chunkFirstJob(JobRepository jobRepository, Step chunkFirstStep, Step chunkSecondStep, Step chunkThirdStep) throws Exception {
        return new JobBuilder("chunkJob 1", jobRepository).start(chunkFirstStep).next(chunkSecondStep).next(chunkThirdStep).build();
    }
}
