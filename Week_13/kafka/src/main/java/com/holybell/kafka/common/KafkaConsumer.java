package com.holybell.kafka.common;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.annotation.PartitionOffset;
import org.springframework.kafka.annotation.TopicPartition;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Component;

@Component
public class KafkaConsumer {

    private Logger logger = LoggerFactory.getLogger(KafkaConsumer.class);

    // 消费监听
    @KafkaListener(topics = {"holybell"} /*,errorHandler = "consumerAwareErrorHandler"*/)
    public void onMessage1(ConsumerRecord<?, ?> record) throws Exception {
        // 消费的哪个topic、partition的消息,打印出消息内容
        if (logger.isInfoEnabled()) {
            logger.info("consumer1 消费消息 主题 : {} 分区 : {} 内容 : {}", record.topic(), record.partition(), record.value());
        }
//        throw new Exception("模拟消费异常!");
    }


    @KafkaListener(id = "consumer2",    // 消费者ID
            groupId = "test-group-01",     // 消费者组ID
            topicPartitions = {
                    @TopicPartition(topic = "holybell", // 监听的主题
                            partitions = {"0"},         // 处理的分区
                            partitionOffsets = @PartitionOffset(partition = "1", initialOffset = "8")), // 监听分区1从偏移量8开始的消息
            }
            /*,errorHandler = "consumerAwareErrorHandler"*/)
    public void onMessage2(ConsumerRecord<?, ?> record) {
        if (logger.isInfoEnabled()) {
            logger.info("consumer2 消费消息 主题 : {} 分区 : {} 内容 : {}", record.topic(), record.partition(), record.value());
        }
    }


//    @KafkaListener(id = "consumer3", groupId = "test-group-02", topics = "holybell")
//    public void onMessage3(List<ConsumerRecord<?, ?>> records) {
//        if (logger.isInfoEnabled()) {
//            logger.info("consumer3 批量消费1次，消息数量 : {}", records.size());
//        }
//        for (ConsumerRecord<?, ?> record : records) {
//            if (logger.isInfoEnabled()) {
//                logger.info("consumer3 消费内容 : {}", record.value());
//            }
//        }
//    }

    /**
     * 过滤部分消息不处理
     */
    @KafkaListener(topics = {"holybell"},
            groupId = "test-group-03",     // 消费者组ID
            containerFactory = "filterContainerFactory")
    public void onMessage4(ConsumerRecord<?, ?> record) {
        if (logger.isInfoEnabled()) {
            logger.info("consumer4 消费消息 主题 : {} 分区 : {} 内容 : {}", record.topic(), record.partition(), record.value());
        }
    }

    /**
     * 转发消息到死信队列
     */
    @KafkaListener(topics = {"holybell"},
            groupId = "test-group-04"     // 消费者组ID
    )
    @SendTo("deadQueue")
    public String onMessage5(ConsumerRecord<?, ?> record) {
        return record.value() + "- forward message!";
    }

    /**
     * 消费死信队列
     */
    @KafkaListener(topics = "deadQueue")
    public void consumeDeadQueue(ConsumerRecord<?, ?> record) {
        if (logger.isInfoEnabled()) {
            logger.info("死信队列 消费消息 主题 : {} 分区 : {} 内容 : {}", record.topic(), record.partition(), record.value());
        }
    }
}
