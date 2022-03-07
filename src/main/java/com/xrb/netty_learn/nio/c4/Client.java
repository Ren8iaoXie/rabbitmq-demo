package com.xrb.netty_learn.nio.c4;

import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.SocketChannel;
import java.nio.charset.Charset;

/**
 * @author xieren8iao
 * @date 2022/1/29 11:24 下午
 */
@Slf4j
public class Client {
    public static void main(String[] args) throws IOException {
        SocketChannel socketChannel=SocketChannel.open();
        socketChannel.connect(new InetSocketAddress("localhost",7777));
        socketChannel.write(Charset.defaultCharset().encode("abcdefghijklmn123\n"));
        log.info("wating...");
    }
}