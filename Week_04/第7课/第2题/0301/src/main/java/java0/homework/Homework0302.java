package java0.homework;

import java.util.concurrent.CountDownLatch;

public class Homework0302 {

    private static int result;

    public static void main(String[] args) throws InterruptedException {

        long start = System.currentTimeMillis();
        CountDownLatch latch = new CountDownLatch(1);
        Thread th = new Thread(() -> {
            try {
                result = sum();
            } finally {
                latch.countDown();
            }
        });
        th.start();
        latch.await();

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
