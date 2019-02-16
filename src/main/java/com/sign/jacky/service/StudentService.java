package com.sign.jacky.service;

import com.sign.jacky.entity.SignIn;
import com.sign.jacky.entity.StartSign;
import com.sign.jacky.vo.CourseList;

import java.util.Date;
import java.util.List;

public interface StudentService {
    List<CourseList> getCourseList(String userId);

    StartSign getSignRequest(String userId);

    Boolean sign(String userId, Date signInTime, int signState, Integer startSignId);

    //List<SignIn> getAllSignRecord(String userId);

    Boolean retroactive(String signInId);

    List<SignIn> getOneTeachingTaskSignRecord(String userId, String teachingTaskId);
}
