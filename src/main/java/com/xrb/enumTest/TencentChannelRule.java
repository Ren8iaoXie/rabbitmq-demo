package com.xrb.enumTest;

/**
 * @author xieren8iao
 * @date 2021/7/29 8:31 下午
 */
public class TencentChannelRule extends GeneralChannelRule{
        @Override
        public void process(String sign) {
            // Tencent处理逻辑
                System.out.println("处理腾讯");
        }
}