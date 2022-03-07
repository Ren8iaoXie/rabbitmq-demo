package com.xrb.netty_learn.nio.c1;

import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;

/**
 * @author xieren8iao
 * @date 2022/1/29 3:58 下午
 */
public class TestByteBufferString
{
    public static void main(String[] args) {
        String str="hello";
        //1.字符串直接转为ByteBuffer 此时为写模式
        byte[] bytes = str.getBytes();
        ByteBuffer buffer1=ByteBuffer.allocate(16);
        buffer1.put(bytes);

        //2.Charset.encode 此时byteBuffer为读模式
        ByteBuffer buffer2 = StandardCharsets.UTF_8.encode(str);

        //3.wrap byteBuffer为读模式
        ByteBuffer buffer3 = ByteBuffer.wrap(bytes);

        System.out.println(StandardCharsets.UTF_8.decode(buffer3));

    }
}