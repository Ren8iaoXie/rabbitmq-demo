package com.xrb.netty_learn.netty.c1;

import io.netty.channel.*;
import io.netty.channel.embedded.EmbeddedChannel;
import lombok.extern.slf4j.Slf4j;

/**
 * @author xieren8iao
 * @date 2022/2/27 4:42 下午
 *
 *
 */
@Slf4j
public class EmbeddedChannelTest {
    public static void main(String[] args) {
//        ChannelHandler channelHandler=new ChannelInitializer<>() {}
        ChannelInboundHandlerAdapter h1 = new ChannelInboundHandlerAdapter() {
            @Override
            public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
                log.info("1");
                super.channelRead(ctx, msg);
            }
        };ChannelInboundHandlerAdapter h2 = new ChannelInboundHandlerAdapter() {
            @Override
            public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
                log.info("2");
                super.channelRead(ctx, msg);
            }
        };ChannelInboundHandlerAdapter h3 = new ChannelInboundHandlerAdapter() {
            @Override
            public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
                log.info("3");
                super.channelRead(ctx, msg);
            }
        };
        ChannelOutboundHandlerAdapter h4=new ChannelOutboundHandlerAdapter(){
            @Override
            public void write(ChannelHandlerContext ctx, Object msg, ChannelPromise promise) throws Exception {
                log.info("4");
                super.write(ctx, msg, promise);
            }
        };
        ChannelOutboundHandlerAdapter h5=new ChannelOutboundHandlerAdapter(){
            @Override
            public void write(ChannelHandlerContext ctx, Object msg, ChannelPromise promise) throws Exception {
                log.info("5");
                super.write(ctx, msg, promise);
            }
        };
        EmbeddedChannel embeddedChannel=new EmbeddedChannel(h1,h2,h3,h4,h5);
        embeddedChannel.writeOutbound("123");
    }
}