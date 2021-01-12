package com.holybell.activemq;

import com.holybell.activemq.common.Producer;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ActiveMqTests {

    @Autowired
    private Producer producer;

    /**
     * 基于Queue实现
     */
    @Test
    public void sendSimpleQueueMessage() {
        this.producer.sendMsg("提现200.00元");
    }

    /**
     * 基于Topic实现
     */
    @Test
    public void sendSimpleTopicMessage() {
        this.producer.sendTopic("提现200.00元");
    }
}
