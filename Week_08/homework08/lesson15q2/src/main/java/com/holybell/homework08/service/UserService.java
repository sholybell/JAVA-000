package com.holybell.homework08.service;

import com.holybell.homework08.mapper.UserMapper;
import com.holybell.homework08.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

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

    public void deleteAll() {
        userMapper.deleteAll();
    }

    public Long countAll() {
        return userMapper.countAll();
    }

    public User select(long id, int age) {
        return userMapper.select(id, age);
    }

    public List<User> selectAgeRangeLimit(Integer age, Integer limit) {
        Assert.notNull(age, "年龄为空!");
        Assert.notNull(limit, "limit为空！");
        return userMapper.selectAgeRangeLimit(age, limit);
    }

    public void updateById(User user, Integer id) {
        userMapper.updateById(user, id);
    }

    public void deleteById(Integer id) {
        userMapper.deleteById( id);
    }
}
