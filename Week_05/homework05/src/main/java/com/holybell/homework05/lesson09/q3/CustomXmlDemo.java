package com.holybell.homework05.lesson09.q3;

import org.springframework.context.support.ClassPathXmlApplicationContext;

public class CustomXmlDemo {

    public static void main(String[] args) {
        ClassPathXmlApplicationContext applicationContext = new ClassPathXmlApplicationContext("classpath:META-INF/schoolApplicationContenxt.xml");
        School school = applicationContext.getBean( School.class);
        System.out.println(school);
        applicationContext.close();
    }
}
