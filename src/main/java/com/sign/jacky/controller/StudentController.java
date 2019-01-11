package com.sign.jacky.controller;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Map;

/**
 * 模块号：430000
 * 学生功能接口
 * 1、查看个人课表
 * 2、签到
 * 3、补签
 * 4、某课程的个人签到记录
 * 5、
 */
@Controller
public class StudentController {

    /**
     * 学生查看个人课表  
     * @param map 参数：student_id 
     * @return 课表
     */
    @RequestMapping(value = "/checkClassSchedule")
    public @ResponseBody String checkClassSchedule(@RequestBody Map<String,String> map){
        return null;
    }

    /**
     * 签到
     * @param map ..
     * @return
     */
    @RequestMapping(value = "/sign")
    public @ResponseBody String sign(@RequestBody Map<String,String> map){
        return null;
    }

    /**
     * 补签
     * @param map
     * @return
     */
    @RequestMapping(value = "/retroactive")
    public @ResponseBody String retroactive(@RequestBody Map<String,String> map){
        return null;
    }

    /**
     * 某课程的个人签到记录
     * @param map
     * @return
     */
    @RequestMapping(value = "/retroactiveRecord")
    public @ResponseBody String retroactiveRecord(@RequestBody Map<String,String> map){
        return null;
    }
    
    
}
