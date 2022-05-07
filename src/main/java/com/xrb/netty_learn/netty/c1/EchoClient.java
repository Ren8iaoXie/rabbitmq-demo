package com.xrb.netty_learn.netty.c1;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;
import lombok.extern.slf4j.Slf4j;

import java.util.Scanner;

/**
 * 回声客户端
 *
 * @author xieren8iao
 * @date 2022/4/16 3:00 下午
 */
@Slf4j
public class EchoClient {
    public static void main(String[] args) throws InterruptedException {
        NioEventLoopGroup group = new NioEventLoopGroup();
        Channel channel = new Bootstrap()
                .group(group)
                .channel(NioSocketChannel.class)
                .handler(new ChannelInitializer<NioSocketChannel>() {
                    @Override
                    protected void initChannel(NioSocketChannel ch) throws Exception {
                        ch.pipeline().addLast(new StringDecoder());
                        ch.pipeline().addLast(new ChannelInboundHandlerAdapter(){
                            @Override
                            public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
                                log.info("echo msg:{}",msg);
                                super.channelRead(ctx, msg);
                            }
                        });
                    }
                })
                .connect("localhost", 8080)
                .sync()
                .channel();
        while (true) {
            Scanner scanner = new Scanner(System.in);
            String msg = scanner.nextLine();
            channel.writeAndFlush(msg);
            if ("q".equals(msg)) {
                channel.close();
                group.shutdownGracefully();
                break;
            }
        }
    }
}