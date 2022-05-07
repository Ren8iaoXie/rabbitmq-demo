package com.xrb.designMode.strategy.spring;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

import java.util.HashMap;

/**
 * @author xieren8iao
 * @date 2022/3/21 8:27 下午
 */
@Component
@PropertySource("classpath:application.yml")
@ConfigurationProperties(prefix = "strategy")
public class StrategyAliasConfig {
    private HashMap<String, String> aliasMap;

    public static final String DEFAULT_STATEGY_NAME = "defaultStrategy";

    public HashMap<String, String> getAliasMap() {
        return aliasMap;
    }

    public void setAliasMap(HashMap<String, String> aliasMap) {
        this.aliasMap = aliasMap;
    }

    public  String of(String entNum) {
        return aliasMap.get(entNum);
    }
}