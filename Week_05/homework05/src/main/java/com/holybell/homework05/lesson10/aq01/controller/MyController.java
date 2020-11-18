package com.holybell.homework05.lesson10.aq01.controller;

import com.holybell.homework05.lesson10.aq01.service.MyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * 控制层
 */
@RestController
public class MyController {

    @Autowired
    private MyService myService;

    @GetMapping("getUsername")
    public String getData(@RequestParam("userId") int userId) {
        return myService.getUsername(userId);
    }
}
