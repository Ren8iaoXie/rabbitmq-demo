package com.xrb.rabbitmqdemo;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.map.MapUtil;
import cn.hutool.core.util.StrUtil;
import com.google.common.collect.Lists;
import com.xrb.java8.Letter;
import com.xrb.java8.TestData;
import org.apache.commons.lang3.StringUtils;
import org.assertj.core.internal.bytebuddy.description.type.TypeDescription;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.function.Function;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

//@SpringBootTest
class RabbitmqDemoApplicationTests {

    @Test
    void contextLoads() {
//
        System.out.println(StringUtils.leftPad("0011", 4, "0"));
//        String a="1.0";
//        boolean matches = isMatches(a);
//        BigDecimal bigDecimal = new BigDecimal("10.0000");
//        BigDecimal bigDecimal1 = bigDecimal.setScale(2, BigDecimal.ROUND_DOWN);
//        System.out.println(bigDecimal1);
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