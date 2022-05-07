package com.xrb.netty_learn.netty.c1;

import io.netty.channel.EventLoop;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.util.NettyRuntime;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.TimeUnit;

/**
 * @author xieren8iao
 * @date 2022/4/11 8:04 下午
 */
@Slf4j
public class TestEventLoop {
    public static void main(String[] args) {
        EventLoopGroup eventLoopGroup=new NioEventLoopGroup(2);
        EventLoop eventLoop = eventLoopGroup.next();

        eventLoop.scheduleAtFixedRate(()->{
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            log.info("hello");
        },1,1, TimeUnit.SECONDS);
    }
}