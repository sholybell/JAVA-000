package com.holybell.homework05.lesson09.q2;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 其实标注@Component也可以，不同的是@Configuration注解会被Spring使用GCLIG进行代理，可以避免对个@Bean方法嵌套调用导致创建多个bean
 */
@Configuration
public class CarConfiguration {

    /**
     * 默认方法名即为bean的名称
     */
    @Bean
    public Car benzCar() {
        Car benzCar = new Car();
        benzCar.setBrand("奔驰");
        benzCar.setPlateNo("闽D123456");
        return benzCar;
    }
}
