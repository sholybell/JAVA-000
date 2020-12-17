package com.holybell.foreignexchangea;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ImportResource;

@SpringBootApplication
@ImportResource({"classpath:spring-dubbo.xml"})
@MapperScan("com.holybell.common.mapper")
public class ForeignExchangeAApplication {

    public static void main(String[] args) {
        SpringApplication.run(ForeignExchangeAApplication.class, args);
    }

}
