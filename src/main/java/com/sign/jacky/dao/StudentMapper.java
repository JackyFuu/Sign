package com.sign.jacky.dao;

import com.sign.jacky.vo.CourseList;
import org.apache.ibatis.annotations.Param;

import java.math.BigInteger;
import java.util.List;
import java.util.Map;

public interface StudentMapper {
    
    Integer getSid(@Param("id") String id, @Param("password") String password,@Param("schoolId") int schoolId);

    Map<String,String> getStudentDetail(int allId);

    List<CourseList> getCourseListByStudentNum(BigInteger studentNum);
}
