package com.holybell.homework05.lesson09.q44;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.stream.IntStream;

/**
 * 待被AOP增强的类
 */
public class Student {

    private Map<Integer, String> dataBase = new HashMap<>();

    {
        IntStream.rangeClosed(1, 10).forEach(i ->
                dataBase.put(i, "student_" + i)
        );
    }

    @Log
    public String getStudentNameById(Integer id) throws InterruptedException {
        TimeUnit.SECONDS.sleep(3);
        return dataBase.get(id);
    }
}
