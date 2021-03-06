package com.sign.jacky.service;

import com.sign.jacky.entity.StartSign;
import com.sign.jacky.vo.RetroactiveRequestList;
import com.sign.jacky.vo.SignInVo;
import com.sign.jacky.vo.TeachingList;

import java.util.List;

public interface TeacherService {


    //List<TeachingList> getTeachingList(int teacherNum);

    Boolean startSign(StartSign startSign);

    List<StartSign> getSumStartSignRecordAccordingTeachingTask(String teachingTaskId);

    List<SignInVo> getOnceStartSignRecord(String startSignId);

    List<TeachingList> getTeachingList(String userId);

    List<RetroactiveRequestList> getRetroactiveRequestList(String userId);

    void agreeRetroactive(String signInId);

    void rejectRetroactive(String signInId);
}
