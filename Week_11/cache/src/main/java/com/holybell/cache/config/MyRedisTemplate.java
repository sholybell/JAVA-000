package com.holybell.cache.config;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.RedisStringCommands;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.data.redis.core.types.Expiration;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

@Configuration
public class MyRedisTemplate extends RedisTemplate<String, String> {

    private Logger logger = LoggerFactory.getLogger(MyRedisTemplate.class);

    private final StringRedisTemplate stringRedisTemplate;

    @Autowired
    public MyRedisTemplate(StringRedisTemplate stringRedisTemplate, RedisConnectionFactory connectionFactory) {
        this.stringRedisTemplate = stringRedisTemplate;
        setConnectionFactory(connectionFactory);
    }

    /**
     * 分布式锁(锁必须依靠自动过期)
     *
     * @param lockName    锁名称
     * @param lockValue   锁值
     * @param expireMills 过期时间
     */
    public boolean distributedLock(final String lockName, final String lockValue, final long expireMills) {
        return stringRedisTemplate.execute((RedisCallback<Boolean>) connection -> connection.set(lockName.getBytes(), lockValue.getBytes(), Expiration.from(expireMills, TimeUnit.MILLISECONDS), RedisStringCommands.SetOption.SET_IF_ABSENT));
    }

    // ------------------------------------------------------

    // 分布式锁前缀
    private final String LOCK_PREFIX = "DISTRIBUTED_LOCK:";
    // 获取锁成功
    private final Long LOCK_SUCCESS = 1L;
    // 释放锁成功
    private final Long RELEASE_SUCCESS = 1L;
    // 释放锁失败
    private final Long RELEASE_FAIL = -1L;
    // 释放锁时，锁过期
    private final Long LOCK_EXPIRE = 0L;

    // 分布式锁的业务键
    private final ThreadLocal<String> LOCAL_KEY = new ThreadLocal<>();
    // 使用一个唯一的字符串作为分布式锁的锁定值
    private final ThreadLocal<String> REQ_ID = new ThreadLocal<>();

    // 定义获取锁的lua脚本
    private final DefaultRedisScript<Long> LOCK_LUA_SCRIPT = new DefaultRedisScript<>(
            "if redis.call(\"setnx\", KEYS[1], KEYS[2]) == 1 then " +
                    "    return redis.call(\"expire\", KEYS[1], KEYS[3]) " +
                    "else " +
                    "    return 0 " +
                    "end"
            , Long.class
    );

    // 定义释放锁的lua脚本
    private final DefaultRedisScript<Long> UNLOCK_LUA_SCRIPT = new DefaultRedisScript<>(
            // 尝试获取KEY[1]的缓存值,判断是否的等于KEY[2],若相等，可以删除当前锁
            // 这么做的原因主要是，当前线程可能获取锁之后，锁因为超时自动过期，后面的业务又获取了分布式锁，结果被当前线程删除了锁
            "if redis.call(\"get\",KEYS[1]) == KEYS[2] then " +
                    "    return redis.call(\"del\",KEYS[1]) " +
                    "else " +
                    "    return -1 " +
                    "end"
            , Long.class
    );

    /**
     * 获取RedisKey
     *
     * @param key 原始KEY，如果为空，自动生成随机KEY
     */
    private String getRedisKey(String key) {
        // 如果Key为空且线程已经保存，直接用，异常保护
        if (StringUtils.isEmpty(key) && !StringUtils.isEmpty(LOCAL_KEY.get())) {
            return LOCAL_KEY.get();
        }
        // 如果都是空那就抛出异常
        if (StringUtils.isEmpty(key) && StringUtils.isEmpty(LOCAL_KEY.get())) {
            throw new RuntimeException("key is null");
        }
        return LOCK_PREFIX + key;
    }

    /**
     * 获取随机请求ID
     */
    private String getRequestId() {
        return UUID.randomUUID().toString();
    }

