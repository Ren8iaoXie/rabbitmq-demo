package com.xrb.rabbitmqdemo.dao;

import com.xrb.rabbitmqdemo.model.MsgLog;
import com.xrb.rabbitmqdemo.model.MsgLogExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface MsgLogMapper {
    long countByExample(MsgLogExample example);

    int deleteByExample(MsgLogExample example);

    int deleteByPrimaryKey(Long id);

    int insert(MsgLog record);

    int insertSelective(MsgLog record);

    List<MsgLog> selectByExampleWithBLOBs(MsgLogExample example);

    List<MsgLog> selectByExample(MsgLogExample example);

    MsgLog selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") MsgLog record, @Param("example") MsgLogExample example);

    int updateByExampleWithBLOBs(@Param("record") MsgLog record, @Param("example") MsgLogExample example);

    int updateByExample(@Param("record") MsgLog record, @Param("example") MsgLogExample example);

    int updateByPrimaryKeySelective(MsgLog record);

    int updateByPrimaryKeyWithBLOBs(MsgLog record);

    int updateByPrimaryKey(MsgLog record);
}