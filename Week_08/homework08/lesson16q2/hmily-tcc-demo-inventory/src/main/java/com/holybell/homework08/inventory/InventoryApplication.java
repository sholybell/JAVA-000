
package com.holybell.homework08.inventory;

import org.dromara.hmily.spring.HmilyApplicationContextAware;
import org.dromara.hmily.spring.aop.SpringHmilyTransactionAspect;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.data.mongo.MongoDataAutoConfiguration;
import org.springframework.boot.autoconfigure.mongo.MongoAutoConfiguration;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication(exclude = {MongoAutoConfiguration.class, MongoDataAutoConfiguration.class})
@EnableEurekaClient
@EnableFeignClients
@MapperScan("com.holybell.homework08.inventory.mapper")
@EnableAspectJAutoProxy(exposeProxy = true)
@EnableTransactionManagement
public class InventoryApplication {

    public static void main(final String[] args) {
        SpringApplication.run(InventoryApplication.class, args);
    }

    @Bean
    public SpringHmilyTransactionAspect hmilyTransactionAspect() {
        return new SpringHmilyTransactionAspect();
    }

    @Bean
    public HmilyApplicationContextAware hmilyApplicationContextAware() {
        return new HmilyApplicationContextAware();
    }
}
