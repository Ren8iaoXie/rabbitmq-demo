package com.xrb.designMode.strategy;

/**
 * @author xieren8iao
 * @date 2022/3/10 8:30 下午
 */
public class WxPay implements Strategy{

    @Override
    public void calculatePay() {
        System.out.println("微信支付");
    }
}