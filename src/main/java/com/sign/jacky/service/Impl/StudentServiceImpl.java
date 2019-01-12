package com.sign.jacky.service.Impl;

import com.sign.jacky.dao.StudentMapper;
import com.sign.jacky.vo.CourseList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.util.List;

@Service
public class StudentServiceImpl implements com.sign.jacky.service.StudentService {

    @Autowired
    StudentMapper studentMapper;

    @Override
    public List<CourseList> getCourseList(BigInteger studentNum) {
        return studentMapper.getCourseListByStudentNum(studentNum);
    }
}
