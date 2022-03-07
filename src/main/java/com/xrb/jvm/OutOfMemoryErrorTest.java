package com.xrb.jvm;

import java.util.ArrayList;
import java.util.List;

/**
 * @author xieren8iao
 * @date 2021/12/30 2:50 下午
 */
public class OutOfMemoryErrorTest
{
    public static void main(String[] args) {
        System.out.println("jvm");
        List<Object> list=new ArrayList<>();
        while (true){
            list.add(new Object());
        }
    }
}