package com.sign.jacky.service.Impl;

import com.sign.jacky.dao.InstructorMapper;
import com.sign.jacky.dao.UserMapper;
import com.sign.jacky.entity.Profession;
import com.sign.jacky.service.InstructorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class InstructorServiceImpl implements InstructorService {

    @Autowired
    UserMapper userMapper;

    @Autowired
    InstructorMapper instructorMapper;
    @Override
    public List<Profession> getMajorList(String userId) {
        int instructorId = userMapper.getAllIdByUid(userId);
        return instructorMapper.getMajorListByInstructorId(instructorId);
    }
}
