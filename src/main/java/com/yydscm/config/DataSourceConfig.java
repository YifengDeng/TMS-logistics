package com.yydscm.config;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.jdbc.DataSourceBuilder;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import javax.sql.DataSource;

/**
 * Created by chenzhaopeng on 2017/7/11.
 */
@Configuration
public class DataSourceConfig {

    @Bean("primaryDataSource")
    @Qualifier("primaryDataSource")
    @Primary
    @ConfigurationProperties(prefix = "spring.datasource.primary")
    public DataSource PrimaryDatasource() {
        return DataSourceBuilder.create().build();
    }


    @Bean(name = "WxDatasource")
    @Qualifier("WxDatasource")
    @ConfigurationProperties(prefix = "spring.datasource.secondary")
    public DataSource WxDatasource() {
        return DataSourceBuilder.create().build();
    }


    @Bean(name = "namedParameterJdbcTemplate")
    @Qualifier("namedParameterJdbcTemplate")
    public NamedParameterJdbcTemplate namedParameterJdbcTemplate(@Qualifier("primaryDataSource") DataSource dataSource) {
        return new NamedParameterJdbcTemplate(dataSource);
    }


    @Bean(name = "WxJdbcTemplate")
    @Qualifier("WxJdbcTemplate")
    public NamedParameterJdbcTemplate WxNamedParameterJdbcTemplate(@Qualifier("WxDatasource") DataSource dataSource) {
        return new NamedParameterJdbcTemplate(dataSource);
    }
}
