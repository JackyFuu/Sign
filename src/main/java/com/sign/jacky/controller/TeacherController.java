package com.sign.jacky.controller;


import com.alibaba.fastjson.JSONObject;
import com.sign.jacky.entity.StartSign;
import com.sign.jacky.service.TeacherService;
import com.sign.jacky.vo.TeachingList;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.TimeZone;

/**
 * 模块号：420000
 * 老师功能接口
 * 1、获取授课表              420010
 * 2、发起签到                420020
 * 2、查看补签请求列表         420030
 * 3、同意/拒绝补签请求        420040
 * 4、查看某门课程任务的总的签到记录    420050
 * 5、查看某门课程任务的某次签到记录 420060
 * 6、
 *
 * 
 */
@RequestMapping("/teacher")
@Controller
public class TeacherController {

    private static final Logger logger = LogManager.getLogger("TeacherController");
    @Autowired
    TeacherService teacherService;

    /**
     * 420010
     * 获取授课表：根据老师的职工id(登录时已存在用户手机)，获得老师的授课表
     * @return
     */
    @RequestMapping(value = "/getTeachingList")
    public @ResponseBody  String getTeachingList(@RequestBody Map<String,String> map){
        String teacherNum = map.get("teacher_num");
        logger.info("teacherId: "+Integer.parseInt(teacherNum) + "正在查看他的授课表...");
        List<TeachingList> teachingList = teacherService.getTeachingList(Integer.parseInt(teacherNum));
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
        String routeSeq = map.get("routes_seq"); //默认=123456
        logger.info("teachingTaskId: " + teachingTaskId + "发起签到中..."+",routeSeq= "+routeSeq);
        StartSign startSign = new StartSign();
        //private Integer startSignId;
        startSign.setStartSignId(null);  //无需分装，为null即可
        //private Integer teachingTaskId;
        startSign.setTeachingTaskId(Integer.valueOf(teachingTaskId));
        //private Date sponsorTime;
        Date d = new Date();
        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd kk:mm:ss ");
        sdf.setTimeZone(TimeZone.getTimeZone("Asia/Shanghai"));
        startSign.setSponsorTime(d);
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
     * 420030
     * 查看补签请求列表
     * @return
     */
    @RequestMapping(value = "/checkRetroactive")
    public @ResponseBody String checkRetroactive(@RequestBody Map<String,String> map){
        return null;
    }

    /**
     * 420040
     * 同意/拒绝补签请求
     * @return
     */
    @RequestMapping(value = "/agreeRetroactive")
    public @ResponseBody String agreeRetroactive(@RequestBody Map<String,String> map){
        return null;
    }

    /**
     * 420050
     * 查看某门课程任务的总的签到记录
     * @return
     */
    @RequestMapping(value = "/getCourseSignRecord")
    public @ResponseBody String getCourseSignRecord(@RequestBody Map<String,String> map){
        String teachingTaskId = map.get("teaching_task_id");
        return null;
    }
    /**
     * 420060
     * 查看某门课程任务的某次签到记录
     * @return
     */
    @RequestMapping(value = "/getSingleSignRecord")
    public @ResponseBody String getSingleSignRecord(@RequestBody Map<String,String> map){
        return null;
    }
}
