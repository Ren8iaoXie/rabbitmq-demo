package com.xrb.rabbitmqdemo.dao;

import com.xrb.rabbitmqdemo.model.Student;
import com.xrb.rabbitmqdemo.model.StudentExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface StudentDAO {
    long countByExample(StudentExample example);

    int deleteByExample(StudentExample example);

    int deleteByPrimaryKey(Long id);

    int insert(Student record);

    int insertSelective(Student record);

    List<Student> selectByExample(StudentExample example);

    Student selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") Student record, @Param("example") StudentExample example);

    int updateByExample(@Param("record") Student record, @Param("example") StudentExample example);

    int updateByPrimaryKeySelective(Student record);

    int updateByPrimaryKey(Student record);
}