package com.sign.jacky.dao;

import org.apache.ibatis.annotations.Param;

import java.util.Map;

public interface InstructorMapper {
    Integer getIid(@Param("id") String id, @Param("password") String password,@Param("schoolId") int schoolId);

    Map<String,String> getInstructorDetail(int allId);
}