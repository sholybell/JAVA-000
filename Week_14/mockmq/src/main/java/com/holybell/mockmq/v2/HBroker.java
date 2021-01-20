package com.holybell.mockmq.v2;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class HBroker {

    private final Map<String, HQueue> queueMap = new ConcurrentHashMap<>(64);

    public void createTopic(String name) {
        queueMap.putIfAbsent(name, new HQueue<String>());
    }

    public HQueue findQueue(String topic) {
        return this.queueMap.get(topic);
    }

    public HProducer createProducer() {
        return new HProducer(this);
    }

    public HConsumer createConsumer() {
        return new HConsumer(this);
    }
}
