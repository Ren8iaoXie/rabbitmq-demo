package com.xrb.netty_learn.nio;

import java.nio.IntBuffer;

/**
 * @author xieren8iao
 * @date 2021/10/31 11:58 上午
 */
public class BasicBuffer {
    public static void main(String[] args) {
        IntBuffer intBuffer = IntBuffer.allocate(5);
        for (int i = 0; i < 5; i++) {
            intBuffer.put(i*2);
        }
        //切换读写
        intBuffer.flip();
        intBuffer.position(1);
        intBuffer.limit(3);
        while (intBuffer.hasRemaining()){
            System.out.println(intBuffer.get());
        }
    }
}