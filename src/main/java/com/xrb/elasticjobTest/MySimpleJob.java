package com.xrb.elasticjobTest;

import com.dangdang.ddframe.job.api.ShardingContext;
import com.dangdang.ddframe.job.api.simple.SimpleJob;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author xieren8iao
 * @date 2021/8/2 2:36 下午
 */
public class MySimpleJob implements SimpleJob {
    @Override
    public void execute(ShardingContext context) {
        System.out.println(
                String.format("分片项 ShardingItem: %s | 运行时间: %s | 线程ID: %s | 分片参数: %s ",
                        context.getShardingItem(),
                        new SimpleDateFormat("HH:mm:ss").format(new Date()),
                        Thread.currentThread().getId(),
                        context.getShardingParameter())
        );
    }
}