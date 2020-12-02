package com.holybell.homework07.lesson14.q2.controller;

import com.holybell.homework07.lesson14.q2.service.MockService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MockController {

    @Autowired
    private MockService mockService;

    @GetMapping("queryFromMaster")
    public void getFromMaster() {
        mockService.queryFromMaster();
    }

    @GetMapping("queryFromSlave")
    public void queryFromSlave() {
        mockService.queryFromSlave();
    }
}
