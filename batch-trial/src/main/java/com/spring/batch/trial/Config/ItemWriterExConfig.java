package com.spring.batch.trial.Config;

import com.spring.batch.trial.domain.Product;
import com.spring.batch.trial.domain.ProductItemPreparedStatementSetter;
import com.spring.batch.trial.domain.ProductRowMapper;
import com.spring.batch.trial.processor.CustomNullProcessor;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.database.BeanPropertyItemSqlParameterSourceProvider;
import org.springframework.batch.item.database.JdbcBatchItemWriter;
import org.springframework.batch.item.database.JdbcPagingItemReader;
import org.springframework.batch.item.database.support.SqlPagingQueryProviderFactoryBean;
import org.springframework.batch.item.file.FlatFileItemWriter;
import org.springframework.batch.item.file.transform.BeanWrapperFieldExtractor;
import org.springframework.batch.item.file.transform.DelimitedLineAggregator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.FileSystemResource;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;
import java.util.Objects;

@Configuration
@RequiredArgsConstructor
public class ItemWriterExConfig {

    private final DataSource dataSource;

    @Bean
    public ItemReader<Product> jdbcPagingItemReaderForChunk() throws Exception {
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
    public FlatFileItemWriter<Product> flatFileItemWriter()  {
        FlatFileItemWriter<Product> itemWriter = new FlatFileItemWriter<>();
        itemWriter.setResource(new FileSystemResource("src/main/resources/data/products-output.csv"));
        DelimitedLineAggregator<Product> lineAggregator = new DelimitedLineAggregator<>();
        lineAggregator.setDelimiter(",");
        BeanWrapperFieldExtractor<Product> fieldExtractor = new BeanWrapperFieldExtractor<>();
        fieldExtractor.setNames(new String[]{"id", "name", "description", "price"});
        lineAggregator.setFieldExtractor(fieldExtractor);
        itemWriter.setLineAggregator(lineAggregator);
        return itemWriter;
    }

    @Bean
    public JdbcBatchItemWriter<Product> jdbcBatchItemWriter()  {
        JdbcBatchItemWriter<Product> itemWriter = new JdbcBatchItemWriter<>();
        itemWriter.setDataSource(dataSource);
        itemWriter.setSql("insert into product_output values(?,?,?,?)");
        itemWriter.setItemPreparedStatementSetter(new ProductItemPreparedStatementSetter());
        return itemWriter;
    }

    @Bean
    public JdbcBatchItemWriter<Product> jdbcBatchItemWriterNameParameters()  {
        JdbcBatchItemWriter<Product> itemWriter = new JdbcBatchItemWriter<>();
        itemWriter.setDataSource(dataSource);
        itemWriter.setSql("insert into product_output values(:id,:name,:description,:price)");
        itemWriter.setItemSqlParameterSourceProvider(new BeanPropertyItemSqlParameterSourceProvider<>());
        return itemWriter;
    }

    @Bean
    public ItemProcessor<Product, Product> itemProcessor() {
        return new CustomNullProcessor();
    }

    @Bean
    public Step chunkStepOne(JobRepository jobRepository, PlatformTransactionManager transactionManager) throws Exception {
        return new StepBuilder("stepChunk1", jobRepository).<Product, Product>chunk(3, transactionManager).reader(jdbcPagingItemReaderForChunk()).writer(flatFileItemWriter()).build();
    }

    @Bean
    public Step chunkStepTwo(JobRepository jobRepository, PlatformTransactionManager transactionManager) throws Exception {
        return new StepBuilder("stepChunk2", jobRepository).<Product, Product>chunk(3, transactionManager).reader(jdbcPagingItemReaderForChunk())
                .processor(itemProcessor())
                .writer(jdbcBatchItemWriter()).build();
    }

    @Bean
    public Step chunkStepThird(JobRepository jobRepository, PlatformTransactionManager transactionManager) throws Exception {
        return new StepBuilder("stepChunk3", jobRepository).<Product, Product>chunk(3, transactionManager).reader(jdbcPagingItemReaderForChunk())
                .writer(jdbcBatchItemWriterNameParameters()).build();
    }


    @Bean
    public Job chunkJobOne(JobRepository jobRepository, Step chunkStepOne, Step chunkStepTwo, Step chunkStepThird) {
        return new JobBuilder("jobChunk1", jobRepository).start(chunkStepOne).next(chunkStepTwo).next(chunkStepThird).build();
    }

}
