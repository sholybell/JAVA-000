package com.holybell.foreignexchangea.config;

import com.zaxxer.hikari.HikariDataSource;
import org.dromara.hmily.tac.p6spy.HmilyP6Datasource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import javax.sql.DataSource;

@Configuration
public class DatabaseConfig {

    @Bean
    @Primary
    public DataSource dataSource() {
        HikariDataSource hikariDataSource = new HikariDataSource();
        hikariDataSource.setJdbcUrl("jdbc:mysql://127.0.0.1:3306/foreign-exchange-a?useUnicode=true&characterEncoding=utf8");
        hikariDataSource.setDriverClassName("com.mysql.jdbc.Driver");
        hikariDataSource.setUsername("root");
        hikariDataSource.setPassword("123456");
        hikariDataSource.setMaximumPoolSize(20);
        hikariDataSource.setMinimumIdle(10);
        hikariDataSource.setConnectionTimeout(30000);
        hikariDataSource.setIdleTimeout(600000);
        hikariDataSource.setMaxLifetime(1800000);
        return new HmilyP6Datasource(hikariDataSource);
    }
}
