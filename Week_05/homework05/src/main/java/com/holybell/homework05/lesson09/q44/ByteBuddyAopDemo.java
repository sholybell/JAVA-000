package com.holybell.homework05.lesson09.q44;

import net.bytebuddy.ByteBuddy;
import net.bytebuddy.asm.Advice;
import net.bytebuddy.matcher.ElementMatchers;

/**
 * ByteBuddy AOP 范例，拦截目标方法前后，输出日志
 */
public class ByteBuddyAopDemo {

    public static void main(String[] args) throws IllegalAccessException, InstantiationException, InterruptedException {
        Student student = new ByteBuddy()
                .subclass(Student.class)
                .method(ElementMatchers.any())  // 拦截所有的方法
                .intercept(Advice.to(LogAdvisor.class)) // 拦截逻辑定义切面
                .make()
                .load(ByteBuddyAopDemo.class.getClassLoader())
                .getLoaded()
                .newInstance();
        System.out.println(student.getStudentNameById(1));
    }

}
