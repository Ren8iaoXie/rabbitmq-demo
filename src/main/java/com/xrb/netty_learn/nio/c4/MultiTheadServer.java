package com.xrb.netty_learn.nio.c4;

import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.nio.charset.StandardCharsets;
import java.util.Iterator;
import java.util.concurrent.ConcurrentLinkedDeque;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 多线程版客户端
 *
 * @author xieren8iao
 * @date 2022/2/8 10:23 下午
 */
@Slf4j
public class MultiTheadServer {
    public static void main(String[] args) throws IOException {
        Thread.currentThread().setName("boss");

        ServerSocketChannel ssc = ServerSocketChannel.open();
        ssc.configureBlocking(false);
        ssc.bind(new InetSocketAddress("localhost", 1234));
        Selector boss = Selector.open();
        SelectionKey bossKey = ssc.register(boss, SelectionKey.OP_ACCEPT, null);
        bossKey.interestOps(SelectionKey.OP_ACCEPT);
        Worker[] workers = new Worker[Runtime.getRuntime().availableProcessors()];
        for (int i = 0; i < workers.length; i++) {
            workers[i] = new Worker("worker-" + i);
        }
        AtomicInteger index = new AtomicInteger();
        while (true) {
            boss.select();
            Iterator<SelectionKey> iter = boss.selectedKeys().iterator();
            while (iter.hasNext()) {
                SelectionKey key = iter.next();
                iter.remove();
                if (key.isAcceptable()) {
                    SocketChannel sc = (SocketChannel) ssc.accept();
                    sc.configureBlocking(false);
                    log.info("connected...{}", sc.getRemoteAddress());
                    log.info("before register...,{}", sc.getRemoteAddress());
                    workers[index.incrementAndGet() % workers.length].register(sc);
                    log.info("after register...,{}", sc.getRemoteAddress());
                }
            }
        }
    }

    static class Worker implements Runnable {
        private Thread thread;
        private Selector selector;
        private String name;
        private volatile boolean isStart = false;

        public Worker(String name) {
            this.name = name;
        }

        /**
         * 线程间执行顺序可以通过队列实现
         */
        private ConcurrentLinkedDeque<Runnable> queue = new ConcurrentLinkedDeque<>();

        public void register(SocketChannel sc) {
            if (!isStart) {
                thread = new Thread(this, name);
                try {
                    selector = Selector.open();
                } catch (IOException exception) {
                    exception.printStackTrace();
                }
                thread.start();
                isStart = true;
            }
            queue.add(() -> {
                try {
                    sc.register(selector, SelectionKey.OP_READ, null);
                } catch (ClosedChannelException e) {
                    e.printStackTrace();
                }
            });
            selector.wakeup();//唤醒select()
        }

        /**
         * When an object implementing interface <code>Runnable</code> is used
         * to create a thread, starting the thread causes the object's
         * <code>run</code> method to be called in that separately executing
         * thread.
         * <p>
         * The general contract of the method <code>run</code> is that it may
         * take any action whatsoever.
         *
         * @see Thread#run()
         */
        @Override
        public void run() {
            while (true) {
                try {
                    selector.select();
                } catch (IOException exception) {
                    exception.printStackTrace();
                }
                Runnable task = queue.poll();
                if (task != null) {
                    task.run();
                }
                Iterator<SelectionKey> iter = selector.selectedKeys().iterator();
                while (iter.hasNext()) {
                    SelectionKey key = iter.next();
                    iter.remove();
                    if (key.isReadable()) {
                        //TODO 暂时不考虑消息边界问题
                        SocketChannel sc = (SocketChannel) key.channel();
                        ByteBuffer buffer = ByteBuffer.allocate(16);
                        try {
                            sc.read(buffer);
                            buffer.flip();
                            String str = StandardCharsets.UTF_8.decode(buffer).toString();
                            log.info("buffer..." + str);
                        } catch (IOException exception) {
                            exception.printStackTrace();
                        }

                    }
                }
            }

        }
    }
}
