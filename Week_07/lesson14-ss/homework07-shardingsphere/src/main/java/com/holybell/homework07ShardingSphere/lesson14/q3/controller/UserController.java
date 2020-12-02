package com.holybell.homework07ShardingSphere.lesson14.q3.controller;

import com.google.common.base.Preconditions;
import com.holybell.homework07ShardingSphere.lesson14.q3.model.User;
import com.holybell.homework07ShardingSphere.lesson14.q3.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value = "/user")
public class UserController {

    @Autowired
    UserService userService;

    @PostMapping("/add")
    public Integer insertUser(String username, String sex, Integer age) {
        Preconditions.checkNotNull(username, "username is need.");
        Preconditions.checkNotNull(sex, "sex is need.");
        Preconditions.checkNotNull(age, "age is need.");
        User user = new User(username, sex, age);
        return userService.add(user);
    }

    @GetMapping("/select")
    public List<User> selectAll() {
        return userService.selectAll();
    }
}