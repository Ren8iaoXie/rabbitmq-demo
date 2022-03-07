package com.xrb.netty_learn.netty.c1;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufAllocator;
import io.netty.buffer.CompositeByteBuf;

import static com.xrb.netty_learn.netty.c1.TestByteBuf.log;

/**
 * @author xieren8iao
 * @date 2022/2/27 9:51 下午
 */
public class TestCompositeBuffer {
    public static void main(String[] args) {


        ByteBuf f1=ByteBufAllocator.DEFAULT.buffer();
        f1.writeBytes(new byte[]{'a','b'});
        /**
         * 切片
         */
//        ByteBuf slice = f1.slice();
        f1.retain();
        ByteBuf f2=ByteBufAllocator.DEFAULT.buffer();
        f2.writeBytes(new byte[]{'a','b'});

        CompositeByteBuf compositeByteBuf= ByteBufAllocator.DEFAULT.compositeBuffer();
        compositeByteBuf.addComponents(true,f1,f2);
        log(compositeByteBuf);
    }
}