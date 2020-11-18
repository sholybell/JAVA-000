package com.holybell.homework05.lesson10.aq01.service;

import com.holybell.homework05.lesson10.aq01.annotations.MyCache;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.IntStream;

@Service
public class MyService {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    // 模拟数据库
    private Map<Integer, String> dataBase = new HashMap<>();

    /**
     * 准备数据
     */
    @PostConstruct
    public void prepareData() {
        IntStream.rangeClosed(1, 10).forEach((num) -> {
            dataBase.put(num, "username" + num);
        });
    }

    /**
     * 模拟从数据库查询数据
     *
     * @param userId 用户ID
     */
    private String getUsernameFromDatabase(int userId) {
        String username = dataBase.get(userId);
        if (logger.isInfoEnabled()) {
            logger.info("查询数据库 userId : {} username : {}", userId, username);
        }
        return username;
    }

    /**
     * 根据用户ID查询用户昵称
     *
     * @param userId 用户ID
     * @return 用户昵称
     */
    @MyCache(120)
    public String getUsername(int userId) {
        return getUsernameFromDatabase(userId);
    }
}
