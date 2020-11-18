package com.holybell.homework05.lesson10.aq01.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 缓存注解，标注此注解的方法可用缓存
 */
@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface MyCache {

    /**
     * 缓存过期时间
     */
    int value() default 60;
}
