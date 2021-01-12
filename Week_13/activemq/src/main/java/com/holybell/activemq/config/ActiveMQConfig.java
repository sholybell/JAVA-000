package com.holybell.activemq.config;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.activemq.command.ActiveMQQueue;
import org.apache.activemq.command.ActiveMQTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jms.config.DefaultJmsListenerContainerFactory;
import org.springframework.jms.config.JmsListenerContainerFactory;

import javax.jms.ConnectionFactory;
import javax.jms.JMSConnectionFactory;
import javax.jms.Queue;
import javax.jms.Topic;

@Configuration
public class ActiveMQConfig {

    @Bean
    public Queue queue() {
        return new ActiveMQQueue("sms.queue");
    }

    @Bean
    public Topic topic() {
        return new ActiveMQTopic("sms.topic");
    }

    @Bean("queueListenerFactory")
    public JmsListenerContainerFactory<?> queueListenerFactory(ConnectionFactory connectionFactory) {
        DefaultJmsListenerContainerFactory factory = new DefaultJmsListenerContainerFactory();
        factory.setConnectionFactory(connectionFactory);
        factory.setPubSubDomain(false);
        return factory;
    }

    @Bean("topicListenerFactory")
    public JmsListenerContainerFactory<?> topicListenerFactory(ConnectionFactory connectionFactory) {
        DefaultJmsListenerContainerFactory factory = new DefaultJmsListenerContainerFactory();
        factory.setConnectionFactory(connectionFactory);
        //设置为发布订阅方式, 默认情况下使用的生产消费者方式
        factory.setPubSubDomain(true);
        return factory;
    }
}
