package com.holybell.homework05.lesson10.q1;

/**
 * 相比{@link Singleton02 } 采取两个双重校验机制，{@link #getSingleton()}方法降低了锁粒度，只有第一次获取单例对象才会串行执行，以后多线程可以并发获取单例对象
 */
public class Singleton03 {

    private volatile static Singleton03 singleton;

    public static Singleton03 getSingleton() {
        if (singleton == null) {
            synchronized (Singleton03.class) {
                if (singleton == null) {
                    singleton = new Singleton03();
                }
            }
        }
        return singleton;
    }
}
