package com.holybell.homework07ShardingSphere.lesson14.q3.service;

import com.holybell.homework07ShardingSphere.lesson14.q3.mapper.UserMapper;
import com.holybell.homework07ShardingSphere.lesson14.q3.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    @Autowired
    UserMapper userMapper;

    public Integer add(User user) {
        return userMapper.insert(user);
    }

    public List<User> selectAll() {
        return userMapper.selectAll();
    }
}
