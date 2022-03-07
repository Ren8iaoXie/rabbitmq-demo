package com.xrb.netty_learn.netty.c1;

import io.netty.channel.DefaultEventLoop;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.TimeUnit;

/**
 * @author xieren8iao
 * @date 2022/2/13 7:04 下午
 */
@Slf4j
public class TestEventLoopGroup {
    public static void main(String[] args) {
        EventLoopGroup eventLoopGroup = new NioEventLoopGroup(2);//io任务、普通任务、定时任务
//        EventLoopGroup eventLoopGroup = new DefaultEventLoop(2);//普通任务、定时任务
//        System.out.println(eventLoopGroup.next());
//        System.out.println(eventLoopGroup.next());
//        System.out.println(eventLoopGroup.next());

        //普通任务 异步
//        eventLoopGroup.next().execute(() -> {
//            try {
//                TimeUnit.SECONDS.sleep(1);
//                log.info("ok");
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
//        });
        //定时任务
        eventLoopGroup.next().scheduleAtFixedRate(() -> {
            System.out.println("ok");
        }, 1, 1, TimeUnit.SECONDS);
        log.info("main");
    }
}