package com.sign.jacky.service.Impl;


import com.sign.jacky.dao.TeacherMapper;
import com.sign.jacky.entity.StartSign;
import com.sign.jacky.entity.Teaching;
import com.sign.jacky.service.TeacherService;
import com.sign.jacky.vo.TeachingList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TeacherServiceImpl implements TeacherService {
    @Autowired
    TeacherMapper teacherMapper;
    @Override
    public List<TeachingList> getTeachingList(int teacherNum) {

        return teacherMapper.getTeachingListByTeacherNum(teacherNum);
    }

    @Override
    public Boolean startSign(StartSign startSign) {
        int row = teacherMapper.startSign(startSign);
        return row>0;
    }
}
