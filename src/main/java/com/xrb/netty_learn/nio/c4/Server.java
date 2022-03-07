package com.xrb.netty_learn.nio.c4;

import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * @author xieren8iao
 * @date 2022/1/29 11:18 下午
 */
@Slf4j
public class Server {
    public static void main(String[] args) throws IOException {
//        normalAccept();
        //1.创建selector对象
        Selector selector = Selector.open();
        //2.创建服务器连接对象
        ServerSocketChannel ssc = ServerSocketChannel.open();
        //3.绑定端口
        ssc.bind(new InetSocketAddress("localhost", 7777));
        //设置非阻塞，默认阻塞
        ssc.configureBlocking(false);

        //4.建立selector和channel之间的关系
        SelectionKey sscKey = ssc.register(selector, 0, null);
        log.info("register key:{}", sscKey);
        //让selectionkey只关注连接事件
        sscKey.interestOps(SelectionKey.OP_ACCEPT);
//        sscKey.interestOps(SelectionKey.OP_READ);

        while (true) {
            //没有事件时会让线程阻塞在此,有事件将会恢复执行
            selector.select();
            //内部包含所有要处理的事件
            Iterator<SelectionKey> iterator = selector.selectedKeys().iterator();
            while (iterator.hasNext()) {
                SelectionKey key = iterator.next();
                log.info("key...{}", key);
                //接受完key以后，需要自己删除事件key，否则还会存在集合中
                iterator.remove();

                //5.区分事件类型
                if (key.isAcceptable()) {//连接事件
                    ServerSocketChannel channel = (ServerSocketChannel) key.channel();
                    SocketChannel socketChannel = channel.accept();
                    socketChannel.configureBlocking(false);
                    ByteBuffer byteBuffer = ByteBuffer.allocate(16);

                    SelectionKey scKey = socketChannel.register(selector, 0, byteBuffer);
                    scKey.interestOps(SelectionKey.OP_READ);
                    log.info("socket channel...{}", socketChannel);
                } else if (key.isReadable()) {//可读事件
                    try {
                        SocketChannel channel = (SocketChannel) key.channel();
                        ByteBuffer byteBuffer = (ByteBuffer) key.attachment();
                        int read = channel.read(byteBuffer);
                        //返回-1为正常断开
                        if (read == -1) {
                            key.cancel();
                        } else {
//                            byteBuffer.flip();
                            split(byteBuffer);
                            if (byteBuffer.position() == byteBuffer.limit()) {
                                ByteBuffer newByteBuffer = ByteBuffer.allocate(byteBuffer.capacity() * 2);
                                byteBuffer.flip();
                                newByteBuffer.put(byteBuffer);
                                key.attach(newByteBuffer);
                            }
//                            log.info("buffer str:{}", StandardCharsets.UTF_8.decode(byteBuffer));
                        }
                    } catch (IOException exception) {
                        exception.printStackTrace();

                        key.cancel();
                    }
                }
            }
        }

    }

    private static void split(ByteBuffer source) {
        source.flip();
        for (int i = 0; i < source.limit(); i++) {
            if (source.get(i) == '\n') {
                int strLength = i - source.position() + 1;
                ByteBuffer target = ByteBuffer.allocate(strLength);
                for (int j = 0; j < strLength; j++) {
                    target.put(source.get());
                }
                target.flip();
                System.out.println(StandardCharsets.UTF_8.decode(target));
            }
        }
        source.compact();//清除已经读过的position
    }

    private static void normalAccept() throws IOException {
        //1.创建ByteBuffer
        ByteBuffer buffer = ByteBuffer.allocate(16);
        //2.创建服务器连接对象
        ServerSocketChannel ssc = ServerSocketChannel.open();
        //3.绑定端口
        ssc.bind(new InetSocketAddress("localhost", 7777));
        //设置非阻塞，默认阻塞
        ssc.configureBlocking(false);
        //4.列表
        List<SocketChannel> list = new ArrayList<>();
        //4.阻塞等待客户端连接
        while (true) {
            log.info("connecting...{}", ssc);
            //accept()阻塞
            SocketChannel socketChannel = ssc.accept();
            if (socketChannel != null) {
                list.add(socketChannel);
                //配置非阻塞
                socketChannel.configureBlocking(false);
                log.info("connected...{}", ssc);
            }
            for (SocketChannel channel : list) {
                log.info("before read...{}", channel);
                //read()等待阻塞
                int read = socketChannel.read(buffer);
                if (read > 0) {
                    buffer.flip();
                    log.info("====>buffer:{},{}", Charset.defaultCharset().decode(buffer), buffer);
                    buffer.clear();
                    log.info("after read...{}", channel);
                }
            }
        }
    }
}