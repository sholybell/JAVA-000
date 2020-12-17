package com.holybell.foreignexchangea.config;

import org.dromara.hmily.spring.HmilyApplicationContextAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@Configuration
@EnableAspectJAutoProxy(exposeProxy = true)
public class HmilyConfig {

    @Bean
    public HmilyApplicationContextAware hmilyApplicationContextAware() {
        return new HmilyApplicationContextAware();
    }
}
