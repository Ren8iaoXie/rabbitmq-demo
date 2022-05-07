package com.xrb.rabbitmqdemo.dao;

import com.xrb.rabbitmqdemo.model.MsgLog;
import com.xrb.rabbitmqdemo.model.Student;
import com.xrb.rabbitmqdemo.model.StudentExample;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface SDao {
    /**
     * 批量插入
     * @param list
     */
    void batchAddStudent(List<Student> list);


    List<MsgLog> selectTimeoutMsg();

    void updateStatus(@Param("id") String msgId, @Param("i") int i);

    void updateTryCount(String msgId);

    Integer updateTryCount1();
}