package com.xrb.concurrent;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.TimeUnit;

/** 两阶段终止模式
 * @author xieren8iao
 * @date 2022/3/22 11:12 上午
 */
public class TwoPhaseTerminationTest {
    public static void main(String[] args) throws InterruptedException {
        TwoPhaseTermination twoPhaseTermination = new TwoPhaseTermination();
        twoPhaseTermination.start();

        Thread.sleep(3500);

        twoPhaseTermination.stop();
    }
}
@Slf4j
class TwoPhaseTermination{
    //监控线程
    private Thread monitor;

    public void  start(){
        monitor=new Thread(()->{
            while (true){
                Thread currentThread = Thread.currentThread();
                if (currentThread.isInterrupted()){
                    log.info("料理后事");
                    break;
                }
                try {
                    TimeUnit.SECONDS.sleep(1);
                    log.info("执行监控记录");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                    currentThread.interrupt();
                }
            }
        },"monitor");
        monitor.start();
    }

    public void stop(){
        monitor.interrupt();
    }
}