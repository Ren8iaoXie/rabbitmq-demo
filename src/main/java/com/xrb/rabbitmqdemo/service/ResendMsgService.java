package com.xrb.rabbitmqdemo.service;

import com.xrb.rabbitmqdemo.dao.SDao;
import com.xrb.rabbitmqdemo.model.MsgLog;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author xieren8iao
 * @date 2021/9/22 7:17 下午
 */
@Component
@Slf4j
public class ResendMsgService {
    // 最大投递次数
    private static final int MAX_TRY_COUNT = 3;
    @Autowired
    private SDao sDao;
    @Autowired
    private RabbitTemplate rabbitTemplate;

    /**
     * 每30s拉取投递失败的消息, 重新投递
     */
    @Scheduled(cron = "0/10 * * * * ?")
    public void resend() {
        log.info("开始执行定时任务(重新投递消息)");
        List<MsgLog> msgLogs = sDao.selectTimeoutMsg();
        msgLogs.forEach(msgLog -> {
            Long msgId = msgLog.getId();
            if (msgLog.getTryCount() >= MAX_TRY_COUNT) {
                sDao.updateStatus(msgId.toString(), 3);
                log.info("超过最大重试次数, 消息投递失败, msgId: {}", msgId);
            } else {
                sDao.updateTryCount(msgId.toString());// 投递次数+1
                CorrelationData correlationData = new CorrelationData(String.valueOf(msgId));
                rabbitTemplate.convertAndSend(msgLog.getExchange(), msgLog.getRoutingKey(), msgLog.getMsg(), correlationData);// 重新投递
                log.info("第 " + (msgLog.getTryCount() + 1) + " 次重新投递消息");
            }
        });

        log.info("定时任务执行结束(重新投递消息)");
    }
}