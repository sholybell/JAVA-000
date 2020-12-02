package com.holybell.homework07ShardingSphere;

import org.apache.shardingsphere.core.strategy.keygen.SnowflakeShardingKeyGenerator;
import org.apache.shardingsphere.spi.keygen.ShardingKeyGenerator;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
@MapperScan("com.holybell.homework07ShardingSphere.lesson14.q3.mapper")
public class Homework07ShardingSphereApplication {

    @Bean
    public ShardingKeyGenerator snowflakeShardingKeyGenerator(){
        return new SnowflakeShardingKeyGenerator();
    }

    public static void main(String[] args) {
        SpringApplication.run(Homework07ShardingSphereApplication.class, args);
    }

}
