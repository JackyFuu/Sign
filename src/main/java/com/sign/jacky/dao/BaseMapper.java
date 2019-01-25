package com.sign.jacky.dao;

import com.sign.jacky.entity.SignIn;
import com.sign.jacky.entity.StartSign;
import com.sign.jacky.entity.University;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface BaseMapper {
    StartSign getStartSignItem(Integer startSignId);

    int signIn(@Param("signIn") SignIn signIn);

    List<University> getSchoolInfo();
}
