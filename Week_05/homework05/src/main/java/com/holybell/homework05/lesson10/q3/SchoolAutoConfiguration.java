package com.holybell.homework05.lesson10.q3;

import com.holybell.homework05.lesson09.q1.StudentProxy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties(StudentsProperties.class)
public class SchoolAutoConfiguration {

    @Autowired
    private StudentsProperties studentsProperties;

    @Bean
    public School school() {
        School school = new School();
        Klass klass = new Klass();
        klass.setStudents(studentsProperties.getStudents());
        school.setClass1(klass);
        school.setStudent100(studentsProperties.getStudents().get(0));
        return school;
    }
}
