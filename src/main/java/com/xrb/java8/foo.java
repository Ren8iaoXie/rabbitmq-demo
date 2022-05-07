package com.xrb.java8;

import cn.hutool.core.util.ObjectUtil;
import com.google.common.collect.Lists;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author xieren8iao
 * @date 2022/4/11 9:37 上午
 */
public class foo {
    public static void main(String[] args) {
        List<TestData> list=new ArrayList<>();
        Set<Long> ids = list.stream().filter(a -> ObjectUtil.isNotEmpty(a.getCompanyId())).map(TestData::getId).collect(Collectors.toSet());
        System.out.println(ids);
    }
}