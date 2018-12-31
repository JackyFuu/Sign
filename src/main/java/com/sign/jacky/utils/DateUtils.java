package com.sign.jacky.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtils {
    /**
     * 获取时间戳
     * @return String类型：yyyyMMddHHmmss
     */
    public static String getTimestamp(){
        return new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
    }
}
