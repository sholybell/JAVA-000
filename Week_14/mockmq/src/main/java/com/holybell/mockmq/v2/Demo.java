package com.holybell.mockmq.v2;

import java.io.IOException;

public class Demo {

    public static void main(String[] args) throws InterruptedException, IOException {

        String topic = "test";
        HBroker broker = new HBroker();
        broker.createTopic(topic);

        HConsumer consumer = broker.createConsumer();
        consumer.subscribe(topic);
        final boolean[] flag = new boolean[1];
        flag[0] = true;
        new Thread(() -> {
            while (flag[0]) {
                HMessage<Order> message = consumer.poll();
                if (null != message) {
                    System.out.println(message.getBody());
                }
            }
            System.out.println("程序退出。");
        }).start();

        HProducer producer = broker.createProducer();
        for (int i = 0; i < 10; i++) {
            Order order = new Order(1000L + i, System.currentTimeMillis(), "USD2CNY", 6.51d);
            producer.send(topic, new HMessage(null, order));
        }

        Thread.sleep(500);
        System.out.println("点击任何键，发送一条消息；点击q或e，退出程序。");
        while (true) {
            char c = (char) System.in.read();
            if (c > 20) {
                System.out.println(c);
                producer.send(topic, new HMessage(null, new Order(100000L + c, System.currentTimeMillis(), "USD2CNY", 6.52d)));
            }

            if (c == 'q' || c == 'e'){ break;}
        }

        flag[0] = false;

    }
}
