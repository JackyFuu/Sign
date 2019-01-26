package com.sign.jacky.controller;

import com.alibaba.fastjson.JSONObject;
import com.sign.jacky.dao.UserMapper;
import com.sign.jacky.entity.University;
import com.sign.jacky.entity.User;
import com.sign.jacky.service.UserService;
import com.sign.jacky.utils.CommonsUtils;
import com.sign.jacky.utils.SMSUtils;
import com.sign.jacky.vo.UserDetail;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 模块号：410000
 * 1、用户注册   410010
 * 2、用户激活   410020
 * 3、用户登录   410030
 * 4、用户认证   410040
 * 5、修改头像   410050
 * 6、修改手机号  410060
 * 7、
 */

@Controller
public class UserController {

    private static final Logger logger = LogManager.getLogger("UserController");
    
    @Autowired
    UserService userService;

    /**
     * 用户注册
     * @return 410010成功，410011失败  410022 重复手机号
     */
    @RequestMapping(value = "/register")
    public @ResponseBody String register(@RequestBody Map<String,String> map){
        String phoneNumber = map.get("phoneNumber");
        String password = map.get("password");

        logger.info("phoneNumber: "+ phoneNumber + "password: " + password + " 正在进行用户注册中...");
        //判断手机号是否已注册
        Boolean isRepeatRegister = userService.isRepeatRegister(phoneNumber);
        JSONObject jsonObject = new JSONObject();
        if (isRepeatRegister){
            jsonObject.put("code","410022");
            jsonObject.put("msg","重复手机号");
            jsonObject.put("data","");
            return jsonObject.toJSONString();
        }
        User user = new User();
        //1、private String userId
        user.setUserId(CommonsUtils.getUUID());
        //2    private String phoneNum
        user.setPhoneNum(phoneNumber);
        //3    private String pwd
        user.setPwd(password);
        //4    private Integer allId
        user.setAllId(null);
        // 5   private Integer position
        user.setPosition(null);
        // 6   private String image;
        user.setImage("/images/icon/ali.png");
        // 7   private Integer state
        user.setState(0);
        // 8   private String code
        //String code = CommonsUtils.getVerificationCode();
        String code = "123456";
        user.setCode(code);
        System.out.println("code: "+code);

        //user传递到service层
        boolean isRegisterSuccess = userService.register(user);
        //是否注册成功
        if(isRegisterSuccess){
            //SMSUtils.sendSMS(phoneNum,code);
            jsonObject.put("code","200");
            jsonObject.put("msg","注册短信已发送");
            jsonObject.put("data","");
            return jsonObject.toJSONString();
        } else {
            jsonObject.put("code","410011");
            jsonObject.put("msg","用户注册失败");
            jsonObject.put("data","");
            return jsonObject.toJSONString();
        }
    }

    /**
     * 用户激活 
     * @return 410020 用户注册成功 410021 用户注册成功
     */

    @RequestMapping(value = "/active")
    public @ResponseBody String active(@RequestBody Map<String,String> map){
        String code = map.get("code");
        String phoneNumber =  map.get("phoneNumber");
        logger.info("phoneNumber: "+ phoneNumber + "code: " + code + " 正在激活用户中...");
        boolean isActiveSuccess = userService.smsActive(code,phoneNumber);
        JSONObject jsonObject = new JSONObject();
        if (isActiveSuccess){
            jsonObject.put("code","200");
            jsonObject.put("msg","用户注册成功");
            jsonObject.put("data","");
            return jsonObject.toJSONString();
        } else {
            jsonObject.put("code","410021");
            jsonObject.put("msg","用户注册失败");
            jsonObject.put("data","");
            return jsonObject.toJSONString();
        }
    }
    
    /**
     * 用户登录
     * @return  410030 登录成功 410031 登录失败
     */
      //返回Json
    @RequestMapping(value = "/login")
    public @ResponseBody String login(@RequestBody Map<String,String> map){
        //使用map.get方法得到JSON中id对应的值
        String phoneNum = map.get("phoneNumber");
        String password = map.get("password");

        logger.info("phoneNum: "+ phoneNum + "password: " + password + " 正在登陆中...");
        User user = userService.login(phoneNum, password);

        JSONObject jsonObject = new JSONObject();
        if(user!=null && user.getState()==1){
            //返回用户信息，要使用@ResponseBody将返回值转换为JSON

            //添加用户信息：
            /**
             * uid           //uid
             * user_image
             * user_type
             * 
             * name
             * gender
             * id           //学号/工号
             * school
             */
            Map<String, String> dataMap = new HashMap<>();
            if(user.getPosition()!=null){   //已认证
                jsonObject.put("code","200");
                jsonObject.put("msg","登录成功");
                //封装data
                dataMap.put("uid", user.getUserId());
                dataMap.put("userImage", user.getImage());
                dataMap.put("userType", String.valueOf(user.getPosition()));
                int allId = user.getAllId();  //学号/工号序号
                int userType = user.getPosition();
                //获得 姓名，性别，学号；
                Map<String, String> userDetailMap = userService.getUserDetail(allId, userType);
                dataMap.put("name", userDetailMap.get("name"));
                dataMap.put("id", userDetailMap.get("id"));
                dataMap.put("gender", userDetailMap.get("gender"));
                dataMap.put("school", userDetailMap.get("school"));
                jsonObject.put("data", dataMap);
                return jsonObject.toJSONString();
            } else {  
                //未认证
                jsonObject.put("code","200");
                jsonObject.put("msg","登录成功,未认证");
                dataMap.put("uid", user.getUserId());
                dataMap.put("userImage", user.getImage());
                jsonObject.put("data",dataMap);
                return jsonObject.toJSONString();
            }
        } else {
            jsonObject.put("code","410031");
            jsonObject.put("msg","登录失败");
            jsonObject.put("data","");
            return jsonObject.toJSONString();
        }
    }
    
    
    /**
     * 用户认证   410040 用户认证成功     410041 用户认证失败
     * 通过用户传输过来的{用户，学号/工号，密码，用户类型}验证相应用户表中是否有该用户，
     * 如果有，则将{sid/instructorId/tid}保存到用户表的allId字段。此则完成认证。
     * @return  410040 用户认证成功 410041 用户认证失败
     */
    
