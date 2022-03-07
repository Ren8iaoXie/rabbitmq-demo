package com.xrb.netty_learn.netty.c1;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.string.StringEncoder;
import lombok.extern.slf4j.Slf4j;
import sun.rmi.runtime.Log;

import java.net.InetSocketAddress;

/**
 * @author xieren8iao
 * @date 2022/2/13 3:23 下午
 */
@Slf4j
public class HelloClient {
    public static void main(String[] args) throws InterruptedException {
        ChannelFuture channelFuture = new Bootstrap()
                .group(new NioEventLoopGroup())
                .channel(NioSocketChannel.class)
                .handler(new ChannelInitializer<NioSocketChannel>() {
                    @Override
                    protected void initChannel(NioSocketChannel sc) throws Exception {
                        sc.pipeline().addLast(new StringEncoder());
                    }
                })
                //异步调用，
                .connect(new InetSocketAddress("localhost", 9999));
        //需要主线程阻塞等待链接成功
//        channelFuture
//                .sync()
//                .channel()
//                .writeAndFlush("hello world");

        channelFuture.addListener(new ChannelFutureListener() {
            @Override
            public void operationComplete(ChannelFuture channelFuture) throws Exception {
                Channel channel = channelFuture.channel();
                log.info("{}",channel);
            }
        });

    }
}