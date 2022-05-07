package com.xrb.redis;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @author xieren8iao
 * @date 2022/3/8 7:22 下午
 */
@SpringBootTest
@RunWith(SpringRunner.class)
public class TestRedisson {
    @Autowired
    private RedissonClient redissonClient;

    @Test
    public void test1(){
        System.out.println(redissonClient);
    }


}