package com.xrb.designMode.proxy;

import java.lang.reflect.Proxy;

/**
 * @author xieren8iao
 * @date 2022/4/10 10:41 下午
 */
public class JDKProxyFactory {
    public static Object getProxy(Object target) {
        return Proxy.newProxyInstance(
                target.getClass().getClassLoader(), // 目标类的类加载
                target.getClass().getInterfaces(),  // 代理需要实现的接口，可指定多个
                new MessageServiceProxy(target)  // 代理对象对应的自定义 InvocationHandler
        );
    }
}