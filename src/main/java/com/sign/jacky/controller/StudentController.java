package com.sign.jacky.controller;


import com.alibaba.fastjson.JSONObject;
import com.sign.jacky.entity.ResignNews;
import com.sign.jacky.entity.SignIn;
import com.sign.jacky.entity.StartSign;
import com.sign.jacky.service.BaseService;
import com.sign.jacky.service.StudentService;
import com.sign.jacky.vo.CourseList;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.*;

/**
 * 模块号：430000
 * 学生功能接口
 * 1、查看个人课表          430010
 * 2、签到                 430020
 * 3、补签                 430030
 * 4、某课程的个人签到记录    430040
 * 5、
 */
@RequestMapping("/student")
@Controller
public class StudentController {

    private static final Logger logger = LogManager.getLogger("StudentController");
    @Autowired
    StudentService studentService;

    @Autowired
    BaseService baseService;

    /**
     * 430010
     * 学生查看个人课表     需要建立一个course_list视图
     * @param map
     * @return 课表
     */
    @RequestMapping(value = "/checkClassSchedule")
    public @ResponseBody String checkClassSchedule(@RequestBody Map<String,String> map){
        String userId = map.get("userId"); //uuid
        logger.info("userId为："+ userId + "的同学正在查看他的上课表...");
        List<CourseList> courseList = studentService.getCourseList(userId);
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("code","200");
        jsonObject.put("msg","获取课程表成功");
        jsonObject.put("data",courseList);
        return jsonObject.toJSONString();
    }

    /**
     * 430020
     * 签到 判断签到时间是否在老师发起签到的1分钟内；判断学生的路由序列是否和老师发起的路由序列是一致的
     * @param map ..
     * @return
     */
    @RequestMapping(value = "/sign")
    public @ResponseBody String sign(@RequestBody Map<String,String> map){
        String userId = map.get("userId");
        String routeSeq = map.get("routeSeq"); //路由器序列号
        Integer startSignId = Integer.valueOf(map.get("startSignId")); //start_sign_id

        logger.info("userId: "+ userId + "正在签到,"  +
                "routeSeq: "+ routeSeq + "startSignId: " + startSignId);

        JSONObject jsonObject = new JSONObject(); //回写签到数据
        String teacherRouteSeq;
        Calendar addOneMinutes;
        StartSign startSignItem = baseService.getStartSignItem(startSignId); //得到老师发起签到的Item
        if (startSignItem != null){
            teacherRouteSeq = startSignItem.getRouteSeq(); //老师发起签到的路由器序列
            addOneMinutes = Calendar.getInstance();
            addOneMinutes.setTime(startSignItem.getSponsorTime());
            addOneMinutes.add(Calendar.MINUTE, 1);  //老师发起签到时间加1分钟

            Date SignInTime = new Date(); //学生开始签到时间
            //如果学生签到时间在老师发起签到后的一分钟之内，并且路由器序列相等，则可以签到
            if(SignInTime.before(addOneMinutes.getTime())) {
                if(teacherRouteSeq.equals(routeSeq)){
                    int signState = 1; //1: 已签到 0：未签到
                    Boolean isSignInSuccess = studentService.sign(userId, SignInTime, signState, startSignId);
                    if (isSignInSuccess){
                        jsonObject.put("code","200");
                        jsonObject.put("msg","签到成功");
                        jsonObject.put("data","");
                        return jsonObject.toJSONString();
                    } else {
                        jsonObject.put("code","430021");
                        jsonObject.put("msg","签到失败，发生系统错误");
                        jsonObject.put("data","");
                        return jsonObject.toJSONString();
                    }
                } else {
                    jsonObject.put("code","430022");
                    jsonObject.put("msg","没有在指定地点签到");
                    jsonObject.put("data","");
                    return jsonObject.toJSONString();
                }
            } else {
                jsonObject.put("code","430023");
                jsonObject.put("msg","签到时间已过期");
                jsonObject.put("data","");
                return jsonObject.toJSONString();
            }
        } else {
            jsonObject.put("code","430024");
            jsonObject.put("msg","未知错误");
            jsonObject.put("data","");
            return jsonObject.toJSONString();
        }
    }

