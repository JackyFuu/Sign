package com.sign.jacky.dao;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class UserMapperTest {
    @Autowired
    UserMapper userMapper;

    @Test
    public void getRegistrationIDListByStudentIdList() {
        List<Integer> studentIdList = new ArrayList<>();
        studentIdList.add(1);
        studentIdList.add(4);
        List<String> registrationIDList =  userMapper.getRegistrationIDListByStudentIdList(studentIdList);

        System.out.println(registrationIDList);
    }
}