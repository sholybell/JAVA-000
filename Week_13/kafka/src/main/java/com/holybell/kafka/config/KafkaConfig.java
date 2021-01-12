package com.holybell.kafka.config;

import org.apache.kafka.clients.admin.NewTopic;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.listener.ConsumerAwareListenerErrorHandler;

@Configuration
public class KafkaConfig {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private ConsumerFactory consumerFactory;

    /**
     * 创建一个名为holybell的topic，分区数为3，副本为2
     */
    @Bean
    public NewTopic holybell() {
        return new NewTopic("holybell", 3, (short) 2);
    }

    /**
     * 定义死信队列
     */
    @Bean
    public NewTopic deadQueue() {
        return new NewTopic("deadQueue", 3, (short) 2);
    }

    // 如果要修改分区数，只需修改配置值重启项目即可
    // 修改分区数并不会导致数据的丢失，但是分区数只能增大不能减小
//    @Bean
//    public NewTopic updateTopic() {
//        return new NewTopic("holybell",10, (short) 2 );
//    }


    // 新建一个异常处理器，用@Bean注入
    @Bean
    public ConsumerAwareListenerErrorHandler consumerAwareErrorHandler() {
        return (message, exception, consumer) -> {
            if (logger.isInfoEnabled()) {
                logger.info("消费异常 消息内容 : {}", message.getPayload());
            }
            return null;
        };
    }

    @Bean
    public ConcurrentKafkaListenerContainerFactory filterContainerFactory() {
        // 消息过滤器
        ConcurrentKafkaListenerContainerFactory factory = new ConcurrentKafkaListenerContainerFactory();
        factory.setConsumerFactory(consumerFactory);
        // 被过滤的消息将被丢弃
        factory.setAckDiscarded(true);
        // 消息过滤策略
        factory.setRecordFilterStrategy(consumerRecord -> {
            if (consumerRecord.offset() % 2 == 0) {
                return false;
            }
            //返回true消息则被过滤
            return true;
//            return false;
        });
        return factory;
    }
}
