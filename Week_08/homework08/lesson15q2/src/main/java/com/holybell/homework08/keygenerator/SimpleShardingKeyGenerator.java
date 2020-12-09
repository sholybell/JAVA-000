package com.holybell.homework08.keygenerator;

import org.apache.shardingsphere.spi.keygen.ShardingKeyGenerator;

import java.util.Properties;
import java.util.concurrent.atomic.AtomicLong;

public class SimpleShardingKeyGenerator  implements ShardingKeyGenerator {

    private AtomicLong atomic = new AtomicLong(0);

    @Override
    public Comparable<?> generateKey() {
        return atomic.incrementAndGet();
    }

    @Override
    public String getType() {
        //声明类型
        return "SIMPLE";
    }

    @Override
    public Properties getProperties() {
        return null;
    }

    @Override
    public void setProperties(Properties properties) {

    }

}
