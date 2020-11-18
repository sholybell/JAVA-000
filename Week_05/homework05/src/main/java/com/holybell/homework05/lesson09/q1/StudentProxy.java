package com.holybell.homework05.lesson09.q1;

import java.lang.reflect.Proxy;

public class StudentProxy {

    public static void main(String[] args) {
        Person studentProxy = (Person) Proxy.newProxyInstance(
                Student.class.getClassLoader(),
                new Class[]{Person.class},
                (proxy, method, args1) -> {
                    System.out.println("这是前置通知，接下来有请" + args1[0] + "进行自我介绍!");
                    Object invoke = method.invoke(new Student(), args1);
                    System.out.println("这是后置通知，感谢" + args1[0] + "的发言，很精彩!");
                    return invoke;
                });

        studentProxy.introduce("张三", "法外狂徒");
    }


}
