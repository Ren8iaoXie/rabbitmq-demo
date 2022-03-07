package com.xrb.java8;

/**
 * @author xieren8iao
 * @date 2022/2/22 4:52 下午
 */
public class Child extends Parent{
    static {
        System.out.println("static child...");
    }
    public Child(){
        System.out.println("construct child...");
    }
    {
        System.out.println("--子类的非静态代码块--");
    }
}