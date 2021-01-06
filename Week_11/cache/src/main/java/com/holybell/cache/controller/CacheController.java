package com.holybell.cache.controller;

import com.holybell.cache.config.MyRedisTemplate;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionEvaluationReport;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class CacheController {

    private Logger logger = LoggerFactory.getLogger(CacheController.class);

    @Autowired
    private MyRedisTemplate stringRedisTemplate;
    @Autowired
    private ConditionEvaluationReport autoConfigurationReport;

    /**
     * 打印SpringBoot自动配置的匹配结果，不匹配的原因
     *
     * @param clazz 匹配的类名
     */
    @GetMapping("printAutoConfigResult")
    public void print(@RequestParam(value = "clazz", required = false) String clazz) {
        Map<String, ConditionEvaluationReport.ConditionAndOutcomes> conditionAndOutcomesBySource = autoConfigurationReport.getConditionAndOutcomesBySource();
        conditionAndOutcomesBySource.forEach((key, value) -> {
            if (StringUtils.isNotEmpty(clazz)) {
                if (key.toLowerCase().contains(clazz)) {
                    logger.info("类: {} ", key);
                    for (ConditionEvaluationReport.ConditionAndOutcome outcome : value) {
                        logger.info("结果: {} {}", outcome.getOutcome().isMatch(), outcome.getOutcome().getMessage());
                    }
                    logger.info("-------------------------------------------------->");
                }
            } else {
                logger.info("类: {} ", key);
                for (ConditionEvaluationReport.ConditionAndOutcome outcome : value) {
                    logger.info("结果: {} {}", outcome.getOutcome().isMatch(), outcome.getOutcome().getMessage());
                }
                logger.info("-------------------------------------------------->");
            }
        });
    }


    /**
     * 分布式锁1(这种方式无法在一个原子操作释放分布式锁)
     */
    @GetMapping("lock1")
    public void lock1() {
        boolean lock = stringRedisTemplate.distributedLock("lock1", "lockvalue1", 10000);
        if (lock) {
            logger.info("成功获取分布式锁!");
        } else {
            logger.info("获取分布式锁失败!");
        }
    }

    /**
     * 分布式锁2，原子操作删除锁
     */
    @GetMapping("lock2")
    public void lock2() {
        stringRedisTemplate.lock("test", 60L, 3);
        stringRedisTemplate.unlock("test");
    }

    /**
     * 原子性扣减库存
     */
    @GetMapping("decreaseStock")
    public boolean decreaseStock() {
        Long remain = stringRedisTemplate.opsForValue().decrement("XXXX", 1L);
        if (remain < 0) {
            return false;   // 已经没有库存
        }
        return true;        // 扣减库存成功
    }

}
