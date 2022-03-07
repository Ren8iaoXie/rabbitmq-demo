package com.xrb.netty_learn.netty.c1;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.string.StringEncoder;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import lombok.extern.slf4j.Slf4j;
import sun.rmi.runtime.Log;

import java.net.InetSocketAddress;
import java.util.Scanner;
import java.util.logging.Logger;

/**
 * @author xieren8iao
 * @date 2022/2/22 9:31 下午
 */
@Slf4j
public class CloseFutureClient {
    public static void main(String[] args) throws InterruptedException {
        NioEventLoopGroup group = new NioEventLoopGroup();
        ChannelFuture channelFuture = new Bootstrap()
                .group(group)
                .channel(NioSocketChannel.class)
                .handler(new ChannelInitializer<NioSocketChannel>() {
                    @Override
                    protected void initChannel(NioSocketChannel channel) throws Exception {
                        channel.pipeline().addLast(new LoggingHandler(LogLevel.DEBUG));
                        channel.pipeline().addLast(new StringEncoder());
                    }
                })
                .connect(new InetSocketAddress("localhost", 7777));

        Channel channel = channelFuture.sync().channel();
        log.info("{}",channel);
        new Thread(()->{
            Scanner scanner=new Scanner(System.in);
            while (true){
                String s = scanner.nextLine();
                if (s.equals("q")){
                    log.info("quit");
                    channel.close();
                    break;
                }
                channel.writeAndFlush(s);
            }
        },"input").start();

        //获取closeFuture对象
        /**
         * 1、调用同步方法阻塞
         * 2、异步关闭执行
         */
        ChannelFuture closeFuture = channel.closeFuture();
        //1.
//        closeFuture.sync();
        //2.
        closeFuture.addListener((ChannelFutureListener) channelFuture1 -> {
            log.info("处理关闭之后的操作");
            group.shutdownGracefully();
        });


    }
}