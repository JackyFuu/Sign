package com.sign.jacky.service.Impl;


import com.sign.jacky.dao.StudentMapper;
import com.sign.jacky.dao.TeacherMapper;
import com.sign.jacky.dao.UserMapper;
import com.sign.jacky.entity.SignIn;
import com.sign.jacky.entity.StartSign;
import com.sign.jacky.service.TeacherService;
import com.sign.jacky.utils.jPushUtils;
import com.sign.jacky.vo.RetroactiveRequestList;
import com.sign.jacky.vo.SignInVo;
import com.sign.jacky.vo.TeachingList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.beans.Transient;
import java.util.*;

@Service
public class TeacherServiceImpl implements TeacherService {
    @Autowired
    TeacherMapper teacherMapper;

    @Autowired
    StudentMapper studentMapper;

    @Autowired
    UserMapper userMapper;

    @Override
    public List<TeachingList> getTeachingList(String userId) {
        Integer teacherId = userMapper.getAllIdByUid(userId);
        return teacherMapper.getTeachingListByTeacherId(teacherId);
    }

    @Override
    public List<RetroactiveRequestList> getRetroactiveRequestList(String userId) {
        Integer teacherId = userMapper.getAllIdByUid(userId);
        return teacherMapper.getRetroactiveRequestListByTeacherId(teacherId);
    }

    @Override
    public void agreeRetroactive(String signInId) {
        //设置sign_in表的re_sign和state标志位为1
        //设置sign_in表的re_sign标志位和resign_news表的state标志位。
        teacherMapper.agreeRetroactiveBySignInId(signInId);
        //设置resign_news表的state标志位。
        teacherMapper.setResignNewsStateBySignInId(signInId);
    }


    @Transient
    @Override
    public Boolean startSign(StartSign startSign) {
        teacherMapper.startSign(startSign);
        int startSignId = startSign.getStartSignId();
        int teachingTaskId= startSign.getTeachingTaskId();
        //获取选了teachingTaskId的学生学号集合
        List<Integer> studentIdList= teacherMapper.getStudentIdListByTeachingTaskId(teachingTaskId);

        //List<String> studentNumList = studentMapper.getStudentNumListByStudentIdList(studentIdList);
        List<String> registrationIDList = userMapper.getRegistrationIDListByStudentIdList(studentIdList);
        //studentIdList
        Map<String, String> param = new HashMap<>();
        //文章标题
        param.put("title", "签到任务");
        //设置提示信息,内容是文章标题
        param.put("msg", "同学，你有一条新的签到任务！");
        param.put("startSignId", String.valueOf(startSignId));
        jPushUtils.jPushAndroid(param, registrationIDList);

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
            signIn.setSignInTime(startSign.getSponsorTime());
            //private Integer reSign;
            signIn.setReSign(0);
            //private Integer state; //1 已签到； 0 未签到
            signIn.setState(0);
            studentMapper.insertSignInfoBySignIn(signIn);
        }
        return true;
    }

    @Override
    public List<StartSign> getSumStartSignRecordAccordingTeachingTask(String teachingTaskId) {
        return teacherMapper.getSumStartSignRecordByTeachingTaskId(teachingTaskId);
    }

    @Override
    public List<SignInVo> getOnceStartSignRecord(String startSignId) {
        return teacherMapper
                .getOnceStartSignRecordByTeachingTaskIdAndStartSignId(startSignId);
    }
}
