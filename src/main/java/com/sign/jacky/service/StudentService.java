package com.sign.jacky.service;

import com.sign.jacky.vo.CourseList;

import java.math.BigInteger;
import java.util.List;

public interface StudentService {
    List<CourseList> getCourseList(BigInteger student_num);
}
