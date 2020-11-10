package java0.homework;

import java.util.concurrent.Semaphore;

public class Homework0306 {

    private static Semaphore semaphore = new Semaphore(1);
    private static volatile int result = 0;

    public static void main(String[] args) throws InterruptedException {

        long start = System.currentTimeMillis();

        new Thread(() -> {
            result = sum();
            try {
                semaphore.acquire(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                semaphore.release(1);
            }
        }).start();

        Thread.sleep(1000L);            // 令线程抢先执行
        semaphore.acquire(1);           //  阻塞主线程
        System.out.println("异步计算结果为：" + result);
        System.out.println("使用时间：" + (System.currentTimeMillis() - start) + " ms");
    }

    private static int sum() {
        return fibo(36);
    }

    private static int fibo(int a) {
        if (a < 2)
            return 1;
        return fibo(a - 1) + fibo(a - 2);
    }
}
