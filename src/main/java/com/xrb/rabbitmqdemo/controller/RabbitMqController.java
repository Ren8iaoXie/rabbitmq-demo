package com.xrb.rabbitmqdemo.controller;
import java.util.Date;
import java.util.*;

import cn.hutool.core.convert.Convert;
import com.xrb.rabbitmqdemo.CheckEmojiFlag;
import com.xrb.rabbitmqdemo.dao.MsgLogMapper;
import com.xrb.rabbitmqdemo.dao.SDao;
import com.xrb.rabbitmqdemo.model.MsgLog;
import com.xrb.rabbitmqdemo.model.RabbitmqDTO;
import com.xrb.rabbitmqdemo.model.Student;
import com.xrb.rabbitmqdemo.service.Producer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author xieren8iao
 * @date 2021/6/25 3:50 下午
 */
@RestController
@Slf4j
public class RabbitMqController {
    @Autowired
    private Producer producer;
    public ThreadLocal myThreadLocal = new ThreadLocal();
    @Autowired
    private SDao sDao;
    @Autowired
    private MsgLogMapper msgLogMapper;
    @RequestMapping("send")
    public void send(RabbitmqDTO dto){
        Map<String,Object> resultMap=new HashMap<>();
        resultMap.put("haah",1);
        myThreadLocal.set(resultMap);
        producer.sendDelayMessage(dto);
        getThreadLocal();
    }

    @RequestMapping("test1")
    @Transactional
     public String test1 (RabbitmqDTO dto) throws InterruptedException {
        Integer i=sDao.updateTryCount1();
        Thread.sleep(2000);
        if (i>0){
            log.info("success");
        }else {
            log.error("fail");
        }
        return "成功";
    }
    @RequestMapping("batchInsert")
    public void batchInsert(RabbitmqDTO dto){
        List<Student> list=new ArrayList<>();
        for (int i = 0; i < 50000; i++) {
            Student student=new Student();
            student.setName("牛"+i);
            student.setAge(18);
            student.setGmtCreate(new Date());
            student.setGmtModified(new Date());
            list.add(student);
        }
        long start=System.currentTimeMillis();
        sDao.batchAddStudent(list);
        System.out.println(System.currentTimeMillis()-start);
    }


    private void getThreadLocal() {
        String s="Abc123";
        String s1 = new StringBuilder().append(Character.toLowerCase(s.charAt(0))).append(s.substring(1)).toString();
        System.out.println(s1);

    }

}