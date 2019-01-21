package com.sign.jacky.dao;

import com.sign.jacky.entity.SignIn;
import com.sign.jacky.entity.StartSign;
import org.apache.ibatis.annotations.Param;

public interface BaseMapper {
    StartSign getStartSignItem(Integer startSignId);

    int signIn(@Param("signIn") SignIn signIn);
}
