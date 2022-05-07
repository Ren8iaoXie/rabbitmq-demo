package com.xrb.designMode.strategy.spring;

import org.springframework.stereotype.Component;

/**
 * @author xieren8iao
 * @date 2022/3/21 8:18 下午
 */
@Component(value = "strategy2")
public class Strategy2 implements Strategy{

    @Override
    public void doSomething() {
        System.out.println("Strategy2 运算法则");
    }
}