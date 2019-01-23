package com.sign.jacky.controller;


import com.alibaba.fastjson.JSONObject;
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

import java.math.BigInteger;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

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
     * @param map 参数：student_id 
     * @return 课表
     */
    @RequestMapping(value = "/checkClassSchedule")
    public @ResponseBody String checkClassSchedule(@RequestBody Map<String,String> map){
        String studentNum = map.get("student_num");
        System.out.println("studentNum1: "+ studentNum + "正在查看他的上课表...");
        List<CourseList> courseList = studentService.getCourseList(studentNum);
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("code","430010");
        jsonObject.put("msg","获取课程表成功");
        jsonObject.put("data",courseList);
        return jsonObject.toJSONString();
    }

    /**
     * 430020
     * 签到 判断签到时间是否在老师发起签到的3分钟内；判断学生的路由序列是否和老师发起的路由序列是一致的
     * @param map ..
     * @return
     */
    @RequestMapping(value = "/sign")
    public @ResponseBody String sign(@RequestBody Map<String,String> map){
        String userId = map.get("user_id");
        Integer routeSeq = Integer.valueOf(map.get("route_seq")); //路由器序列号
        Integer startSignId = Integer.valueOf(map.get("start_sign_id")); //start_sign_id

        logger.info("userId: "+ userId + "正在签到,"  +
                "routeSeq: "+ routeSeq + "startSignId: " + startSignId);

        JSONObject jsonObject = new JSONObject(); //回写签到数据
        int teacherRouteSeq = 0;
        Calendar addOneMinutes = null;
        StartSign startSignItem = baseService.getStartSignItem(startSignId); //得到老师发起签到的Item
        if (startSignItem != null){
            teacherRouteSeq = Integer.parseInt(startSignItem.getRouteSeq()); //老师发起签到的路由器序列
            addOneMinutes = Calendar.getInstance();
            addOneMinutes.setTime(startSignItem.getSponsorTime());
            addOneMinutes.add(Calendar.MINUTE, 1);  //老师发起签到时间加1分钟

            Date SignInTime = new Date(); //学生开始签到时间
            //如果学生签到时间在老师发起签到后的一分钟之内，并且路由器序列相等，则可以签到
            if(SignInTime.before(addOneMinutes.getTime())) {
                if(teacherRouteSeq == routeSeq){
                    int signState = 1; //1: 已签到 0：未签到
                    Boolean isSignInSuccess = studentService.sign(userId, SignInTime, signState, startSignId);
                    if (isSignInSuccess){
                        jsonObject.put("code","430020");
                        jsonObject.put("msg","签到成功");
                        return jsonObject.toJSONString();
                    } else {
                        jsonObject.put("code","430021");
                        jsonObject.put("msg","签到失败，发生系统错误");
                        return jsonObject.toJSONString();
                    }
                } else {
                    jsonObject.put("code","430022");
                    jsonObject.put("msg","没有在指定地点签到");
                    return jsonObject.toJSONString();
                }
            } else {
                jsonObject.put("code","430023");
                jsonObject.put("msg","签到时间已过期");
                return jsonObject.toJSONString();
            }
        } else {
            jsonObject.put("code","430024");
            jsonObject.put("msg","未知错误");
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
        String userId = map.get("user_id"); //uuid

        System.out.println("studentNum1: "+ userId + "正在获取他的签到请求...");
        JSONObject jsonObject = new JSONObject(); //回写签到数据

        StartSign startSign = studentService.getSignRequest(userId);
        if (startSign != null){
            jsonObject.put("code","430030");
            jsonObject.put("msg","获取签到请求成功");
            jsonObject.put("startSignId",startSign.getStartSignId());
            return jsonObject.toJSONString();
        } else {
            jsonObject.put("code","430031");
            jsonObject.put("msg","没有签到请求");
            return jsonObject.toJSONString();
        }
    }
    /**
     * 430030
     * 补签
     * @param map
     * @return
     */
    @RequestMapping(value = "/retroactive")
    public @ResponseBody String retroactive(@RequestBody Map<String,String> map){
        return null;
    }

    /**
     * 430040
     * 某课程的个人签到记录
     * @param map
     * @return
     */
    @RequestMapping(value = "/retroactiveRecord")
    public @ResponseBody String retroactiveRecord(@RequestBody Map<String,String> map){
        return null;
    }
    
    
}
