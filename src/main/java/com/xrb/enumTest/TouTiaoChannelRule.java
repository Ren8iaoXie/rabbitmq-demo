package com.xrb.enumTest;

/**
 * @author xieren8iao
 * @date 2021/7/29 8:33 下午
 */
public class TouTiaoChannelRule extends GeneralChannelRule {
    @Override
    public void process(String sign) {
        // TouTiao处理逻辑
        System.out.println("处理头条");
    }
}