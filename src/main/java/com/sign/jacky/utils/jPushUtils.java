package com.sign.jacky.utils;

import cn.jiguang.common.resp.APIConnectionException;
import cn.jiguang.common.resp.APIRequestException;
import cn.jpush.api.JPushClient;
import cn.jpush.api.push.PushResult;
import cn.jpush.api.push.model.Message;
import cn.jpush.api.push.model.Options;
import cn.jpush.api.push.model.Platform;
import cn.jpush.api.push.model.PushPayload;
import cn.jpush.api.push.model.audience.Audience;
import cn.jpush.api.push.model.notification.IosNotification;
import cn.jpush.api.push.model.notification.Notification;

import java.util.List;
import java.util.Map;

public class jPushUtils {

    // 设置好账号的app_key和masterSecret是必须的
    private static String appKey = "ce19fb300735fe44183cf497";
    private static String masterSecret = "4b3d5ba9349949a4f4b1202c";
    //极光推送>>Android
    //Map<String, String> param,同学们可以自定义参数
    public static void jPushAndroid(Map<String, String> param, List<String> registrationIdMap) {
        //创建JPushClient
        JPushClient jpushClient = new JPushClient(masterSecret, appKey);
        //推送的关键,构造一个payload
        PushPayload payload = PushPayload.newBuilder()
                .setPlatform(Platform.android())//指定android平台的用户
                .setAudience(Audience.registrationId(registrationIdMap))
                //发送内容,这里不要盲目复制粘贴,这里是我从controller层中拿过来的参数)
                .setNotification(Notification.android(param.get("msg"), param.get("title"), param))
                //这里是指定开发环境,不用设置也没关系
                //.setOptions(Options.newBuilder().setApnsProduction(false).build())
                .setMessage(Message.content(param.get("msg")))//自定义信息
                .build();
        try {
            PushResult pu = jpushClient.sendPush(payload);
            System.out.println(pu);
        } catch (APIConnectionException | APIRequestException e) {
            e.printStackTrace();
        }
    }

    //极光推送>>ios
    //Map<String, String> parm是我自己传过来的参数,同学们可以自定义参数
    public static  void jPushIOS(Map<String, String> param) {
        //创建JPushClient
        JPushClient jpushClient = new JPushClient(masterSecret, appKey);
        PushPayload payload = PushPayload.newBuilder()
                .setPlatform(Platform.ios())//ios平台的用户
                .setAudience(Audience.all())//所有用户
                .setNotification(Notification.newBuilder()
                        .addPlatformNotification(IosNotification.newBuilder()
                                .setAlert(param.get("msg"))
                                .setBadge(+1)
                                .setSound("happy")
                                .addExtras(param)
                                .build())
                        .build())
                .setOptions(Options.newBuilder().setApnsProduction(false).build())
                .setMessage(Message.newBuilder().setMsgContent(param.get("msg")).addExtras(param).build())//自定义信息
                .build();

        try {
            PushResult pu = jpushClient.sendPush(payload);

        } catch (APIConnectionException | APIRequestException e) {
            e.printStackTrace();
        }
    }
}
