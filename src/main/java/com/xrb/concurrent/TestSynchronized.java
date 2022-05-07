package com.xrb.concurrent;

import com.xrb.threadPoolTest.Test1;
import lombok.extern.slf4j.Slf4j;

/**
 * @author xieren8iao
 * @date 2022/3/27 10:44 上午
 */
@Slf4j
public class TestSynchronized {
    public static void main(String[] args) throws InterruptedException {
        NumberOper numberOper = new NumberOper();
        Thread t1 = new Thread(() -> {
            for (int i = 0; i < 50000; i++) {
                numberOper.add();
            }
        }, "t1"
        );
        t1.start();
        Thread t2 = new Thread(() -> {
            for (int i = 0; i < 50000; i++) {
                numberOper.sub();
            }
        }, "t2");
        t2.start();

        t1.join();
        t2.join();
        log.info("count:{}", numberOper.getCount());
    }

}

class NumberOper {
    private int count = 0;


    public synchronized void add() {
        count++;
    }

    public synchronized void sub() {
        count--;
    }

    public synchronized int getCount() {
        return count;
    }
}