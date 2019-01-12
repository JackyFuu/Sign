package com.sign.jacky.controller;


import com.alibaba.fastjson.JSONObject;
import com.sign.jacky.service.StudentService;
import com.sign.jacky.vo.CourseList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.math.BigInteger;
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
    @Autowired
    StudentService studentService;

    /**
     * 430010
     * 学生查看个人课表     需要建立一个course_list视图
     * @param map 参数：student_id 
     * @return 课表
     */
    @RequestMapping(value = "/checkClassSchedule")
    public @ResponseBody String checkClassSchedule(@RequestBody Map<String,String> map){
        String studentNum = map.get("student_num");
        BigInteger studentNum1 = new BigInteger(studentNum);
        System.out.println("studentNum1: "+ studentNum1 + "正在查看他的上课表...");
        List<CourseList> courseList = studentService.getCourseList(studentNum1);
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
        String studentNum = map.get("student_num"); //学号
        String teachingTaskId = map.get("teaching_task_id"); //课程任务号
        String routeSeq = map.get("route_seq"); //路由器序列号
        return null;
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
