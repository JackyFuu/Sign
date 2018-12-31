package com.sign.jacky.controller;


import com.sign.jacky.service.TeacherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 模块号：420000
 * 
 * 老师功能接口
 * 1、获取授课表          420010
 * 2、查看某门课程的签到记录 420020
 * 2、发起签到           420030
 * 3、查看补签请求列表     420040
 * 4、同意/拒绝补签请求      420050
 * 5、查看某门课程的某次签到记录  420060
 * 
 */
@Controller
public class TeacherController {
    
    @Autowired
    TeacherService teacherService;

    /**
     * 获取授课表：根据老师的uid。得到老师的职工id，
     * @return
     */
    @RequestMapping(value = "/getTeachingList")
    public @ResponseBody  String getTeachingList(){
        return null;
    }

    /**
     * 发起签到
     * @return
     */
    @RequestMapping(value = "/startSign")
    public @ResponseBody String startSign(){
        return null;
    }

    /**
     * 查看补签请求列表
     * @return
     */
    @RequestMapping(value = "/checkRetroactive")
    public @ResponseBody String checkRetroactive(){
        return null;
    }

    /**
     * 同意/拒绝补签请求
     * @return
     */
    @RequestMapping(value = "/agreeRetroactive")
    public @ResponseBody String agreeRetroactive(){
        return null;
    }
}
