package com.holybell.homework05.lesson09.q44;

import net.bytebuddy.asm.Advice;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Method;
import java.util.Arrays;

public class LogAdvisor {

    public static Logger logger = LoggerFactory.getLogger(LogAdvisor.class);

    @Advice.OnMethodEnter
    public static void onMethodEnter(@Advice.Origin Method method, @Advice.AllArguments Object[] arguments) {
        // 接口或方法存在统计注解
        if (method.getAnnotation(Log.class) != null || method.getDeclaringClass().getAnnotation(Log.class) != null) {
            logger.info("进入{}方法 参数 : {}", method.getName(), Arrays.toString(arguments));
        }

    }

    @Advice.OnMethodExit
    public static void onMethodExit(@Advice.Origin Method method, @Advice.AllArguments Object[] arguments, @Advice.Return Object ret) {
        if (method.getAnnotation(Log.class) != null || method.getDeclaringClass().getAnnotation(Log.class) != null) {
            logger.info("退出{}方法 参数 : {} 返回 : {}", method.getName(), Arrays.toString(arguments), ret);
        }
    }

}
