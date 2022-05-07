package com.xrb.concurrent;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author xieren8iao
 * @date 2022/3/30 9:33 下午
 */
public class TestRotationPrint {
    static final Object lock = new Object();
    static int flag = 1;

    public static void main(String[] args) throws InterruptedException {
        //轮换打印abcabcabcabcabc
//        WaitNotify waitNotify = new WaitNotify(5,1,2);
//        testPrintByWaitNotify(waitNotify);

        AwaitSignal awaitSignal = new AwaitSignal(5);
        awaitSignal.testPrintByAwaitSignal();

    }

    private static void testPrintByWaitNotify(WaitNotify waitNotify) {
        Thread t1 = new Thread(() -> {
            waitNotify.print(1, 2, "a");
        }, "t1");

        Thread t2 = new Thread(() -> {
            waitNotify.print(2, 3, "b");
        }, "t3");

        Thread t3 = new Thread(() -> {
            waitNotify.print(3, 1, "c");
        }, "t3");

        t1.start();
        t2.start();
        t3.start();
    }
}

class WaitNotify {
    public WaitNotify(int loopNumber, int flag, int nextFlag) {
        this.loopNumber = loopNumber;
        this.flag = flag;
        this.nextFlag = nextFlag;
    }

    public void print(int curWaitFlag, int nextFlag, String str) {
        for (int i = 0; i < this.loopNumber; i++) {
            synchronized (this) {
                while (curWaitFlag != this.flag) {
                    try {
                        this.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                System.out.print(str);
                this.flag = nextFlag;
                this.notifyAll();
            }
        }
    }


    private int loopNumber;

    private int flag;

    private int nextFlag;
}

/**
 * 通过ReentrantLock生成的条件对象 需要获取到锁之后使用
 */
@Slf4j
class AwaitSignal extends ReentrantLock {
    private int loopNumber;

    public AwaitSignal(int loopNumber) {
        this.loopNumber = loopNumber;
    }

    public void print(String str, Condition curCondition, Condition nextCondition) {
        for (int i = 0; i < loopNumber; i++) {
            lock();
            try {
                curCondition.await();
                System.out.print(str);
                nextCondition.signal();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                unlock();
            }
        }
    }

    public void testPrintByAwaitSignal() throws InterruptedException {
        AwaitSignal awaitSignal = new AwaitSignal(5);
        Condition conditionA = awaitSignal.newCondition();
        Condition conditionB = awaitSignal.newCondition();
        Condition conditionC = awaitSignal.newCondition();

        Thread t1 = new Thread(() -> {
            awaitSignal.print("a", conditionA, conditionB);
        }, "t1");

        Thread t2 = new Thread(() -> {
            awaitSignal.print("b", conditionB, conditionC);
        }, "t3");

        Thread t3 = new Thread(() -> {
            awaitSignal.print("c", conditionC, conditionA);
        }, "t3");

        t1.start();
        t2.start();
        t3.start();

        Thread.sleep(1000);

        awaitSignal.lock();
        try {
            log.info("开始");
            conditionA.signal();
        } finally {
            awaitSignal.unlock();
        }
    }
}

