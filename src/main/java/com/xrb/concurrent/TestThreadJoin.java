package com.xrb.concurrent;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.TimeUnit;

/**
 * @author xieren8iao
 * @date 2022/3/22 9:26 上午
 */
@Slf4j
public class TestThreadJoin {
    static int r=0;

    public static void main(String[] args) throws InterruptedException {
        Thread t1 = new Thread(() -> {
            log.info("running");
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            r = 10;
        }, "t1");
        t1.start();
        //等待该线程执行结束
        t1.join();
        log.info("running...");
        log.info("r:{}",r);

    }
}