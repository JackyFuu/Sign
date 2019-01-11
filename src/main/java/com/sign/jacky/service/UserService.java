package com.sign.jacky.service;

import com.sign.jacky.entity.User;
import com.sign.jacky.vo.UserDetail;

import java.util.Map;

public interface UserService {
    User login(String phoneNum, String password);

    boolean register(User user);

    boolean smsActive(String code, String phone_number);

    boolean identifyUser(String uid, String id, String password, String user_type, int schoolId);

    Boolean isRepeatRegister(String phoneNum);

    int getSchoolId(String school);

    Map<String,String> getUserDetail(int allId, int userType);

    Integer getAllId(String uid);

    Boolean changeIcon(String uid);

    Boolean changePhoneNumber(String uid, String newPhoneNumber);
}