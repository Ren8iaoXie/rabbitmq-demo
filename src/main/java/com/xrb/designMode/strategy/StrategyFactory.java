package com.xrb.designMode.strategy;

import javax.naming.spi.StateFactory;
import java.util.HashMap;
import java.util.Map;

/**
 * @author xieren8iao
 * @date 2022/3/10 8:31 下午
 */
public class StrategyFactory {
    /**
     * 单例模式
     */
    private StrategyFactory() {

    }

    private static volatile StrategyFactory factory = null;

    private static Map<Integer, Strategy> strategyMap = new HashMap<>();

    static {
        strategyMap.put(PayTypeEnum.WX_PAY.getValue(), new WxPay());
        strategyMap.put(PayTypeEnum.STORED_PAY.getValue(), new StoredPay());
    }

    public Strategy creator(Integer type) {
        Strategy strategy = strategyMap.get(type);
        return strategy;
    }

    public static StrategyFactory getInstance() {
        if (factory == null) {
            synchronized (StateFactory.class) {
                if (factory == null) {
                    factory = new StrategyFactory();
                }
            }
        }
        return factory;
    }
}