//package com.sign.jacky.jpush;
//
//import cn.jiguang.common.resp.APIConnectionException;
//import cn.jiguang.common.resp.APIRequestException;
//import cn.jpush.api.JPushClient;
//import cn.jpush.api.push.model.Message;
//import cn.jpush.api.push.model.Options;
//import cn.jpush.api.push.model.Platform;
//import cn.jpush.api.push.model.PushPayload;
//import cn.jpush.api.push.model.audience.Audience;
//import cn.jpush.api.push.model.notification.AndroidNotification;
//import cn.jpush.api.push.model.notification.IosAlert;
//import cn.jpush.api.push.model.notification.IosNotification;
//import cn.jpush.api.push.model.notification.Notification;
//
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//
//public class JPushService {
//    private Boolean production = false;
//    private JPushClient jPushClient;
//
//    public JPushService(String appKey, String masterSecret, Boolean production) {
//        this.production = production;
//        jPushClient = new JPushClient(masterSecret, appKey);
//    }
//
//    /**
//     * 推送给所有客户端
//     */
//    public void sendNotificationToAll(String notificationTitle, String content, Map<String, String> extras) {
//        sendNotification(Audience.all(), notificationTitle, content, extras, PushType.Both);
//    }
//
//    /**
//     * 推送给指定设备标识标签的用户
//     *
//     * @param tagList           设备标签集合
//     * @param notificationTitle 消息内容标题
//     * @param content           消息内容
//     */
//    public void sendNotificationByTags(List<String> tagList, String notificationTitle, String content) {
//        for (String tag : tagList) {
//            sendNotificationByTag(tag, notificationTitle, content);
//        }
//    }
//
//    public void sendNotificationByTags(List<String> tagList, String notificationTitle, String content, Map<String, String> extras) {
//        for (String tag : tagList) {
//            sendNotificationByTag(tag, notificationTitle, content, extras);
//        }
//    }
//
//    public void sendNotificationByTag(String tag, String notificationTitle, String content) {
//        sendNotificationByTag(tag, notificationTitle, content, new HashMap<>());
//    }
//
//    public void sendNotificationByTag(String tag, String notificationTitle, String content, Map<String, String> extras) {
//        sendNotification(Audience.tag(tag), notificationTitle, content, extras, PushType.Both);
//    }
//
//    public void sendNotificationByAlias(List<String> aliasList, String notificationTitle, String content) {
//        for (String alias : aliasList) {
//            sendNotificationByAlias(alias, notificationTitle, content);
//        }
//    }
//
//    public void sendNotificationByAlias(String alias, String notificationTitle, String content) {
//        sendNotificationByAlias(alias, notificationTitle, content, new HashMap<>());
//    }
//
//    public void sendNotificationByAlias(String alias, String notificationTitle, String content, Map<String, String> extras) {
//        if (isEmpty(alias)) return;
//        sendNotification(Audience.alias(alias), notificationTitle, content, extras, PushType.Both);
//    }
//
//    public void sendByAliasOnlyCustomize(String alias, String notificationTitle, String content, Map<String, String> extras) {
//        if (isEmpty(alias)) return;
//        sendNotification(Audience.alias(alias), notificationTitle, content, extras, PushType.Customize);
//    }
//
//    public void sendByAliasOnlyNotification(String alias, String notificationTitle, String content, Map<String, String> extras) {
//        if (isEmpty(alias)) return;
//        sendNotification(Audience.alias(alias), notificationTitle, content, extras, PushType.Notification);
//    }
//
//
//    private void sendNotification(Audience audience, String notificationTitle, String content, Map<String, String> extras, PushType pushType) {
//        PushPayload.Builder builder = PushPayload.newBuilder()
//                .setPlatform(Platform.all())
//                .setAudience(audience);
//        if (PushType.Both.equals(pushType) || PushType.Notification.equals(pushType)) {
//            builder = builder.setNotification(Notification.newBuilder()
//                    .addPlatformNotification(AndroidNotification.newBuilder()
//                            .setAlert(content)
//                            .setTitle(content)
//                            .addExtras(extras)
//                            .build())
//                    .addPlatformNotification(IosNotification.newBuilder()
//                            .setContentAvailable(false)
//                            .setAlert(
//                                    IosAlert.newBuilder().
//                                            setTitleAndBody(notificationTitle, null, content)
//                                            .build()
//                            )
//                            .incrBadge(1)
//                            .setSound("sound.caf")
//                            .addExtras(extras)
//                            .build())
//                    .build());
//        }
//        if (PushType.Both.equals(pushType) || PushType.Customize.equals(pushType)) {
//            builder = builder.setMessage(Message.newBuilder()
//                    .setMsgContent(content)
//                    .setTitle(notificationTitle)
//                    .addExtras(extras)
//                    .build());
//        }
//        builder = builder.setOptions(Options.newBuilder()
//                .setApnsProduction(production)
//                .setSendno(1)
//                .setTimeToLive(86400)
//                .build());
//
//        PushPayload pushPayload = builder.build();
//        try {
//            jPushClient.sendPush(pushPayload);
//        } catch (APIConnectionException | APIRequestException e) {
//            e.printStackTrace();
//        }
//    }
//
//
//}
