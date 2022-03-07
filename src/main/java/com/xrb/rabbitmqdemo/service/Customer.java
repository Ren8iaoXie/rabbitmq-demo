package com.xrb.rabbitmqdemo.service;

import com.rabbitmq.client.Channel;
import com.xrb.rabbitmqdemo.dao.MsgLogMapper;
import com.xrb.rabbitmqdemo.model.MsgLog;
import com.xrb.rabbitmqdemo.model.RabbitmqDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.time.LocalDateTime;

/**
 * @author xieren8iao
 * @date 2021/6/25 3:46 下午
 */
@Component
@Slf4j
public class Customer {
    @Autowired
    private MsgLogMapper msgLogMapper;
    /**
     * 如果生产环境你用上述方案的代码，一旦发生一次消费报错你就会崩溃。
     * 因为 basicNack 方法的第三个参数代表是否重回队列，如果你填 false 那么消息就直接丢弃了，相当于没有保障消息可靠。
     * 如果你填 true ，当发生消费报错之后，这个消息会被重回消息队列顶端，继续推送到消费端，继续消费这条消息，
     * 通常代码的报错并不会因为重试就能解决，所以这个消息将会出现这种情况：
     * 继续被消费，继续报错，重回队列，继续被消费......死循环
     * 所以真实的场景一般是三种选择
     * 1、当消费失败后将此消息存到 Redis，记录消费次数，如果消费了三次还是失败，就丢弃掉消息，记录日志落库保存
     * 2、直接填 false ，不重回队列，记录日志、发送邮件等待开发手动处理
     * 3、不启用手动 ack ，使用 SpringBoot 提供的消息重试
     * 作者：暮色妖娆丶
     * 链接：https://zhuanlan.zhihu.com/p/384200726
     * 来源：知乎
     * 著作权归作者所有。商业转载请联系作者获得授权，非商业转载请注明出处。
     */

    /**
     * 默认情况下,如果没有配置手动ACK, 那么Spring Data AMQP 会在消息消费完毕后自动帮我们去ACK
     * 存在问题：如果报错了,消息不会丢失,但是会无限循环消费,一直报错,如果开启了错误日志很容易就吧磁盘空间耗完
     * 解决方案：手动ACK,或者try-catch 然后在 catch 里面将错误的消息转移到其它的系列中去
     * spring.rabbitmq.listener.simple.acknowledge-mode = manual
     *
     * @param dto 监听的内容
     */
    @RabbitListener(queues = "delay_queue")
    @Transactional
    public void cfgUserReceiveDealy(RabbitmqDTO dto, Message message, Channel channel) throws IOException {
        MessageProperties messageProperties = message.getMessageProperties();
        log.info("===============接收队列接收消息====================");
        log.info("接收时间:{},接受内容:{}", LocalDateTime.now(), dto);
        long deliveryTag = messageProperties.getDeliveryTag();
        try {
            Long id = dto.getId();
            Long msgId = dto.getMsgId();
            MsgLog msgLog = msgLogMapper.selectByPrimaryKey(msgId);
            if (msgLog == null || msgLog.getStatus().equals(Byte.valueOf("4"))) {
                log.info("重复消费:" + msgId);
                return;
            }
            log.info("=====收到消息了niupi啊啊啊，id为" + id);
            //TODO 处理消费逻辑
            if (id > 10) {
                msgLog.setStatus((byte) 4);
                int i = msgLogMapper.updateByPrimaryKeyWithBLOBs(msgLog);
                channel.basicAck(deliveryTag, false);
            }
//            int i= (int) (id/0);
            //通知 MQ 消息已被接收,可以ACK(从队列中删除)了
//            int z=1/0;
//            msgLog.setStatus((byte) 4);
//            int i = msgLogMapper.updateByPrimaryKeyWithBLOBs(msgLog);
//            channel.basicAck(deliveryTag, false);
            //TODO 处理            channel.basicNack(tag, false, true);
        } catch (Exception e) {
            log.error("============消费失败,尝试消息补发再次消费!==============");
            log.error(e.getMessage());
            /**
             * basicRecover方法是进行补发操作，
             * 其中的参数如果为true是把消息退回到queue但是有可能被其它的consumer(集群)接收到，
             * 设置为false是只补发给当前的consumer
             */
//            channel.basicNack(deliveryTag, false, true);
//            channel.basicRecover(false);
//            channel.basicNack(deliveryTag,false,true);
        }
    }
}