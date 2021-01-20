package com.holybell.mockmq.v2;

public class HConsumer<T> {

    private final HBroker broker;

    private HQueue queue;

    public HConsumer(HBroker broker) {
        this.broker = broker;
    }

    public void subscribe(String topic) {
        this.queue = this.broker.findQueue(topic);
        if (null == queue) throw new RuntimeException("Topic[" + topic + "] doesn't exist.");
    }

    public HMessage<T> poll() {
        return queue.consume();
    }

}
