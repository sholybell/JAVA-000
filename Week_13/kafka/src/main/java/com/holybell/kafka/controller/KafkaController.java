package com.holybell.kafka.controller;

import com.holybell.kafka.common.KafkaProducer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class KafkaController {

    @Autowired
    private KafkaProducer producer;

    @GetMapping("sendMsg")
    public void sendMsg1(@RequestParam("msg") String msg) {
        producer.sendMsg(msg);
    }

    @GetMapping("sendCbMsg1")
    public void sendCbMsg1(@RequestParam("msg") String msg) {
        producer.sendCbMsg1(msg);
    }

    @GetMapping("sendCbMsg2")
    public void sendCbMsg2(@RequestParam("msg") String msg) {
        producer.sendCbMsg2(msg);
    }

    @GetMapping("sendTxMsg")
    public void sendTxMsg(@RequestParam("msg") String msg) {
        producer.sendMsgTx(msg, true);
    }
}
