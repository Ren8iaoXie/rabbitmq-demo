package com.xrb.netty_learn.netty.c1;

import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.embedded.EmbeddedChannel;

/**
 * @author xieren8iao
 * @date 2022/2/27 4:42 下午
 *
 */public class EmbeddedChannelTest {
    public static void main(String[] args) {
//        ChannelHandler channelHandler=new ChannelInitializer<>() {}
        EmbeddedChannel embeddedChannel=new EmbeddedChannel();
    }
}