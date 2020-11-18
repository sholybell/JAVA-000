package com.holybell.homework05.lesson10.q1;

/**
 * 相比{@link Singleton01 } 避免了线程安全问题，但是{@link #getSingleton()}方法加锁的粒度太大，多线程对单例对象的获取永远串行执行
 *
 */
public class Singleton02 {

    private static Singleton02 singleton;

    public synchronized static Singleton02 getSingleton() {
        if (singleton == null) {
            singleton = new Singleton02();
        }
        return singleton;
    }
}
