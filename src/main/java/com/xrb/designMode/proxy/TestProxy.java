package com.xrb.designMode.proxy;

/**
 * @author xieren8iao
 * @date 2022/4/10 10:42 下午
 */
public class TestProxy {
    public static void main(String[] args) {
        MessageService messageService = new MessageServiceImpl();
        MessageService proxy = (MessageService) JDKProxyFactory.getProxy(messageService);
        proxy.send("123");
    }
}