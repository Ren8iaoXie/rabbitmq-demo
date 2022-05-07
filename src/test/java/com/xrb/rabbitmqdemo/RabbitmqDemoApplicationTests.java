package com.xrb.rabbitmqdemo;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.map.MapUtil;
import cn.hutool.core.util.StrUtil;
import com.google.common.collect.Lists;
import com.xrb.designMode.strategy.spring.StrategyFactory;
import com.xrb.java8.Letter;
import com.xrb.java8.TestData;
import org.apache.commons.lang3.StringUtils;
import org.assertj.core.internal.bytebuddy.description.type.TypeDescription;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.function.Function;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@SpringBootTest
class RabbitmqDemoApplicationTests {
    @Autowired
    private ApplicationContext context;
    @Test
    void contextLoads() {
//
        context.getBean(StrategyFactory.class).getBy("1").doSomething();
        context.getBean(StrategyFactory.class).getBy("2").doSomething();
    }

    //是否数字
    public boolean isMatches(String bot) {
        boolean flag = false;
        try {
            String regex = "^[1-9]+[0-9]*$";
            Pattern p = Pattern.compile(regex);
            Matcher m = p.matcher(bot);
            if (m.find()) {
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return flag;
    }
}