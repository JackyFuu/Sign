package com.sign.jacky.controller;

import com.alibaba.fastjson.JSONObject;
import com.sign.jacky.entity.Profession;
import com.sign.jacky.service.InstructorService;
import com.sign.jacky.utils.DateUtils;
import com.sign.jacky.vo.BeLateStudentList;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 模块号：440000
 */
@RequestMapping("/instructor")
@Controller
public class InstructorController {

    private static final Logger logger = LogManager.getLogger("InstructorController");

    @Autowired
    InstructorService instructorService;

//    /**
//     * 辅导员获取所带专业
//     * @param map userId
//     * @return professionList
//     */
//    @RequestMapping(value = "/getMajorList")
//    public @ResponseBody String getMajorList(@RequestBody Map<String,String> map){
//        String userId = map.get("userId");
//        logger.info("userId: "+ userId + " 正在TA的辅导员获取所带专业...");
//        List<Profession> professionList = instructorService.getMajorList(userId);
//        JSONObject jsonObject = new JSONObject();
//        jsonObject.put("code","200");
//        jsonObject.put("msg","获取辅导员获取所带专业");
//        jsonObject.put("data",professionList);
//        return jsonObject.toJSONString();
//    }

//    /**
//     * getCertainMajorCourseList
//     * @param map
//     * @return
//     */
//    @RequestMapping(value = "/getCertainMajorCourseList")
//    public @ResponseBody String getCertainMajorCourseList(@RequestBody Map<String,String> map){
//        String professionId = map.get("professionId");
//        logger.info("professionId: "+ professionId + " 正在TA的辅导员获取所带专业...");
//        return null;
//    }

    @RequestMapping(value = "/getConditionalBeLateStudentList")
    public @ResponseBody String getConditionalBeLateStudentList(@RequestBody Map<String,String> map){
        String userId = map.get("userId");
        Integer state = Integer.valueOf(map.get("state")); // 1 今天 2 这周 3 本月 4 本学期
        Date searchTime = new Date();
        switch (state){
            case 1:
                searchTime = DateUtils.getTimesMorning();
                break;
            case 2:
                searchTime = DateUtils.getTimesWeekMorning();
                break;
            case 3:
                searchTime = DateUtils.getTimesMonthMorning();
                break;
            case 4:
                searchTime = DateUtils.getCurrentYearStartTime();
                break;
        }
        logger.info("teacherId: "+ userId + "正在有条件的（" +state+ "）获取迟到学生的信息"
            + searchTime);
        List<BeLateStudentList> beLateStudentLists = instructorService.
                getConditionalBeLateStudentList(userId, searchTime);
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("code","200");
        jsonObject.put("msg","获取迟到学生记录成功");
        jsonObject.put("data",beLateStudentLists);
        return jsonObject.toJSONString();
    }
}
