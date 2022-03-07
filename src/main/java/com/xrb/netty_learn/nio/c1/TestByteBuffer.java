package com.xrb.netty_learn.nio.c1;

import java.io.FileInputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

/**
 * @author xieren8iao
 * @date 2022/1/29 12:48 下午
 */
public class TestByteBuffer {
    public static void main(String[] args) {
        /**
         * 1.准备channel通道
         * 2.准备buffer
         * 3.使用channel写数据 到buffer
         */
        try (FileChannel fileChannel = new FileInputStream("data.txt").getChannel();) {
            ByteBuffer buffer = ByteBuffer.allocate(10);

            while (true) {
                int read = fileChannel.read(buffer);
                if (read == -1) {
                    break;
                }
                //切换读写
                buffer.flip();
                while (buffer.hasRemaining()) {
                    byte b = buffer.get();
                    System.out.println((char) b);
                }
                buffer.clear();
            }
        } catch (IOException exception) {

        }
    }
}