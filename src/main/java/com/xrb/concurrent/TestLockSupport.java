package com.xrb.concurrent;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.locks.LockSupport;

/**
 * @author xieren8iao
 * @date 2022/3/29 11:33 上午
 */
@Slf4j
public class TestLockSupport {
    public static void main(String[] args) throws InterruptedException {
        Thread t1 = new Thread(() -> {
            log.info("pack...");
            LockSupport.park();
            log.info("consume...");
        }, "t1");
        t1.start();
        Thread.sleep(2000);

        LockSupport.unpark(t1);
    }
}