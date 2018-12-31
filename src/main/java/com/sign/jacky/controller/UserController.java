package com.sign.jacky.controller;

import com.alibaba.fastjson.JSONObject;
import com.sign.jacky.dao.UserMapper;
import com.sign.jacky.entity.University;
import com.sign.jacky.entity.User;
import com.sign.jacky.service.UserService;
import com.sign.jacky.utils.CommonsUtils;
import com.sign.jacky.utils.SMSUtils;
import com.sign.jacky.vo.UserDetail;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Map;

/**
 * 模块号：410000
 */

@Controller
public class UserController {
    
    @Autowired
    UserService userService;

    /**
     * 用户注册
     * @return 410010成功，410011失败  410022 重复手机号
     */
    @RequestMapping(value = "/register")
    public @ResponseBody String register(@RequestBody Map<String,String> map){
        String phoneNum = map.get("phone_number");
        String password = map.get("password");
        
        //判断手机号是否已注册
        Boolean isRepeatRegister = userService.isRepeatRegister(phoneNum);
        if (isRepeatRegister){
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("code","410022");
            jsonObject.put("msg","重复手机号");
            return jsonObject.toJSONString();
        }
        User user = new User();
        //1、private String userId
        user.setUserId(CommonsUtils.getUUID());
        //2    private String phoneNum
        user.setPhoneNum(phoneNum);
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
            //SMSUtils.sendSMS(phoneNumber,code);
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("code","410010");
            jsonObject.put("msg","注册短信已发送");
            return jsonObject.toJSONString();
        } else {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("code","410011");
            jsonObject.put("msg","用户注册失败");
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
        String phone_number =  map.get("phone_number");
        boolean isActiveSuccess = userService.smsActive(code,phone_number);
        if (isActiveSuccess){
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("code","410020");
            jsonObject.put("msg","用户注册成功");
            return jsonObject.toJSONString();
        } else {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("code","410021");
            jsonObject.put("msg","用户注册失败");
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
        String phoneNum = map.get("phone_number");
        String password = map.get("password");
        System.out.println(phoneNum);
        System.out.println(password);
        User user = userService.login(phoneNum, password);
        
        
        if(user!=null && user.getState()==1){
            //返回用户信息，要使用@ResponseBody将返回值转换为JSON
            JSONObject jsonObject = new JSONObject();
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
            if(user.getPosition()!=null){   //已认证
                jsonObject.put("code","410030");
                jsonObject.put("msg","登录成功");
                jsonObject.put("uid", user.getUserId());
                jsonObject.put("user_image", user.getImage());
                jsonObject.put("user_type", user.getPosition());
                int allId = user.getAllId();  //学号/工号序号
                int userType = user.getPosition();
                //获得 姓名，性别，学号；
                Map<String, String> userDetailMap = userService.getUserDetail(allId, userType);
                jsonObject.put("name", userDetailMap.get("name"));
                jsonObject.put("id", userDetailMap.get("id"));
                jsonObject.put("gender", userDetailMap.get("gender"));
                jsonObject.put("school", userDetailMap.get("school"));
                return jsonObject.toJSONString();
            } else {  
                //未认证
                jsonObject.put("code","410031");
                jsonObject.put("msg","登录成功,未认证");
                jsonObject.put("uid", user.getUserId());
                jsonObject.put("user_image", user.getImage());
                return jsonObject.toJSONString();
            }
        } else {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("code","410031");
            jsonObject.put("msg","登录失败");
            
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
        String userType = map.get("user_type");
        String ID = map.get("ID");  //学号等等
        String password = map.get("password");
        String school = map.get("school");
        int schoolId = userService.getSchoolId(school); //获取学校id
        System.out.println("identify:"+uid+" " + ID + " "+ userType + " " + school + " " + password);
        boolean isIdentifySuccess = userService.identifyUser(uid,ID,password,userType,schoolId);
        if(isIdentifySuccess){
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("code","410040");
            jsonObject.put("msg","用户认证成功");
            /**
             * user_type
             * name
             * gender
             * id           //学号/工号
             * school
             */
            jsonObject.put("user_type",userType);
            Integer allId = userService.getAllId(uid);
            Map<String, String> userDetailMap = userService.getUserDetail(allId, Integer.parseInt(userType));
            jsonObject.put("name", userDetailMap.get("name"));
            jsonObject.put("id", userDetailMap.get("id"));
            jsonObject.put("gender", userDetailMap.get("gender"));
            jsonObject.put("school", userDetailMap.get("school"));
            
            return jsonObject.toJSONString();
        } else {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("code","410041");
            jsonObject.put("msg","用户认证失败");
            return jsonObject.toJSONString();
        }
    }

    /**
     * 修改头像
     */

    /**
     * 修改手机号
     */
//    /**
//     * 获得用户个人资料 410050 获得用户个人资料   410051
//     * 
//     * 传入参数
//     * uid
//     * 
//     * @return
//     */
//    @RequestMapping(value = "/personalDetail")
//    public String getPersonalDetail(@RequestBody Map<String,String> map){
//        String uid = map.get("uid");
//        String userType = map.get("user_type");
//        UserDetail userDetail = userService.getUserDetail(uid,userType);  //头像地址
//        
//        JSONObject jsonObject = new JSONObject();
//        jsonObject.put("code","410050");
//        jsonObject.put("msg","获得用户个人资料");
//        return jsonObject.toJSONString();
//    }
    
}
