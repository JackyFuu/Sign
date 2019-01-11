package com.sign.jacky.controller;


import com.alibaba.fastjson.JSONObject;
import com.sign.jacky.entity.StartSign;
import com.sign.jacky.entity.Teaching;
import com.sign.jacky.service.TeacherService;
import com.sign.jacky.utils.CommonsUtils;
import com.sign.jacky.vo.TeachingList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 模块号：420000
 * 老师功能接口
 * 1、获取授课表          420010
 * 2、查看某门课程的签到记录 420020
 * 2、发起签到           420030
 * 3、查看补签请求列表     420040
 * 4、同意/拒绝补签请求      420050
 * 5、查看某门课程的某次签到记录  420060
 * 
 */
@RequestMapping("/teacher")
@Controller
public class TeacherController {
    
    @Autowired
    TeacherService teacherService;

    /**
     * 420010
     * 获取授课表：根据老师的职工id(登录时已存在用户手机)，获得老师的授课表
     * @return
     */
    @RequestMapping(value = "/getTeachingList")
    public @ResponseBody  String getTeachingList(@RequestBody Map<String,String> map){
        String teacher_num = map.get("teacher_num");
        System.out.println("teacherId:"+teacher_num);
        List<TeachingList> teachingList = teacherService.getTeachingList(Integer.parseInt(teacher_num));
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("code","420010");
        jsonObject.put("msg","获取授课表成功");
        jsonObject.put("data",teachingList);
        return jsonObject.toJSONString();
    }
    /**
     * 420020
     * 发起签到 授课id
     * @return
     */
    @RequestMapping(value = "/startSign")
    public @ResponseBody String startSign(@RequestBody Map<String,String> map){
        String teachingTaskId = map.get("teaching_task_id");
        String routeSeq = map.get("route_seq"); //默认=123456
        StartSign startSign = new StartSign();
        //private Integer startSignId;
        startSign.setStartSignId(1);
        //private Integer teachingTaskId;
        startSign.setTeachingTaskId(Integer.valueOf(teachingTaskId));
        //private Date sponsorTime;
        startSign.setSponsorTime(new Date());
        //private String routeSeq;
        startSign.setRouteSeq(routeSeq);

        //startSign封装完毕，将startSign传入service层。
        Boolean isSuccessStartSign = teacherService.startSign(startSign);
        JSONObject jsonObject = new JSONObject();
        if (isSuccessStartSign){
            jsonObject.put("code","420020");
            jsonObject.put("msg","发起签到成功");
            return jsonObject.toJSONString();
        } else {
            jsonObject.put("code","420021");
            jsonObject.put("msg","发起签到失败");
            return jsonObject.toJSONString();
        }
    }

    /**
     * 查看补签请求列表 
     * @return
     */
    @RequestMapping(value = "/checkRetroactive")
    public @ResponseBody String checkRetroactive(@RequestBody Map<String,String> map){
        return null;
    }

    /**
     * 同意/拒绝补签请求
     * @return
     */
    @RequestMapping(value = "/agreeRetroactive")
    public @ResponseBody String agreeRetroactive(@RequestBody Map<String,String> map){
        return null;
    }

    /**
     * 查看某门课程的某次签到记录
     * @return
     */
    @RequestMapping(value = "/getSignRecord")
    public @ResponseBody String getSignRecord(@RequestBody Map<String,String> map){
        return null;
    }
}