    /**
     * 430030
     * 获取签到要求 根据user_id获得学生的学号，在根据学号获取学生的签到teaching_task_id  start_sign_id
     * 根据学号和学校名获取学生id,然后通过select_course表中获取teaching_task_id,
     * 根据teaching_task_id在start_sign表中获取最近的一次发起签到记录，
     * 得到该发起签到记录的start_sign_id，回传到学生端口
     * @param map
     * @return
     */
    @RequestMapping(value = "/getSignRequest")
    public @ResponseBody String getSignRequest(@RequestBody Map<String,String> map){
        String userId = map.get("userId"); //uuid

        logger.info("userId: "+ userId + "正在获取他的签到请求...");
        JSONObject jsonObject = new JSONObject(); //回写签到数据

        StartSign startSign = studentService.getSignRequest(userId);
        if (startSign != null){
            jsonObject.put("code","200");
            jsonObject.put("msg","获取签到任务成功");
            Map<String, String> dataMap = new HashMap<>();
            dataMap.put("startSignId", String.valueOf(startSign.getStartSignId()));
            jsonObject.put("data",dataMap);
            return jsonObject.toJSONString();
        } else {
            jsonObject.put("code","430031");
            jsonObject.put("msg","没有签到任务");
            jsonObject.put("data","");
            return jsonObject.toJSONString();
        }
    }

    /**
     * 430040
     * 根据学生uidId和teachingTaskId获取获取该学生某上课任务的的所有签到记录
     * @param map
     * @return
     */
    @RequestMapping(value = "/getOneTeachingTaskSignRecord")
    public @ResponseBody String getOneTeachingTaskSignRecord(@RequestBody Map<String,String> map){
        String userId = map.get("userId"); //uuid
        String teachingTaskId = map.get("teachingTaskId");
        logger.info("获取用户： "+ userId + "学生的上课任务："+teachingTaskId+ "所有签到记录成功");
        List<SignIn> signInList = studentService.getOneTeachingTaskSignRecord(userId, teachingTaskId);
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("code","200");
        jsonObject.put("msg","获取学生的某上课任务的所有签到记录成功");
        jsonObject.put("data",signInList);
        return jsonObject.toJSONString();
    }


    /**
     * 430050
     * 发起补签请求  补签需要在发起签到的10分钟内进行补签
     * @param map
     * @return
     */
    @RequestMapping(value = "/requestRetroactive")
    public @ResponseBody String requestRetroactive(@RequestBody Map<String,String> map){
       
        Integer signInId = Integer.valueOf(map.get("signInId"));
        Boolean isTwoRequestRetroactive = studentService.isTwoRequestRetroactive(signInId);
        if (isTwoRequestRetroactive) {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("code","200");
            jsonObject.put("msg","签到任务只能发起一次补签请求");
            jsonObject.put("data","");
            return jsonObject.toJSONString();
        }
        String resignReason = map.get("resignReason");
        logger.info("正在补签签到编号为： "+ signInId + " 的签到记录中..., 补签原因为：resignReason" + resignReason);
        ResignNews resignNews = new ResignNews();
//        private Integer resignNewsId;
//        private Integer signInId;
        resignNews.setSignInId(signInId);
//        private String resignReason;
        resignNews.setResignReason(resignReason);
//        private Integer state;
        resignNews.setState(1); //1 代表为未处理，2代表同意，3代表拒绝

//        private Date createTime;
        resignNews.setCreateTime(new Date());
        logger.info("正在补签签到编号为： "+ signInId + " 的签到记录中...");
        studentService.saveResignNews(resignNews);
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("code","200");
        jsonObject.put("msg","发起补签请求成功");
        jsonObject.put("data","");
        return jsonObject.toJSONString();
    }

    /**
     * 430060
     * 某课程的个人签到记录
     * @param map
     * @return
     */
    @RequestMapping(value = "/retroactiveRecord")
    public @ResponseBody String retroactiveRecord(@RequestBody Map<String,String> map){
        return null;
    }
    
    
}
