package com.reactive.trial.reactive.config;

import io.r2dbc.h2.H2ConnectionConfiguration;
import io.r2dbc.h2.H2ConnectionFactory;
import io.r2dbc.spi.ConnectionFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.r2dbc.core.R2dbcEntityTemplate;
import org.springframework.data.r2dbc.repository.config.EnableR2dbcRepositories;

@Configuration
@EnableR2dbcRepositories(basePackages = "com.reactive.trial.reactive.repo")
public class R2dbcConfig {

    @Bean
    public R2dbcEntityTemplate r2dbcEntityTemplate(ConnectionFactory connectionFactory) {
        return new R2dbcEntityTemplate(connectionFactory);
    }

    @Bean
    public ConnectionFactory connectionFactory() {
        H2ConnectionConfiguration configuration = H2ConnectionConfiguration.builder()
                .url("r2dbc:h2:mem:///testdb")
                .username("sa")
                .password("")
                .build();

        return new H2ConnectionFactory(configuration);
    }
}
