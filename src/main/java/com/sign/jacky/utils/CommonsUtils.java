package com.sign.jacky.utils;

import org.apache.commons.lang3.RandomUtils;

import java.util.UUID;

public class CommonsUtils {

    /**
     * 生成一个id
     * @return 随机生成的id
     */
    public static String getUUID(){
        return UUID.randomUUID().toString();
    }

    /**
     * //生成6位验证码
     * @return 生成一个随机4位数
     */
    public static String getVerificationCode(){
        String num = "";
        for(int i=0; i<6; i++){
            num = num + String.valueOf((int)Math.floor(Math.random()*9 + 1));
        }
        return num;
    }
}
