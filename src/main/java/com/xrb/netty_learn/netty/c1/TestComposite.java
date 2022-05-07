package com.xrb.netty_learn.netty.c1;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufAllocator;
import io.netty.buffer.CompositeByteBuf;

import static com.xrb.netty_learn.netty.c1.TestByteBuf.log;

/**
 * @author xieren8iao
 * @date 2022/4/16 1:45 下午
 */
public class TestComposite {
    public static void main(String[] args) {
        ByteBuf buf1= ByteBufAllocator.DEFAULT.buffer(5);
        buf1.writeBytes(new byte[]{'a','b'});
        ByteBuf buf2= ByteBufAllocator.DEFAULT.buffer(5);
        buf2.writeBytes(new byte[]{'c','d'});

        CompositeByteBuf compositeByteBuf = ByteBufAllocator.DEFAULT.compositeBuffer(10);
        //自动计算写指针
        compositeByteBuf.addComponents(true,buf1,buf2);
        log(compositeByteBuf);
    }
}