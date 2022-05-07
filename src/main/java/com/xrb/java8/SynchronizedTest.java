package com.xrb.java8;

import lombok.extern.slf4j.Slf4j;

/**
 * @author xieren8iao
 * @date 2022/3/5 5:36 下午
 */
@Slf4j
public class SynchronizedTest {
    public static void main(String[] args) throws InterruptedException {
        Thread t1 = new Thread(() -> {
            log.info("1开始");
            A.test1();
        }, "t1");
        t1.start();

        Thread t2 = new Thread(() -> {
            log.info("2开始");
            A.test2();
        }, "t2");
        t2.start();
//        log.info("count:{}", A.getCount());
    }


}

@Slf4j
class A {
    private static int count = 0;

    public static synchronized   void test1() {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        log.info("1");
    }

    public static synchronized void test2() {
        log.info("2");
    }

    public static int getCount() {
        return count;
    }
}