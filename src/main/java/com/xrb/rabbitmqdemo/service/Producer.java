package com.xrb.rabbitmqdemo.service;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.RandomUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.xrb.rabbitmqdemo.controller.RabbitMqController;
import com.xrb.rabbitmqdemo.dao.MsgLogMapper;
import com.xrb.rabbitmqdemo.model.MsgLog;
import com.xrb.rabbitmqdemo.model.RabbitmqDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * @author xieren8iao
 * @date 2021/6/25 3:45 下午
 */
@Component
@Slf4j
public class Producer {
    @Autowired
    private RabbitTemplate rabbitTemplate;
    @Autowired
    private RabbitMqController rabbitMqController;
    @Autowired
    private MsgLogMapper msgLogMapper;
    String exchange = "delay_exchange";
    String routingKey = "delay_key";

    public void sendDelayMessage(RabbitmqDTO dto) {
        System.out.println(JSONObject.toJSONString(dto));
        //这里的消息可以是任意对象，无需额外配置，直接传即可
        log.info("===============延时队列生产消息====================");
        log.info("发送时间:{},发送内容:{}", DateUtil.now(), dto);
        MsgLog msgLog = new MsgLog();
        msgLog.setCompanyId("100151");
        msgLog.setMsgId(RandomUtil.randomString(32));
        msgLog.setExchange(exchange);
        msgLog.setRoutingKey(routingKey);
        msgLog.setStatus((byte) 1);
        msgLog.setTryCount(0);
        msgLog.setRemark("测试");
        msgLog.setGmtCreate(new Date());
        msgLog.setGmtModified(new Date());
        msgLog.setMsg(JSONObject.toJSONString(dto));
        msgLogMapper.insert(msgLog);
        dto.setMsgId(msgLog.getId());
        CorrelationData correlationData = new CorrelationData();
        correlationData.setId(String.valueOf(msgLog.getId()));

        this.rabbitTemplate.convertAndSend(
                exchange,
                routingKey,
                JSON.toJSON(dto),
                message -> {
                    //注意这里时间可以使long，而且是设置header
                    message.getMessageProperties().setHeader("x-delay", dto.getSeconds() * 1000);
                    return message;
                }, correlationData
        );
        log.info("{}ms后执行", dto.getSeconds() * 1000);
    }
}