package com.holybell.homework07.lesson14.q2.loadbalancer;

public interface IDynamicDataSourceStrategy {

    /**
     * 获取从库的策略
     */
    String getSlaveDatabaseName();
}
