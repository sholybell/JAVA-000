package com.holybell.homework07.lesson14.q2.loadbalancer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public class RandomStrategy implements IDynamicDataSourceStrategy {

    private Logger logger = LoggerFactory.getLogger(RandomStrategy.class);

    private List<String> slaveDatabaseNameList;

    public List<String> getSlaveDatabaseNameList() {
        return slaveDatabaseNameList;
    }

    public void setSlaveDatabaseNameList(List<String> slaveDatabaseNameList) {
        this.slaveDatabaseNameList = slaveDatabaseNameList;
    }

    public RandomStrategy() {

    }

    public RandomStrategy(List<String> slaveDatabaseNameList) {
        this.slaveDatabaseNameList = slaveDatabaseNameList;
    }

    @Override
    public String getSlaveDatabaseName() {
        if (slaveDatabaseNameList == null || slaveDatabaseNameList.size() == 0) {
            throw new RuntimeException("从库名称配置列表配置异常!");
        }
        int randNum = ThreadLocalRandom.current().nextInt(0, slaveDatabaseNameList.size());
        String slaveName = slaveDatabaseNameList.get(randNum);
        if (logger.isInfoEnabled()) {
            logger.info("本次策略获取的从库名称 : {}", slaveName);
        }
        return slaveName;
    }
}
