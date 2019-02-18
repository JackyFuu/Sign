package com.sign.jacky.service.Impl;

import com.sign.jacky.dao.BaseMapper;
import com.sign.jacky.dao.StudentMapper;
import com.sign.jacky.dao.UserMapper;
import com.sign.jacky.entity.ResignNews;
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
    UserMapper userMapper;

    @Autowired
    BaseMapper baseMapper;

    @Override
    public List<CourseList> getCourseList(String userId) {
        Integer studentId = userMapper.getAllIdByUid(userId);
        return studentMapper.getCourseListByStudentId(studentId);
    }



    /**
     * 根据学号和学校名获取学生id,然后通过select_course表中获取teaching_task_id,
     * 根据teaching_task_id在start_sign表中获取最近的一次发起签到记录，
     * 如果这条记录在查询时的1分钟之内，则返回这条记录，
     * 如果没有找到，则返回startSign = null,
     * 得到该发起签到记录startSign，回传到学生端口
     * @param  userId user_id
     * @return StartSign
     */
    @Override
    public StartSign getSignRequest(String userId) {

        int studentId = userMapper.getAllIdByUid(userId);
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

    @Override
    public Boolean sign(String userId, Date signInTime, int signState, Integer startSignId) {
        //根据userId获取学生id
        int studentId = userMapper.getAllIdByUid(userId);
        //更新签到记录的时间和状态
        int row = studentMapper.signByStudentIdAndStartSignId(studentId, startSignId, signInTime, signState);
        return row>0;
    }

    //@Override
//    public List<SignIn> getAllSignRecord(String userId) {
//        //根据userId获取学生id
//        int studentId = userMapper.getAllIdByUid(userId);
//        return studentMapper.getAllSignRecordByStudentId(studentId);
//    }

    /**
     * 更新签到时间，设置补签标记为1，设置为已签到
     * @param signInId
     * @return
     */
    @Override
    public Boolean retroactive(String signInId) {
        Date newSignInTime = new Date();
        int row = studentMapper.retroactiveByStartSignId(signInId,newSignInTime);
        return row>0;
    }

    @Override
    public List<SignIn> getOneTeachingTaskSignRecord(String userId, String teachingTaskId) {
        //根据userId获取学生id
        int studentId = userMapper.getAllIdByUid(userId);
        return studentMapper.getOneTeachingTaskSignRecordByStudentIdAndTeachingTaskId(studentId, teachingTaskId);
    }

    @Override
    public void saveResignNews(ResignNews resignNews) {
        studentMapper.saveResignNews(resignNews);
    }

}
