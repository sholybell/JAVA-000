package com.holybell.homework07.lesson14.q2.config;

import com.holybell.homework07.lesson14.q2.loadbalancer.IDynamicDataSourceStrategy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

public class MultiDataSource extends AbstractRoutingDataSource {

    // 记录是否
    public static final ThreadLocal<Boolean> lookupKeyCache = new ThreadLocal<>();

    @Autowired
    private IDynamicDataSourceStrategy strategy;

    @Override
    protected Object determineCurrentLookupKey() {
        if (lookupKeyCache.get() != null && lookupKeyCache.get()) {
            return strategy.getSlaveDatabaseName();
        }
        return "master";
    }
}