    @RequestMapping(value = "/identify")
    public @ResponseBody String identify(@RequestBody Map<String,String> map){
        String uid = map.get("uid");
        // user_type:  1学生  2辅导员  3教师
        String userType = map.get("userType");
        String ID = map.get("ID");  //学号等等
        String password = map.get("password");
        Integer schoolId = Integer.valueOf(map.get("schoolId"));

        logger.info("uid:"+uid+" 正在认证中... ID为：" + ID + "用户类型为："+
                userType + "学校id为：" + schoolId + "教务系统密码为： " + password);

        boolean isIdentifySuccess = userService.identifyUser(uid,ID,password,userType,schoolId);
        JSONObject jsonObject = new JSONObject();
        if(isIdentifySuccess){
            jsonObject.put("code","200");
            jsonObject.put("msg","用户认证成功");
            /**
             * user_type
             * name
             * gender
             * id           //学号/工号
             * school
             */
            Map<String, String> dataMap = new HashMap<>();
            dataMap.put("userType",userType);
            Integer allId = userService.getAllId(uid);
            Map<String, String> userDetailMap = userService.getUserDetail(allId, Integer.parseInt(userType));
            dataMap.put("name", userDetailMap.get("name"));
            dataMap.put("id", userDetailMap.get("id")); //学号/工号
            dataMap.put("gender", userDetailMap.get("gender"));
            dataMap.put("school", userDetailMap.get("school"));
            jsonObject.put("data", dataMap);
            return jsonObject.toJSONString();
        } else {
            jsonObject.put("code","410041");
            jsonObject.put("msg","用户认证失败");
            jsonObject.put("data","");
            return jsonObject.toJSONString();
        }
    }

    /**
     * 修改头像 410050
     * @param map
     * @return
     */
    @RequestMapping(value = "/changeIcon")
    public @ResponseBody String changeIcon(@RequestBody Map<String,String> map){
        String uid = map.get("uid");
        Boolean isChangeIconSuccess = userService.changeIcon(uid);
        if(isChangeIconSuccess){
            return null;
        } else {
            return null;
        }
    }

    /**
     * 修改手机号  410060 修改手机号成功  410061 修改手机号失败
     * @param map
     * @return
     */
    @RequestMapping(value = "/changePhoneNumber")
    public @ResponseBody String changePhoneNumber(@RequestBody Map<String,String> map){
        String uid = map.get("uid");
        String newPhoneNumber = map.get("newPhoneNumber");
        Boolean isChangePhoneNumberSuccess = userService.changePhoneNumber(uid, newPhoneNumber);
        JSONObject jsonObject = new JSONObject();
        if(isChangePhoneNumberSuccess){
            jsonObject.put("code","200");
            jsonObject.put("phone_number",newPhoneNumber);
            jsonObject.put("msg","修改手机号成功");
            jsonObject.put("data","");
            return jsonObject.toJSONString();
        } else {
            jsonObject.put("code","410061");
            jsonObject.put("msg","修改手机号失败");
            jsonObject.put("data","");
            return jsonObject.toJSONString();
        }
    }

    /**
     * 修改密码  410070 修改密码成功  410071 修改密码失败
     * @param map
     * @return
     */
    @RequestMapping(value = "/changePassword")
    public @ResponseBody String changePassword(@RequestBody Map<String,String> map){
        String uid = map.get("uid");
        String oldPassword = map.get("oldPassword");
        String newPassword = map.get("newPassword");
        logger.info("uid: "+ uid + " 正在修改登录密码中... 旧密码为："+ oldPassword +"新密码为："+ newPassword);
        Boolean isChangePhoneNumberSuccess = userService.changePassword(uid, oldPassword,newPassword);
        JSONObject jsonObject = new JSONObject();
        if(isChangePhoneNumberSuccess){
            jsonObject.put("code","200");
            jsonObject.put("msg","修改密码成功");
            jsonObject.put("data","");
            return jsonObject.toJSONString();
        } else {
            jsonObject.put("code","410071");
            jsonObject.put("msg","修改密码失败");
            jsonObject.put("data","");
            return jsonObject.toJSONString();
        }
    }

    /**
     * 410080 获取所有学校信息   没有入参的情况下不需要接收参数
     * @return
     */
    @RequestMapping(value = "/getSchoolInfo")
    public @ResponseBody String getSchoolInfo(){
        logger.info(" 获取学校信息中...");
        List<University> universityList= userService.getSchoolInfo();
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("code","200");
        jsonObject.put("msg","获取所有学校信息成功");
        jsonObject.put("data",universityList);
        return jsonObject.toJSONString();
    }

}
