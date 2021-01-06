package com.holybell.cache;

import com.holybell.cache.config.MyRedisTemplate;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = {CacheApplication.class})
public class LuaLockTest {

    private final Logger logger = LoggerFactory.getLogger(LuaLockTest.class);

    @Autowired
    private MyRedisTemplate redisTemplate;

    @Test
    public void testRedisLock() throws InterruptedException {
        ArrayList<Thread> list = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            //logger.info("线程开始");
            Thread t = new Thread() {
                @Override
                public void run() {
                    if (redisTemplate.lock("suaner", 5L, 1)) {
                        try {
                            //成功获取锁
                            logger.info("获取锁成功，继续执行任务" + Thread.currentThread().getName());
                            try {
                                Thread.sleep(10);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        } catch (Exception e) {
                            logger.error("excepiotn ", e);
                        } finally {
                            redisTemplate.unlock("suaner");
                        }
                    }
                }
            };
            list.add(t);
            t.start();
        }
        for (Thread t : list) {
            t.join();
        }
        Thread.sleep(10000);
    }
}
