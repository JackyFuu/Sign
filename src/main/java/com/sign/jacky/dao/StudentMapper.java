package com.sign.jacky.dao;

import com.sign.jacky.entity.SignIn;
import com.sign.jacky.entity.StartSign;
import com.sign.jacky.vo.CourseList;
import org.apache.ibatis.annotations.Param;

import java.math.BigInteger;
import java.util.List;
import java.util.Map;

public interface StudentMapper {
    
    Integer getSid(@Param("id") String id, @Param("password") String password,@Param("schoolId") int schoolId);

    Map<String,String> getStudentDetail(int allId);

    List<CourseList> getCourseListByStudentNum(String studentNum);

    Integer getSidByStudentNumAndSchoolId(@Param("studentNum") String studentNum,
                                      @Param("schoolId") Integer schoolId);

    List<Integer> getTeachingTaskIdListBySid(int studentId);

    StartSign getNewestStartSignByTeachingTaskId(Integer teachingTaskId);

    void insertSignInfoBySignIn(@Param("signIn") SignIn signIn);
}
