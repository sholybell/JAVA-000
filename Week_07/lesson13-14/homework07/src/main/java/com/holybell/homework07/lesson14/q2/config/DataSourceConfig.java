package com.holybell.homework07.lesson14.q2.config;

import com.holybell.homework07.lesson14.q2.loadbalancer.IDynamicDataSourceStrategy;
import com.holybell.homework07.lesson14.q2.loadbalancer.RandomStrategy;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Configuration
public class DataSourceConfig {

    @Bean
    public static Map<Object, Object> targetDataSources() {
        Map<Object, Object> dataSourceMap = new HashMap<>();
        HikariConfig config = new HikariConfig();
        config.setMinimumIdle(5);
        config.setMaximumPoolSize(15);
        config.setAutoCommit(true);
        config.setIdleTimeout(3000);
        config.setPoolName("HikariCPPool");
        config.setMaxLifetime(1800000);
        config.setConnectionTimeout(30000);
        config.setConnectionTestQuery("SELECT 1");
        config.setJdbcUrl("jdbc:mysql://localhost:3306/bluestore_core_db?characterEncoding=UTF8&serverTimezone=UTC");
        config.setUsername("root");
        config.setPassword("123456");

        dataSourceMap.put("master", new HikariDataSource(config));
        // 模拟多个从库
        dataSourceMap.put("slave1", new HikariDataSource(config));
        dataSourceMap.put("slave2", new HikariDataSource(config));
        dataSourceMap.put("slave3", new HikariDataSource(config));
        return dataSourceMap;
    }

    @Bean
    public AbstractRoutingDataSource routingDataSource() {
        MultiDataSource dataSource = new MultiDataSource();
        dataSource.setTargetDataSources(targetDataSources());
        return dataSource;
    }

    @Bean
    public IDynamicDataSourceStrategy strategy() {
        List<String> collect = targetDataSources().keySet().stream().map(Object::toString).collect(Collectors.toList());
        collect.remove("master");   // 主库不参与负载均衡
        return new RandomStrategy(collect);
    }

}
