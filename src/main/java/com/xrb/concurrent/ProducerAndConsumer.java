package com.xrb.concurrent;

import lombok.extern.slf4j.Slf4j;
import sun.rmi.runtime.Log;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * 生产者、消费者模式
 *
 * @author xieren8iao
 * @date 2022/3/29 10:57 上午
 */
@Slf4j
public class ProducerAndConsumer {
    public static void main(String[] args) throws InterruptedException {
        Queue queue = new Queue(2);
        for (int i = 0; i < 3; i++) {
            int id = i;
            new Thread(() -> {
                Message message = new Message(id, "值:" + id);
                queue.put(message);
            }, "生产者" + i).start();
        }
        new Thread(() -> {
            while (true) {
                try {
                    TimeUnit.SECONDS.sleep(1);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                Message message = queue.take();
                log.info("消息内容:{}", message);
            }
        },"消费者").start();

    }

}

@Slf4j
class Queue {
    public Queue(int capacity) {
        this.capacity = capacity;
    }

    //容量
    private int capacity;

    //双向队列
    private LinkedList<Message> queueList = new LinkedList<>();

    /**
     * 消费消息
     *
     * @return
     */
    public Message take() {
        //消费者取数据，当队列为空时一直等待
        synchronized (queueList) {
            while (queueList.isEmpty()) {
                try {
                    log.info("消费者等待生产消息.");
                    queueList.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            //唤醒队列生产消息
            log.info("消费者消费消息");
            queueList.notifyAll();
            return queueList.removeFirst();
        }

    }

    /**
     * 生产消息
     *
     * @param message
     */
    public void put(Message message) {
        synchronized (queueList) {
            //当队列容量等于阈值
            while (queueList.size() == capacity) {
                try {
                    log.info("生产者等待生产消息");
                    queueList.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            log.info("生产者生产消息");
            queueList.addLast(message);
            //唤醒消费者消费
            queueList.notifyAll();
        }
    }
}

class Message {
    public Message(int id, Object value) {
        this.id = id;
        this.value = value;
    }

    private int id;

    private Object value;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Object getValue() {
        return value;
    }

    public void setValue(Object value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return "Message{" +
                "id=" + id +
                ", value=" + value +
                '}';
    }
}