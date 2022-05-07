package com.xrb.concurrent;

import lombok.extern.slf4j.Slf4j;
import sun.rmi.runtime.Log;

import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;

/**
 * 保护性暂停 拓展
 *
 * @author xieren8iao
 * @date 2022/3/27 7:56 下午
 */
@Slf4j
public class GuardedObjectTest {
    public static void main(String[] args) throws InterruptedException {
//        GuardedObject guardedObject = new GuardedObject(1);
//        new Thread(() -> {
//            log.info("开始等待获取结果...");
//            Object o = guardedObject.get(2000);
//            log.info("获取结果内容:{}", o);
//        }, "get").start();
//
//        new Thread(() -> {
//            log.info("开始处理结果...");
//            try {
//                TimeUnit.SECONDS.sleep(5);
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
//            guardedObject.complete("完成了");
//        }, "complete").start();

        for (int i = 0; i < 3; i++) {
            new People().start();
        }
        TimeUnit.SECONDS.sleep(2);

        Set<Integer> ids = Future.getIds();
        for (Integer id : ids) {
            new Postman(id, "你好").start();
        }
    }
}

@Slf4j
class People extends Thread {
    @Override
    public void run() {
        GuardedObject guardedObject = Future.createGuardedObject();
        log.info("开始收信 id:{}", guardedObject.getId());
        Object o = guardedObject.get(5000);
        log.info("收到信id:{}信内容:{}", guardedObject.getId(), o);
    }
}

@Slf4j
class Postman extends Thread {
    public Postman(Integer id, String mail) {
        this.id = id;
        this.mail = mail;
    }

    private int id;

    private String mail;

    @Override
    public void run() {
        log.info("开始送信...");
        GuardedObject guardedObject = Future.getGuardedObject(id);
        guardedObject.complete(mail + "_" + "你好");
        log.info("送信结束...id:{},内容:{}", id, mail);
    }
}

class Future {
    private static int id = 1;

    private static Map<Integer, GuardedObject> boxes = new ConcurrentHashMap();

    private static synchronized int generateId() {
        return id++;
    }

    /**
     * 创建保护性对象
     *
     * @return
     */
    public static GuardedObject createGuardedObject() {
        GuardedObject guardedObject = new GuardedObject(generateId());
        boxes.put(id, guardedObject);
        return guardedObject;
    }

    public static GuardedObject getGuardedObject(int id) {
        return boxes.remove(id);
    }

    public static Set<Integer> getIds() {
        return boxes.keySet();
    }

}

class GuardedObject {
    private int id;

    private Object response;

    public GuardedObject(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    /**
     * 获取结果
     *
     * @param timeout 最多等待超时时间
     * @return
     */
    public Object get(long timeout) {
        synchronized (this) {
            long start = System.currentTimeMillis();
            long passedTime = 0;
            while (response == null) {
                long waitTime = timeout - passedTime;
                if (waitTime <= 0) {
                    break;
                }
                try {
                    this.wait(waitTime);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                passedTime = System.currentTimeMillis() - start;
            }
            return response;
        }
    }

    public void complete(Object object) {
        synchronized (this) {
            this.response = object;
            this.notifyAll();
        }
    }

}