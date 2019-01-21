package com.sign.jacky.service.Impl;

import com.sign.jacky.dao.BaseMapper;
import com.sign.jacky.dao.StudentMapper;
import com.sign.jacky.entity.SignIn;
import com.sign.jacky.entity.StartSign;
import com.sign.jacky.vo.CourseList;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Service
public class StudentServiceImpl implements com.sign.jacky.service.StudentService {

    private static final Logger logger = LogManager.getLogger("StudentServiceImpl");
    @Autowired
    StudentMapper studentMapper;

    @Autowired
    BaseMapper baseMapper;

    @Override
    public List<CourseList> getCourseList(String studentNum) {
        return studentMapper.getCourseListByStudentNum(studentNum);
    }

    @Override
    public Boolean sign(SignIn signIn) {
        return null;
    }


    /**
     * 根据学号和学校名获取学生id,然后通过select_course表中获取teaching_task_id,
     * 根据teaching_task_id在start_sign表中获取最近的一次发起签到记录，
     * 如果这条记录在查询时的1分钟之内，则返回这条记录，
     * 如果没有找到，则返回startSign = null,
     * 得到该发起签到记录startSign，回传到学生端口
     * @param studentNum 学号
     * @param schoolId 学校名
     * @return StartSign
     */
    @Override
    public StartSign getSignRequest(String studentNum, Integer schoolId) {
        //根据学号和学校名获取学生id
        int studentId= studentMapper.getSidByStudentNumAndSchoolId(studentNum,schoolId);
        //然后通过select_course表中获取teaching_task_id列表
        List<Integer> teachingTaskIdList = studentMapper.getTeachingTaskIdListBySid(studentId);
        //根据teaching_task_id在start_sign表中获取最近的一次发起签到记录
        for ( Integer teachingTaskId: teachingTaskIdList) {
            StartSign startSign = studentMapper.getNewestStartSignByTeachingTaskId(teachingTaskId);
            // 如果这条记录在查询时的1分钟之内，则返回这条记录，
            if (startSign != null) {
                Calendar addOneMinutes = Calendar.getInstance();
                Date startSignTime = startSign.getSponsorTime();
                addOneMinutes.setTime(startSignTime);
                addOneMinutes.add(Calendar.MINUTE, 1);  //老师发起签到时间加1分钟

                if (new Date().before(addOneMinutes.getTime())) {
                    return startSign;
                }
            } else {
                continue;
            }
        }
        //如果没有找到，则返回startSign = null
        return null;
    }
}
