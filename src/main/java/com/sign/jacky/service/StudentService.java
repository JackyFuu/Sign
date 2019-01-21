package com.sign.jacky.service;

import com.sign.jacky.entity.SignIn;
import com.sign.jacky.entity.StartSign;
import com.sign.jacky.vo.CourseList;

import java.util.List;

public interface StudentService {
    List<CourseList> getCourseList(String student_num);

    Boolean sign(SignIn signIn);

    StartSign getSignRequest(String studentNum, Integer schoolId);
}
