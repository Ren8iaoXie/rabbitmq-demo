package com.xrb.designMode.proxy;

/**
 * @author xieren8iao
 * @date 2022/4/10 10:37 下午
 */
public class MessageServiceImpl implements MessageService{
    @Override
    public void send(String msg) {
        System.out.println(msg);
    }
}