package com.holybell.homework07.lesson14.q2.aop;


import com.holybell.homework07.lesson14.q2.config.MultiDataSource;
import com.holybell.homework07.lesson14.q2.annotations.ReadOnly;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;

@Aspect
@Component
public class DynamicDataSourceAspect {

    private Logger logger = LoggerFactory.getLogger(DynamicDataSourceAspect.class);

    /**
     * 切点表达式
     */
    @Pointcut("@annotation(com.holybell.homework07.lesson14.q2.annotations.ReadOnly)")
    public void point() {
    }

    /**
     * 拦截目标方法如果有缓存值返回缓存值
     *
     * @param joinPoint 连接点
     * @throws Throwable
     */
    @Around("point()")
    public void around(ProceedingJoinPoint joinPoint) throws Throwable {
        if (logger.isInfoEnabled()) {
            logger.info("进入环绕切面....");
        }
        ReadOnly readOnly = getReadOnly(joinPoint);
        // 找到缓存，直接返回
        if (readOnly != null) {
            MultiDataSource.lookupKeyCache.set(true);       // 指定从库操作
        }
        joinPoint.proceed();
        MultiDataSource.lookupKeyCache.set(true);           // 切换为默认主库操作
    }

    /*
     * 获取方法上面的注解对象
     *
     * @param joinPoint 连接点
     * @return
     * @throws NoSuchMethodException
     */
    private ReadOnly getReadOnly(ProceedingJoinPoint joinPoint) throws NoSuchMethodException {
        // 获取方法名
        String methodName = joinPoint.getSignature().getName();
        // 反射获取目标类
        Class<?> targetClass = joinPoint.getTarget().getClass();
        // 拿到方法对应的参数类型
        Class<?>[] parameterTypes = ((MethodSignature) joinPoint.getSignature()).getParameterTypes();
        // 根据类、方法、参数类型（重载）获取到方法的具体信息
        Method objMethod = targetClass.getMethod(methodName, parameterTypes);
        // 拿到方法定义的注解信息
        ReadOnly annotation = objMethod.getDeclaredAnnotation(ReadOnly.class);
        // 返回
        return annotation;
    }

}
