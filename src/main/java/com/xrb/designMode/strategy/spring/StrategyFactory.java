package com.xrb.designMode.strategy.spring;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * @author xieren8iao
 * @date 2022/3/21 8:20 下午
 */
@Component
public class StrategyFactory {
    @Autowired
    private StrategyAliasConfig strategyAliasConfig;

    //Spring会自动将Strategy接口的实现类注入到这个Map中，key为bean id，value值则为对应的策略实现类
    @Autowired
    private Map<String, Strategy> strategyMap;

    //找不到对应的策略类，使用默认的
    public Strategy getBy(String strategyName) {
        String name = strategyAliasConfig.of(strategyName);
        if (name == null) {
            return strategyMap.get(StrategyAliasConfig.DEFAULT_STATEGY_NAME);
        }
        Strategy strategy = strategyMap.get(name);
        if (strategy == null) {
            return strategyMap.get(StrategyAliasConfig.DEFAULT_STATEGY_NAME);
        }
        return strategy;

    }
}