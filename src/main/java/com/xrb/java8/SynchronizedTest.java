package com.xrb.java8;

import cn.hutool.core.date.DateUtil;

/**
 * @author xieren8iao
 * @date 2022/3/5 5:36 下午
 */
public class SynchronizedTest {
    public static void main(String[] args) {
        new Thread(() ->
        {
            while (true){
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                test1();
            }

        }, "thread-1").start();

        new Thread(() ->
        {
            while (true){
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                test1();
            }
        }, "thread-2").start();

    }

    public static synchronized void test1() {
//        synchronized (SynchronizedTest.class) {
            System.out.println(Thread.currentThread().getName()
                    + "get test1"+ DateUtil.now());
//        }
    }
}