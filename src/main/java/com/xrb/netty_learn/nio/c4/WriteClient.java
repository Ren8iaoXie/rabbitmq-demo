package com.xrb.netty_learn.nio.c4;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.SocketChannel;
import java.nio.charset.Charset;

/**
 * @author xieren8iao
 * @date 2022/2/8 9:31 下午
 */
public class WriteClient {
    public static void main(String[] args) throws IOException {
        SocketChannel sc = SocketChannel.open();
        sc.connect(new InetSocketAddress("localhost", 1234));
        sc.write(Charset.defaultCharset().encode("hi"));
        System.out.println();
//        int count = 0;
//        ByteBuffer buffer = ByteBuffer.allocate(1024 * 1024);
//        while (true) {
//            int readCount = sc.read(buffer);
//            count+=readCount;
//            System.out.println(count);
//            buffer.clear();
//        }
    }
}