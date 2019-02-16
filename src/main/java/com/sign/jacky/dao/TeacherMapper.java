package com.sign.jacky.dao;

import com.sign.jacky.entity.StartSign;
import com.sign.jacky.vo.SignInVo;
import com.sign.jacky.vo.TeachingList;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface TeacherMapper {
    Integer getTid(@Param("id") String id, @Param("password") String password,@Param("schoolId") int schoolId);

    Map<String,String> getTeacherDetail(int allId);

    void startSign(@Param("startSign") StartSign startSign);

    List<Integer> getStudentIdListByTeachingTaskId(int teachingTaskId);

    List<StartSign> getSumStartSignRecordByTeachingTaskId(String teachingTaskId);

    List<SignInVo> getOnceStartSignRecordByTeachingTaskIdAndStartSignId(
            @Param("startSignId") String startSignId);

    //List<TeachingList> getTeachingListByUserId(@Param("userId") String userId);

    List<TeachingList> getTeachingListByTeacherId(@Param("teacherId") Integer teacherId);
}
