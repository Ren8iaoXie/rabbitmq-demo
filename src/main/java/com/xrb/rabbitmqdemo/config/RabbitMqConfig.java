package com.xrb.rabbitmqdemo.config;

import cn.hutool.json.JSON;
import com.alibaba.fastjson.JSONObject;
import com.xrb.rabbitmqdemo.dao.SDao;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.CustomExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.Map;

/**
 * @author xieren8iao
 * @date 2021/6/25 3:44 下午
 */
@Configuration
@Slf4j
public class RabbitMqConfig {
    @Autowired
    private RabbitTemplate rabbitTemplate;
    @Autowired
    private CachingConnectionFactory connectionFactory;
    @Autowired
    private SDao sDao;
    @PostConstruct
    public void enableConfirmCallback() {
        //confirm 监听，当消息成功发到交换机 ack = true，没有发送到交换机 ack = false
        //correlationData 可在发送时指定消息唯一 id
        rabbitTemplate.setConfirmCallback((correlationData, ack, cause) -> {
            String msgId = correlationData.getId();
            if(!ack){
                //记录日志、发送邮件通知、落库定时任务扫描重发
                log.info(cause);
                log.info(JSONObject.toJSONString(correlationData));
            }else {
                log.info("消息成功发送到Exchange"+msgId
                );
                sDao.updateStatus(msgId, 2);
            }
        });
        rabbitTemplate.setMandatory(true);
        //当消息成功发送到交换机没有路由到队列触发此监听
        rabbitTemplate.setReturnsCallback(returned -> {
            //记录日志、发送邮件通知、落库定时任务扫描重发
            log.info(JSONObject.toJSONString(returned));
        });
    }

    /**
     * 延时队列交换机
     * 注意这里的交换机类型：CustomExchange
     * @return
     */
    @Bean
    public CustomExchange delayExchange(){
        Map<String, Object> args = new HashMap<>();
        args.put("x-delayed-type", "direct");
        return new CustomExchange("delay_exchange","x-delayed-message",true, false,args);
    }

    /**
     * 延时队列
     * @return
     */
    @Bean
    public Queue delayQueue(){
        return new Queue("delay_queue",true);
    }

    /**
     * 给延时队列绑定交换机
     * @return
     */
    @Bean
    public Binding cfgDelayBinding(Queue cfgDelayQueue, CustomExchange cfgUserDelayExchange){
        return BindingBuilder.bind(cfgDelayQueue).to(cfgUserDelayExchange).with("delay_key").noargs();
    }

    /**
     * 配置的消息转换器
     * @return
     */
    @Bean
    public MessageConverter messageConverter(){
        return new Jackson2JsonMessageConverter();
    }
}