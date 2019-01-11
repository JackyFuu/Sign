package com.sign.jacky.service;

import com.sign.jacky.entity.StartSign;
import com.sign.jacky.entity.Teaching;
import com.sign.jacky.vo.TeachingList;

import java.util.List;

public interface TeacherService {


    List<TeachingList> getTeachingList(int teacherNum);

    Boolean startSign(StartSign startSign);
}
