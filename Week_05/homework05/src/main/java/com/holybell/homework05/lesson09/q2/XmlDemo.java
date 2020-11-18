package com.holybell.homework05.lesson09.q2;

import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * 通过XML配置的方式，装配bean
 */
public class XmlDemo {

    public static void main(String[] args) {
        // XML配置方式
        ConfigurableApplicationContext applicationContext = new ClassPathXmlApplicationContext("classpath:META-INF/applicationContext.xml");
        Car hondaCar = applicationContext.getBean("hondaCar", Car.class);
        System.out.println(hondaCar);
        applicationContext.close();
    }
}
