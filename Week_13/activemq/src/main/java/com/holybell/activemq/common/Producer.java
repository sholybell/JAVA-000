package com.holybell.activemq.common;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsMessagingTemplate;
import org.springframework.stereotype.Component;

import javax.jms.Queue;
import javax.jms.Topic;

@Component
public class Producer {

    @Autowired
    private JmsMessagingTemplate jmsMessagingTemplate;

    @Autowired
    private Queue queue;

    public void sendMsg(String msg) {
        System.out.println("发送消息内容 :" + msg);
        this.jmsMessagingTemplate.convertAndSend(this.queue, msg);
    }

    @Autowired
    private Topic topic;

    public void sendTopic(String msg) {
        System.out.println("发送Topic消息内容 :"+msg);
        this.jmsMessagingTemplate.convertAndSend(this.topic, msg);
    }
}
