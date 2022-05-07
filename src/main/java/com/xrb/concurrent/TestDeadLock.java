package com.xrb.concurrent;

import lombok.extern.slf4j.Slf4j;

/**
 * @author xieren8iao
 * @date 2022/3/30 8:02 下午
 */
@Slf4j
public class TestDeadLock {
    public static void main(String[] args) {
        Object a=new Object();
        Object b=new Object();

        new Thread(()->{
            synchronized (a){
                try {
                    log.info("获得锁a");
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                synchronized (b){
                    log.info("获得锁b");
                }
            }
        },"t1").start();

        new Thread(()->{
            synchronized (b){
                try {
                    log.info("获得锁b");
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                synchronized (a){
                    log.info("获得锁a");
                }
            }
        },"t2").start();

    }
}