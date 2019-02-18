package com.sign.jacky.controller;

import com.alibaba.fastjson.JSONObject;
import com.sign.jacky.entity.Profession;
import com.sign.jacky.service.InstructorService;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

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

    /**
     * 辅导员获取所带专业
     * @param map userId
     * @return professionList
     */
    @RequestMapping(value = "/getMajorList")
    public @ResponseBody String getMajorList(@RequestBody Map<String,String> map){
        String userId = map.get("userId");
        logger.info("userId: "+ userId + " 正在TA的辅导员获取所带专业...");
        List<Profession> professionList = instructorService.getMajorList(userId);
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("code","200");
        jsonObject.put("msg","获取辅导员获取所带专业");
        jsonObject.put("data",professionList);
        return jsonObject.toJSONString();
    }

    /**
     * getCertainMajorCourseList
     * @param map
     * @return
     */
    @RequestMapping(value = "/getCertainMajorCourseList")
    public @ResponseBody String getCertainMajorCourseList(@RequestBody Map<String,String> map){
        String professionId = map.get("professionId");
        logger.info("professionId: "+ professionId + " 正在TA的辅导员获取所带专业...");
        return null;
    }
}
