package java0.homework;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

public class Homework0303 {

    public static void main(String[] args) throws ExecutionException, InterruptedException {

        long start = System.currentTimeMillis();

        FutureTask<Integer> task = new FutureTask<>(Homework0303::sum);
        Thread th = new Thread(task);
        th.start();

        System.out.println("异步计算结果为：" + task.get());
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
