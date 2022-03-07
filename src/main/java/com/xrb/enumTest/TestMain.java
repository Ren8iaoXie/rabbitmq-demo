package com.xrb.enumTest;

/**
 * @author xieren8iao
 * @date 2021/7/29 8:34 下午
 */
public class TestMain {
    public static void main(String[] args) {
        String sign = "TOUTIAO";
        ChannelRuleEnum channelRule = ChannelRuleEnum.match(sign);
        GeneralChannelRule rule = channelRule.channel;
        rule.process(sign);
    }
}