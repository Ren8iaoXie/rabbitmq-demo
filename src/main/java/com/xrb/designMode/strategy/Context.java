package com.xrb.designMode.strategy;

/**上下文类
 * @author xieren8iao
 * @date 2022/3/10 8:20 下午
 */
public class Context {
    private Strategy strategy;

    public void calculatePay(Integer type){
        StrategyFactory factory = StrategyFactory.getInstance();
        strategy = factory.creator(type);
        strategy.calculatePay();
    }

}