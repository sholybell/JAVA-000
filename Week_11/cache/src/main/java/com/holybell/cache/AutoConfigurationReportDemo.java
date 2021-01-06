package com.holybell.cache;

import org.springframework.boot.autoconfigure.condition.ConditionEvaluationReport;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.util.Map;

public class AutoConfigurationReportDemo {

    public static void main(String[] args) {
        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext();
        applicationContext.scan("com.holybell");
        applicationContext.refresh();

        ConditionEvaluationReport report = applicationContext.getBean("autoConfigurationReport", ConditionEvaluationReport.class);
        Map<String, ConditionEvaluationReport.ConditionAndOutcomes> conditionAndOutcomesBySource = report.getConditionAndOutcomesBySource();

        // 以获取StringRedisTemplate类的装配结果为例
        conditionAndOutcomesBySource.forEach((key, value) -> {
            if (key.endsWith("stringRedisTemplate")) {
                System.out.println("类: " + key);
                for (ConditionEvaluationReport.ConditionAndOutcome outcome : value) {
                    System.out.println("条件装配结果 : " + outcome.getOutcome().isMatch() + " ,原因: " + outcome.getOutcome().getMessage());
                }
            }
        });

        applicationContext.close();
    }
}
