package com.sign.jacky.service.Impl;

import com.sign.jacky.dao.InstructorMapper;
import com.sign.jacky.dao.StudentMapper;
import com.sign.jacky.dao.TeacherMapper;
import com.sign.jacky.dao.UserMapper;
import com.sign.jacky.entity.User;
import com.sign.jacky.service.UserService;
import com.sign.jacky.vo.UserDetail;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    UserMapper userMapper;
    
    @Autowired
    StudentMapper studentMapper;
    @Autowired
    TeacherMapper teacherMapper;
    @Autowired
    InstructorMapper instructorMapper;
    @Override
    public User login(String phoneNum, String password) {
        return userMapper.login(phoneNum, password);
    }

    @Override
    public boolean register(User user) {
        int row = userMapper.insert(user);
        return row>0;
    }

    @Override
    public boolean smsActive(String code, String phone_number) {
        int row = userMapper.smsActive(code,phone_number);
        return row>0;
    }

    @Override
    public boolean identifyUser(String uid, String id, String password, String user_type, int schoolId) {
        Integer uuid;
        switch (user_type) {  //通过{user_type，id，password，schoolId}查到用户的学校资料，返回用户学校唯一标识
            case "1":
                uuid = studentMapper.getSid(id, password, schoolId);
                break;
            case "2":
                uuid = instructorMapper.getIid(id, password, schoolId);
                break;
            case "3":
                uuid = teacherMapper.getTid(id, password, schoolId);
                break;
            default:
                uuid = null;
                break;
        }
        if(uuid!=null){
            userMapper.setPositionAndUUID(uid,user_type,uuid);  //设置用户职位
            return true;
        } else {
            return false;
        }
    }

    @Override
    public Boolean isRepeatRegister(String phoneNum) {
        String phoneNum1 = userMapper.isRepeatRegister(phoneNum);
        return phoneNum.equals(phoneNum1);
            
    }

    @Override
    public int getSchoolId(String school) {
        return userMapper.getSchoolId(school);
    }

    @Override
    public Map<String, String> getUserDetail(int allId, int userType) {
        Map<String, String> map = new HashMap<>();
        switch (userType) {  // user_type:  1学生  2辅导员  3教师
            case 1:
                map = studentMapper.getStudentDetail(allId);
                break;
            case 2:
                map = instructorMapper.getInstructorDetail(allId);
                break;
            case 3:
                map = teacherMapper.getTeacherDetail(allId);
                break;
        }
        return map;
    }

    @Override
    public Integer getAllId(String uid) {
        
        return userMapper.getAllIdByUid(uid);
    }

//    @Override
//    public UserDetail getUserDetail(String uid, String userType) {
//        
//        UserDetail userDetail = new UserDetail();
//        Map<String, String> map1 = userMapper.getUserDetail(uid);  
//        //性别，学号，学校，
//        
//        //private String image;   //头像地址   user
//        //userDetail.setImage();
////        private String name;   //姓名   student/teacher/...
////        private Integer gender;  //性别  student/teacher/...
////        private Integer position; //  身份（学生，老师，辅导员）
//        userDetail.setPosition(Integer.valueOf(userType));
////        private Integer allId;    //学号/功耗  student
//        userDetail.setAllId();
////        private University university; //学号  student
//        return null;
//    }
}
