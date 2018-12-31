package com.sign.jacky.utils;

import com.github.qcloudsms.SmsSingleSender;
import com.github.qcloudsms.SmsSingleSenderResult;
import com.github.qcloudsms.httpclient.HTTPException;
import org.json.JSONException;

import java.io.IOException;

public class SMSUtils {

    /**
     * 发送注册短信
     * @param phoneNumber 发送对象的手机号
     * @param code 验证码
     * @return 1表示发送成功，0表示未成功
     */
    public static String sendSMS(String phoneNumber,String code) {
        String reStr = ""; //定义返回值
        // 短信应用SDK AppID
        int appid = 1400165498; // 1400开头
        // 短信应用SDK AppKey
        String appkey = "c45e3ecac0916bd5f5dc2694559c5ae8";
        // 需要发送短信的手机号码
        //String[] phoneNumbers = {"15807305759"};
        // 短信模板ID，需要在短信应用中申请
        int templateId = 241219; // NOTE: 这里的模板ID`7839`只是一个示例，真实的模板ID需要在短信控制台中申请

        // 签名
        String smsSign = "厚脸皮的大长嘴公众号"; // NOTE: 这里的签名"腾讯云"只是一个示例
        // ，真实的签名需要在短信控制台中申请，另外签名参数使用的是`签名内容`，而不是`签名ID`
        try {
            String[] params = {code, "2"};
            SmsSingleSender ssender = new SmsSingleSender(appid, appkey);
            SmsSingleSenderResult result = ssender.sendWithParam("86", phoneNumber,
                    templateId, params, smsSign, "", "");  // 签名参数未提供或者为空时，会使用默认签名发送短信
            System.out.println(result);
            if(result.result==0){
                reStr = "1"; //成功
            }else {
                reStr = "0"; //未成功
            }
        } catch (HTTPException e) {
            // HTTP响应码错误
            e.printStackTrace();
        } catch (JSONException e) {
            // json解析错误
            e.printStackTrace();
        } catch (IOException e) {
            // 网络IO错误
            e.printStackTrace();
        }
        return reStr;
    }
}
