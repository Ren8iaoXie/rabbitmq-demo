package com.xrb.designMode.strategy.spring;

import org.springframework.stereotype.Component;

/**
 * @author xieren8iao
 * @date 2022/3/21 8:18 下午
 */
@Component(value = "strategy1")
public class Strategy1 implements Strategy{

    @Override
    public void doSomething() {
        System.out.println("Strategy1 运算法则");
    }
}