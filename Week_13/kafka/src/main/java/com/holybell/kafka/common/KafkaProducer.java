package com.holybell.kafka.common;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Component;
import org.springframework.util.concurrent.ListenableFutureCallback;

@Component
public class KafkaProducer {

    private Logger logger = LoggerFactory.getLogger(KafkaProducer.class);

    @Autowired
    private KafkaTemplate<String, Object> kafkaTemplate;

    /**
     * 发送一条普通消息
     */
    public void sendMsg(String msg) {
        kafkaTemplate.send("holybell", msg);
    }

    /**
     * 发送一条带回调处理的消息1
     */
    public void sendCbMsg1(String cbMsg) {
        kafkaTemplate.send("holybell", cbMsg).addCallback(success -> {
            // 消息发送到的topic
            String topic = success.getRecordMetadata().topic();
            // 消息发送到的分区
            int partition = success.getRecordMetadata().partition();
            // 消息在分区内的offset
            long offset = success.getRecordMetadata().offset();
            if (logger.isInfoEnabled()) {
                logger.info("发送消息 成功 主题 : {} 分区 : {} 偏移量 : {}", topic, partition, offset);
            }
        }, failure -> {
            if (logger.isInfoEnabled()) {
                logger.info("发送消息 失败 原因 : {}", failure.getMessage());
            }
        });
    }

    /**
     * 发送一条带回调处理的消息2
     */
    public void sendCbMsg2(String cbMsg) {
        kafkaTemplate.send("holybell", cbMsg).addCallback(new ListenableFutureCallback<SendResult<String, Object>>() {
            @Override
            public void onFailure(Throwable ex) {
                if (logger.isInfoEnabled()) {
                    logger.info("发送消息 失败 原因 : {}", ex.getMessage());
                }
            }

            @Override
            public void onSuccess(SendResult<String, Object> result) {
                if (logger.isInfoEnabled()) {
                    logger.info("发送消息 成功 主题 : {} 分区 : {} 偏移量 : {}", result.getRecordMetadata().topic(), result.getRecordMetadata().partition(), result.getRecordMetadata().offset());
                }
            }
        });
    }

    public void sendMsgTx(String msg, boolean ex) {
        kafkaTemplate.executeInTransaction(operations -> {
            operations.send("holybell", msg);
            if (ex) {
                throw new RuntimeException("fail");
            }
            return null;
        });
    }
}