package com.xrb.netty_learn.nio.c1;

import java.nio.ByteBuffer;

/**
 * @author xieren8iao
 * @date 2022/1/29 3:48 下午
 */
public class TestByteBufferRead {
    public static void main(String[] args) {
        ByteBuffer buffer=ByteBuffer.allocate(16);
        buffer.put(new byte[]{'a','b','c','d'});

        //切换为读
        buffer.flip();

        //buffer.get() 将position位置往后移1
        System.out.println((char) buffer.get());
        System.out.println((char) buffer.get());

        //讲position位置移到0
        buffer.rewind();

        System.out.println((char) buffer.get());
        System.out.println((char) buffer.get());

        //标记此时position的位置 配合reset()使用
        buffer.mark();

        System.out.println((char) buffer.get());
        System.out.println((char) buffer.get());


        //回到mark()标记的position的位置
        buffer.reset();

        System.out.println((char) buffer.get());
        System.out.println((char) buffer.get());






    }
}