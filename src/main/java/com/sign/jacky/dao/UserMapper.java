package com.sign.jacky.dao;


import com.sign.jacky.entity.User;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface UserMapper {

    int insert(User record);
    
    int smsActive(@Param("code") String code, @Param("phoneNum") String phone_number);

    User login(@Param("phoneNum") String phoneNum, @Param("password") String password);

    void setPositionAndUUID(@Param("uid") String uid, 
                            @Param("user_type") String user_type, 
                            @Param("uuid") int uuid);

    String isRepeatRegister(@Param("phoneNum") String phoneNum);

    int getSchoolId(String school);

    Integer getAllIdByUid(String uid);

    int updatePhoneNumberByUid(@Param("uid") String uid,
                               @Param("newPhoneNumber") String newPhoneNumber);

    int updatePasswordByUid( @Param("uid") String uid,
                             @Param("oldPassword") String oldPassword,
                             @Param("newPassword") String newPassword);

    //List<String> getUserIdListByStudentIdList(List<Integer> studentIdList);


}