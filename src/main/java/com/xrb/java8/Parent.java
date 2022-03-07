package com.xrb.java8;

/**
 * @author xieren8iao
 * @date 2022/2/22 4:51 下午
 */
public class Parent {
    static {
        System.out.println("static parent...");
    }
    public Parent(){
        System.out.println("construct parent...");
    }

    {
        System.out.println("--父类的非静态代码块--");
    }
}