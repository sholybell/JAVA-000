package com.holybell.homework08.controller;

import com.alibaba.fastjson.JSONObject;
import com.holybell.homework08.model.User;
import com.holybell.homework08.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.atomic.AtomicLong;

@RestController
@RequestMapping(value = "/user")
public class UserController {

    private Logger logger = LoggerFactory.getLogger(UserController.class);

    @Autowired
    UserService userService;

    private ExecutorService es = Executors.newFixedThreadPool(10);

    /**
     * 批量新增，准备数据
     *
     * @param cnt 添加的最大ID
     */
    @PostMapping("batchAdd")
    public void batchAdd(@RequestParam("cnt") Integer cnt) {
        AtomicLong al = new AtomicLong(cnt);
//        userService.deleteAll();          // 删除
        for (int i = 0; i < cnt; i++) {     // 批量添加记录
            String sex = (i % 2 == 0) ? "男" : "女";
            User user = new User("userName" + i, sex, ThreadLocalRandom.current().nextInt(1, 100));
            es.execute(() -> {
                userService.add(user);
                if (logger.isInfoEnabled()) {
                    logger.info("批量插入记录，剩余 : {}", al.decrementAndGet());
                }
            });
        }
    }

    /**
     * 统计记录数
     */
    @GetMapping("/countAll")
    public Long countAll() {
        return userService.countAll();
    }

    /**
     * 查询
     */
    @GetMapping("/select")
    public void select() {
        User user = userService.select(32L, 54);
        logger.info("精确查询 : {}", JSONObject.toJSONString(user));

        List<User> users = userService.selectAgeRangeLimit(89, 100);
        logger.info("范围查询 : {}", users.size());
    }

    @PostMapping("/update")
    public void update() {
        User user = new User();
        user.setAge(11);
        user.setCreateTime(new Date());
        user.setName("test");
        user.setSex("F");
        user.setUpdateTime(new Date());
        user.setStatus(0);
        userService.updateById(user, 1111);
    }

    @PostMapping("/delete")
    public void delete(@RequestParam("id") Integer id) {
        userService.deleteById(id);
    }
}