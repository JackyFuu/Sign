package com.sign.jacky.service.Impl;

import com.sign.jacky.dao.*;
import com.sign.jacky.entity.University;
import com.sign.jacky.entity.User;
import com.sign.jacky.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
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

    @Autowired
    BaseMapper baseMapper;
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
    public boolean identifyUser(String uid, String id, String password, String userType, int schoolId) {
        //Uuid为学生/教工在数据库中的唯一标识
        Integer uuid;
        switch (userType) {  //通过{user_type，id(学号/工号)，password，schoolId}查到用户的学校资料，返回用户学校唯一标识
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
            userMapper.setPositionAndUUID(uid,userType,uuid);  //设置用户职位
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

    @Override
    public Boolean changeIcon(String userId, String newImage) {
        int row = userMapper.changeIconByUserId(userId, newImage);
        return row > 0;
    }

    @Override
    public Boolean changePhoneNumber(String uid, String newPhoneNumber) {
        int row = userMapper.updatePhoneNumberByUid(uid,newPhoneNumber);
        return row>0;
    }

    @Override
    public Boolean changePassword(String uid, String oldPassword, String newPassword) {
        int row = userMapper.updatePasswordByUid(uid,oldPassword,newPassword);
        return row>0;
    }

    @Override
    public List<University> getSchoolInfo() {
        return baseMapper.getSchoolInfo();
    }

    @Override
    public Boolean setRegistrationID(String userId, String registrationID) {
        int row = userMapper.setRegistrationIDByUserId(userId, registrationID);
        return row>0;
    }


}
