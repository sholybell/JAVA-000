package com.holybell.mockmq.v2;

public class HProducer {

    private HBroker broker;

    public HProducer(HBroker broker) {
        this.broker = broker;
    }

    public boolean send(String topic, HMessage message) {
        HQueue queue = this.broker.findQueue(topic);
        if (null == queue) {
            throw new RuntimeException("Topic[" + topic + "] doesn't exist.");
        }
        return queue.produce(message, 1000);
    }
}
