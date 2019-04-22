package com.sign.jacky.controller;


import com.alibaba.fastjson.JSONObject;
import com.sign.jacky.entity.StartSign;
import com.sign.jacky.service.TeacherService;
import com.sign.jacky.vo.RetroactiveRequestList;
import com.sign.jacky.vo.SignInVo;
import com.sign.jacky.vo.TeachingList;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.text.SimpleDateFormat;
import java.util.*;

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
        //String teacherNum = map.get("teacherNum");
        String userId = map.get("userId");
        logger.info("teacherId: "+userId + "正在查看他的授课表...");
        List<TeachingList> teachingList = teacherService.getTeachingList(userId);
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("code","200");
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
        String teachingTaskId = map.get("teachingTaskId");
        String routeSeq = map.get("routeSeq"); //默认=123456
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
            jsonObject.put("code","200");
            jsonObject.put("msg","发起签到成功");
            jsonObject.put("data","");
            return jsonObject.toJSONString();
        } else {
            jsonObject.put("code","420021");
            jsonObject.put("msg","发起签到失败");
            jsonObject.put("data","");
            return jsonObject.toJSONString();
        }
    }

    /**
     * 420030
     * 查看补签请求列表
     * @return
     */
    @RequestMapping(value = "/getRetroactiveRequestList")
    public @ResponseBody String getRetroactiveRequestList(@RequestBody Map<String,String> map){
        String userId = map.get("userId");
        logger.info("userId(老师）: " + userId + "查看补签请求列表...");
        List<RetroactiveRequestList> retroactiveRequestList= teacherService.getRetroactiveRequestList(userId);
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("code","200");
        jsonObject.put("msg","查看补签请求列表成功");
        jsonObject.put("data",retroactiveRequestList);
        return jsonObject.toJSONString();

    }

    /**
     * 420040
     * 同意/拒绝补签请求
     * @return
     */
    @RequestMapping(value = "/agreeRetroactive")
    public @ResponseBody String agreeRetroactive(@RequestBody Map<String,String> map){
        String signInId = map.get("signInId");
        String isAgree = map.get("isAgree");  //1 表示同意， 0表示拒绝
        logger.info("正在同意/拒绝signInId: " + signInId + "的补签请求...");
        //如果同意则设置标志位，如果不同意则不做任何操作
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("code","200");
        if (isAgree.equals("1")){
            //设置sign_in表的re_sign和state标志位为1,resign_news表的state标志位为1.
            teacherService.agreeRetroactive(signInId);
            jsonObject.put("msg","同意补签请求");
            Map<String, String> dataMap = new HashMap<>();
            dataMap.put("state", "1");
            jsonObject.put("data",dataMap);
            return jsonObject.toJSONString();
        } else {
            //设置resign_news表的state标志位为2.
            teacherService.rejectRetroactive(signInId);
            jsonObject.put("msg","拒绝补签请求");
            Map<String, String> dataMap1 = new HashMap<>();
            dataMap1.put("state", "2");
            jsonObject.put("data",dataMap1);
            return jsonObject.toJSONString();
        }
    }

    /**
     * 420050
     * 查看某门课程任务的总的签到记录
     * 根据teaching_task_id查询start_sign表，查询该授课任务的发起签到记录。
     *
     * @return
     */
    @RequestMapping(value = "/getStartSignRecord")
    public @ResponseBody String getStartSignRecord(@RequestBody Map<String,String> map){
        String teachingTaskId = map.get("teachingTaskId");
        logger.info("查看授课任务：" + teachingTaskId + "的所有发起签到记录中...");
        List<StartSign> startSignList = teacherService.
                getSumStartSignRecordAccordingTeachingTask(teachingTaskId);
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("code","200");
        jsonObject.put("msg","获取课程任务发起签到记录成功");
        jsonObject.put("data",startSignList);
        return jsonObject.toJSONString();
    }
    /**
     * 420060
     * 查看某门课程任务的某次签到记录 根据start_sign_id查询某次发起签到的签到记录
     * @return
     */
    @RequestMapping(value = "/getSingleSignRecord")
    public @ResponseBody String getSingleSignRecord(@RequestBody Map<String,String> map){
        String startSignId = map.get("startSignId");
        logger.info("查看签到编号为："+ startSignId + " 的所有学生签到记录中...");
        List<SignInVo> signInList = teacherService.getOnceStartSignRecord(startSignId);
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("code","200");
        jsonObject.put("msg","查看某门课程任务的某次签到记录成功");
        jsonObject.put("data",signInList);
        return jsonObject.toJSONString();
    }
}
