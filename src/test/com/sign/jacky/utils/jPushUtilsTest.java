package com.sign.jacky.utils;

import org.junit.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class jPushUtilsTest {

    @Test
    public void jPushAndroid() {

        List<String> registrationIDList = new ArrayList<>();
        registrationIDList.add("120c83f760624798e15");
        Map<String, String> param = new HashMap<>();
        //文章标题
        param.put("title", "签到任务");
        //设置提示信息,内容是文章标题
        param.put("msg", "同学，你有一条新的签到任务！");
        param.put("startSignId", String.valueOf(1));
        jPushUtils.jPushAndroid(param, registrationIDList);
    }
}