    /**
     * 加锁
     *
     * @param key        Key
     * @param expSec     过期时间(秒)
     * @param retryTimes 重试次数
     */
    public boolean lock(String key, long expSec, int retryTimes) {
        try {
            final String redisKey = getRedisKey(key);
            final String requestId = getRequestId();
            if (logger.isInfoEnabled()) {
                logger.info("分布式锁 redisKey : {} requestId : {}", redisKey, requestId);
            }
            //组装lua脚本参数
            List<String> keys = Arrays.asList(redisKey, requestId, String.valueOf(expSec));
            //执行脚本
            Long result = stringRedisTemplate.execute(LOCK_LUA_SCRIPT, keys);
            //存储本地变量
            if (LOCK_SUCCESS.equals(result)) {
                // 获取锁成功，保存当前线程相关的锁信息
                REQ_ID.set(requestId);
                LOCAL_KEY.set(redisKey);
                if (logger.isInfoEnabled()) {
                    logger.info("{} 成功获取锁，锁定值 : {}", Thread.currentThread().getName(), requestId);
                }
                return true;
            }
            //重试次数为0直接返回失败
            else if (retryTimes == 0) {
                return false;
            }
            //重试获取锁
            else {
                int count = 0;
                while (true) {
                    try {
                        //休眠一定时间后再获取锁，这里时间可以通过外部设置
                        Thread.sleep(100);
                        result = stringRedisTemplate.execute(LOCK_LUA_SCRIPT, keys);
                        if (LOCK_SUCCESS.equals(result)) {
                            REQ_ID.set(requestId);
                            LOCAL_KEY.set(redisKey);
                            if (logger.isInfoEnabled()) {
                                logger.info("{} 成功获取锁，锁定值 : {}", Thread.currentThread().getName(), requestId);
                            }
                            return true;
                        } else {
                            count++;
                            if (retryTimes == count) {
                                if (logger.isInfoEnabled()) {
                                    logger.info("{} {}次获取锁失败,不再尝试获取!", Thread.currentThread().getName(), count);
                                }
                                return false;
                            } else {
                                if (logger.isInfoEnabled()) {
                                    logger.info("{} {}次获取锁失败,将尝试再获取!", Thread.currentThread().getName(), count);
                                }
                            }
                        }
                    } catch (Exception e) {
                        logger.error("{} 获取分布式锁异常", Thread.currentThread().getName(), e);
                        break;
                    }
                }
            }
        } catch (Exception e1) {
            logger.error("{} 获取分布式锁异常", Thread.currentThread().getName(), e1);
        }
        return false;
    }


    /**
     * 释放分布式锁
     */
    public boolean unlock(String key) {
        try {
            String localKey = LOCAL_KEY.get();
            // 如果本地线程没有KEY，说明还没加锁，不能释放
            // 当前线程不能拿着一个同名的key解除另一个线程的同名锁
            if (StringUtils.isEmpty(localKey)) {
                if (logger.isInfoEnabled()) {
                    logger.info("当前线程并未获得分布式锁!");
                }
                return false;
            }
            String redisKey = getRedisKey(key);
            //判断KEY是否正确，不能释放其他线程的KEY
            if (!StringUtils.isEmpty(localKey) && !localKey.equals(redisKey)) {
                if (logger.isInfoEnabled()) {
                    logger.info("当前线程尝试释放的锁非法 持有锁 : {} 要释放的锁 : {}", localKey, key);
                }
                return false;
            }

            //组装lua脚本参数
            List<String> keys = Arrays.asList(redisKey, REQ_ID.get());
            if (logger.isInfoEnabled()) {
                logger.info("尝试释放分布式锁 锁名 : {} 锁值 : {}", redisKey, REQ_ID.get());
            }

            // 使用lua脚本删除redis中匹配value的key，可以避免由于方法执行时间过长而redis锁自动过期失效的时候误删其他线程的锁
            Long result = stringRedisTemplate.execute(UNLOCK_LUA_SCRIPT, keys);
            //如果这里抛异常，后续锁无法释放
            if (RELEASE_SUCCESS.equals(result)) {
                if (logger.isInfoEnabled()) {
                    logger.info("{} 释放分布式锁成功 锁名 : {} 锁值 : {}", Thread.currentThread().getName(), redisKey, REQ_ID.get());
                }
                return true;
            } else if (RELEASE_FAIL.equals(result)) {
                // 返回-1说明获取到的KEY值与requestId不一致或者KEY不存在，可能已经过期或被其他线程加锁
                // 一般发生在key的过期时间短于业务处理时间，属于正常可接受情况
                if (logger.isInfoEnabled()) {
                    logger.info("{} 释放分布式锁失败 当前锁值已被其他线程获取", Thread.currentThread().getName());
                }
            } else if (LOCK_EXPIRE.equals(result)) {
                // 其他情况，一般是删除KEY失败，返回0
                if (logger.isInfoEnabled()) {
                    logger.info("{} 释放分布式锁失败 锁已经过期", Thread.currentThread().getName());
                }
            }
        } catch (Exception e) {
            logger.error("release lock occured an exception", e);
        } finally {
            //清除本地变量
            clean();
        }
        return false;
    }

    /**
     * 清除本地线程变量，防止内存泄露
     */
    private void clean() {
        LOCAL_KEY.remove();
        REQ_ID.remove();
    }
}
