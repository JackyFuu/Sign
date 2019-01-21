package com.sign.jacky.service.Impl;


import com.sign.jacky.dao.StudentMapper;
import com.sign.jacky.dao.TeacherMapper;
import com.sign.jacky.entity.SignIn;
import com.sign.jacky.entity.StartSign;
import com.sign.jacky.entity.Teaching;
import com.sign.jacky.service.TeacherService;
import com.sign.jacky.vo.TeachingList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.beans.Transient;
import java.util.Date;
import java.util.List;

@Service
public class TeacherServiceImpl implements TeacherService {
    @Autowired
    TeacherMapper teacherMapper;

    @Autowired
    StudentMapper studentMapper;
    @Override
    public List<TeachingList> getTeachingList(int teacherNum) {

        return teacherMapper.getTeachingListByTeacherNum(teacherNum);
    }

    @Transient
    @Override
    public Boolean startSign(StartSign startSign) {
        teacherMapper.startSign(startSign);
        int startSignId = startSign.getStartSignId();
        int teachingTaskId= startSign.getTeachingTaskId();
        //获取选了teachingTaskId的学生学号集合
        List<Integer>  studentIdList= teacherMapper.getStudentIdListByTeachingTaskId(teachingTaskId);
        //将签到记录插入sign_in表
        for (int studentId: studentIdList) {
            SignIn signIn = new SignIn();
            //private Integer signInId;
            signIn.setSignInId(null);
            //private Integer studentId;
            signIn.setStudentId(studentId);
            //private Integer teachingTaskId;
            signIn.setTeachingTaskId(teachingTaskId);
            //private Integer startSignId;
            signIn.setStartSignId(startSignId);
            //private Date signInTime;
            signIn.setSignInTime(null);
            //private Integer reSign;
            signIn.setReSign(0);
            //private Integer state; //1 已签到； 0 未签到
            signIn.setState(0);
            studentMapper.insertSignInfoBySignIn(signIn);
        }
        return true;
    }
}
