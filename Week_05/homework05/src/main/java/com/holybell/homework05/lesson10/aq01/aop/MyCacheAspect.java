package com.holybell.homework05.lesson10.aq01.aop;


import com.holybell.homework05.lesson10.aq01.annotations.MyCache;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.*;

/**
 * 拦截目标方法的缓存设置缓存
 */
@Aspect
@Component
public class MyCacheAspect {

    private Logger logger = LoggerFactory.getLogger(MyCacheAspect.class);

    // 模拟缓存
    private Map<String, CacheValue> cache = new ConcurrentHashMap<>();

    // 定时器，每秒一次删除过期的缓存
    @PostConstruct
    public void initJob() {

        Executors.newSingleThreadScheduledExecutor().scheduleAtFixedRate(() -> {
            if (logger.isInfoEnabled()) {
                logger.info("执行删除缓存任务");
            }
            cache.forEach((k, v) -> {
                if (v.getExpireTime() < System.currentTimeMillis()) {
                    if (logger.isInfoEnabled()) {
                        logger.info("删除过期key : {}", k);
                    }
                    cache.remove(k);
                }
            });
        }, 1, 1, TimeUnit.SECONDS);
    }

    /**
     * 切点表达式
     */
    @Pointcut("@annotation(com.holybell.homework05.lesson10.aq01.annotations.MyCache)")
    public void point() {
    }

    /**
     * 拦截目标方法如果有缓存值返回缓存值
     *
     * @param joinPoint 连接点
     * @throws Throwable
     */
    @Around("point()")
    public Object around(ProceedingJoinPoint joinPoint) throws Throwable {
//        // 获取目标方法的名称
//        String methodName = joinPoint.getSignature().getName();
        // 获取方法传入参数
        Object[] params = joinPoint.getArgs();
        String key = joinPoint.getSignature().toString() + "#" + Arrays.toString(params);
        CacheValue cacheValue = cache.get(key);
        // 找到缓存，直接返回
        if (cacheValue != null) {
            return cacheValue.getValue();
        }
        // 未找到缓存，执行目标方法，并加入缓存
        Object result = joinPoint.proceed();
        MyCache myCache = getMyCache(joinPoint);
        cacheValue = new CacheValue(result, System.currentTimeMillis() + myCache.value() * 1000L);
        cache.put(key, cacheValue);
        return result;
    }

    /**
     * 获取方法上面的注解对象
     *
     * @param joinPoint 连接点
     * @return
     * @throws NoSuchMethodException
     */
    private MyCache getMyCache(ProceedingJoinPoint joinPoint) throws NoSuchMethodException {
        // 获取方法名
        String methodName = joinPoint.getSignature().getName();
        // 反射获取目标类
        Class<?> targetClass = joinPoint.getTarget().getClass();
        // 拿到方法对应的参数类型
        Class<?>[] parameterTypes = ((MethodSignature) joinPoint.getSignature()).getParameterTypes();
        // 根据类、方法、参数类型（重载）获取到方法的具体信息
        Method objMethod = targetClass.getMethod(methodName, parameterTypes);
        // 拿到方法定义的注解信息
        MyCache annotation = objMethod.getDeclaredAnnotation(MyCache.class);
        // 返回
        return annotation;
    }

    /**
     * 获取目标方法返回类型
     *
     * @param joinPoint 连接点
     * @return
     * @throws NoSuchMethodException
     */
    private Class<?> getReturnClass(ProceedingJoinPoint joinPoint) throws NoSuchMethodException {
        // 获取方法名
        String methodName = joinPoint.getSignature().getName();
        // 反射获取目标类
        Class<?> targetClass = joinPoint.getTarget().getClass();
        // 拿到方法对应的参数类型
        Class<?>[] parameterTypes = ((MethodSignature) joinPoint.getSignature()).getParameterTypes();
        // 根据类、方法、参数类型（重载）获取到方法的具体信息
        Method objMethod = targetClass.getMethod(methodName, parameterTypes);
        return objMethod.getReturnType();
    }

    static class CacheValue {
        // 缓存值
        private Object value;
        // 过期时间,毫秒
        private long expireTime;

        public Object getValue() {
            return value;
        }

        public void setValue(Object value) {
            this.value = value;
        }

        public long getExpireTime() {
            return expireTime;
        }

        public void setExpireTime(long expireTime) {
            this.expireTime = expireTime;
        }

        public CacheValue(Object cache, long expireTime) {
            this.value = cache;
            this.expireTime = expireTime;
        }
    }

    public static void main(String[] args) {
        System.out.println(System.currentTimeMillis());
    }
}
