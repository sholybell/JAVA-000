package java0.homework;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CountDownLatch;

public class Homework0309 {

    public static void main(String[] args) throws InterruptedException {

        long start = System.currentTimeMillis();

        Map<String, Integer> resMap = new HashMap<>(1);
        CountDownLatch latch = new CountDownLatch(1);
        new Thread(() -> {          // 不使用Callable，使用Runnable接口
            try {
                resMap.put("result", sum());
            } finally {
                latch.countDown();
            }
        }).start();
        latch.await();
        // 确保  拿到result 并输出
        System.out.println("异步计算结果为：" + resMap.get("result"));
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
