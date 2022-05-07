package com.xrb.netty_learn.netty.c1;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufAllocator;
import io.netty.channel.embedded.EmbeddedChannel;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import io.netty.handler.logging.LoggingHandler;

/**
 * @author xieren8iao
 * @date 2022/4/16 11:16 下午
 */
public class TestFieldLengthDecoder {
    public static void main(String[] args) {
        EmbeddedChannel embeddedChannel = new EmbeddedChannel(
                new LengthFieldBasedFrameDecoder(1024, 0, 4, 0, 0)
        ,
                new LoggingHandler()
        );
        ByteBuf buf = ByteBufAllocator.DEFAULT.buffer();
        String msg="hello,world!";
        buf.writeInt(msg.length());
        buf.writeBytes(msg.getBytes());
        embeddedChannel.writeInbound(buf);
    }
}