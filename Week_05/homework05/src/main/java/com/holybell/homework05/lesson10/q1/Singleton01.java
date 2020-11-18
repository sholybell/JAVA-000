package com.holybell.homework05.lesson10.q1;

/**
 * 这种单例模式，在多线程环境下线程不安全，可能创建多个singleton
 */
public class Singleton01 {

    private Singleton01 singleton;

    public Singleton01 getSingleton() {
        if (singleton == null) {
            singleton = new Singleton01();
        }
        return singleton;
    }
}
