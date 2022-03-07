package com.xrb.netty_learn.nio.c1;

import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;

/**
 * @author xieren8iao
 * @date 2022/1/29 4:29 下午
 */
public class TestByteBufferExample {
    public static void main(String[] args) {
        /**
         * 处理粘包、半包
         */
        ByteBuffer source = ByteBuffer.allocate(32);
        source.put("Hello\nI am Zhangsan\nHow".getBytes());
        split(source);
        source.put("are you\n".getBytes());
        split(source);
//        source.flip();
//        System.out.println(StandardCharsets.UTF_8.decode(source));
    }

    private static void split(ByteBuffer source) {
        source.flip();

        for (int i = 0; i < source.limit(); i++) {
            if (source.get(i) == '\n') {
                int strLength = i - source.position() + 1;
                ByteBuffer target=ByteBuffer.allocate(strLength);
                for (int j = 0; j < strLength; j++) {
                    target.put(source.get());
                }
                target.flip();
                System.out.println(StandardCharsets.UTF_8.decode(target));
            }
        }
        source.compact();//清除已经读过的position
    }
}