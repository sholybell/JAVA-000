package com.holybell.homework08;

import org.apache.shardingsphere.core.strategy.keygen.SnowflakeShardingKeyGenerator;
import org.apache.shardingsphere.spi.keygen.ShardingKeyGenerator;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
@MapperScan("com.holybell.homework08.lesson15.q2.mapper")
public class Lesson15Qustion02Application {

    @Bean
    public ShardingKeyGenerator snowflakeShardingKeyGenerator() {
        return new SnowflakeShardingKeyGenerator();
    }

    public static void main(String[] args) {
        SpringApplication.run(Lesson15Qustion02Application.class, args);
    }

}
