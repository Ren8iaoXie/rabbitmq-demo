package com.xrb.redisdemo.config.controller;

import lombok.extern.slf4j.Slf4j;
import org.redisson.Redisson;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import sun.rmi.runtime.Log;

/**
 * @author xieren8iao
 * @date 2022/4/22 4:39 下午
 */
@RestController
@RequestMapping("/redisson")
@Slf4j
public class RedissonController {
    @Autowired
    private StringRedisTemplate stringRedisTemplate;
    @Autowired
    private RedissonClient redissonClient;
    @GetMapping("/save")
    public String save(){
        stringRedisTemplate.opsForValue().set("key","redisson");
        return "save ok";
    }

    @GetMapping("/get")
    public String get(){
        return stringRedisTemplate.opsForValue().get("key");
    }

    @RequestMapping("/getLock")
    public String getLock(){
        RLock lock = redissonClient.getLock("WuKong-lock");
        log.info("getLock请求");
        // 2.加锁
        lock.lock();
        try {
            System.out.println("加锁成功，执行后续代码。线程 ID：" + Thread.currentThread().getId());
            Thread.sleep(10000);
        } catch (Exception e) {
            //TODO
        } finally {
            lock.unlock();
            // 3.解锁
            System.out.println("Finally，释放锁成功。线程 ID：" + Thread.currentThread().getId());
        }
        return "getLock ok";
    }
}
