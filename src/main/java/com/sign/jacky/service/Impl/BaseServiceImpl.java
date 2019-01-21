package com.sign.jacky.service.Impl;

import com.sign.jacky.dao.BaseMapper;
import com.sign.jacky.dao.StudentMapper;
import com.sign.jacky.entity.StartSign;
import com.sign.jacky.entity.Student;
import com.sign.jacky.service.BaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BaseServiceImpl implements BaseService {

    @Autowired
    BaseMapper baseMapper;


    @Override
    public StartSign getStartSignItem(Integer startSignId) {
        return baseMapper.getStartSignItem(startSignId);
    }


}
