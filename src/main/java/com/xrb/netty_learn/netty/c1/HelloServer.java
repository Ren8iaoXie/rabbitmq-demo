package com.xrb.netty_learn.netty.c1;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.string.StringDecoder;

/**
 * @author xieren8iao
 * @date 2022/2/13 3:04 下午
 */
public class HelloServer {
    public static void main(String[] args) {
        //1.服务器构建
        new ServerBootstrap()

                .group(new NioEventLoopGroup())
                //3. 选择服务器的ServerSocketChannel实现
                .channel(NioServerSocketChannel.class)
                //4.channel代表和客户端进行数据读写的通道Initializer初始化，负责添加别的handler
                .childHandler(new ChannelInitializer<NioSocketChannel>() {
                    @Override
                    protected void initChannel(NioSocketChannel ch) throws Exception {
                        //添加具体handler
                        ch.pipeline().addLast(new StringDecoder());//讲ByteBuf转为字符串
                        ch.pipeline().addLast(new ChannelInboundHandlerAdapter() { //自定义处理器
                            @Override
                            public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
                                System.out.println(msg);
                            }
                        });
                    }
                })
                //绑定端口
                .bind(9999);
    }
}