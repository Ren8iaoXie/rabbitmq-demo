package com.xrb.designMode.strategy.spring;

/**
 * @author xieren8iao
 * @date 2022/3/21 8:19 下午
 */
public class DefaultStrategy implements Strategy{
    @Override
    public void doSomething() {
        System.out.println("默认策略  运算法则");
    }
}