package com.assignment.config;

import com.assignment.repository.DocumentDAO;
import org.jdbi.v3.core.Jdbi;
import org.jdbi.v3.sqlobject.SqlObjectPlugin;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import javax.sql.DataSource;

@Configuration
public class JdbiConfig {

    @Bean
    public DataSource dataSource() {
        return dataSourceProperties().initializeDataSourceBuilder().build();
    }

    @Bean
    Jdbi jdbi() {
        final Jdbi jdbi = Jdbi.create(dataSource());
        jdbi.installPlugin(new SqlObjectPlugin());
        return jdbi;
    }

    @Bean
    DocumentDAO documentDAO(final Jdbi jdbi) {
        return jdbi.onDemand(DocumentDAO.class);
    }

    @Bean
    @Primary
    @ConfigurationProperties("spring.datasource")
    public DataSourceProperties dataSourceProperties() {
        return new DataSourceProperties();
    }
}
