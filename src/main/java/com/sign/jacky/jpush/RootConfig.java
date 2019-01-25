//package com.sign.jacky.jpush;
//
//import org.springframework.context.EnvironmentAware;
//import org.springframework.context.annotation.*;
//import org.springframework.core.env.Environment;
//import org.springframework.stereotype.Controller;
//import org.springframework.web.bind.annotation.ControllerAdvice;
//
//
//@Configuration
//@EnableAspectJAutoProxy(proxyTargetClass = true) //启用自动代理功能。
//@PropertySource(value = {"classpath:jpush.properties"})
//@ComponentScan(basePackages = {"com.sign.jacky.jpush"},
//        excludeFilters = {@ComponentScan.Filter(type = FilterType.ANNOTATION, value = {Controller.class, ControllerAdvice.class})})
//public class RootConfig implements EnvironmentAware {
//
//    private Environment environment;
//    @Override
//    public void setEnvironment(Environment environment) {
//        this.environment = environment;
//    }
//
//    @Bean
//    public JPushService JPushService() {
//        String appKey = environment.getProperty("jpush.owner.app.key");
//        String masterSecret = environment.getProperty("jpush.owner.master.secret");
//        Boolean production = Boolean.valueOf(environment.getProperty("jpush.production"));
//        return new JPushService(appKey, masterSecret, production);
//    }
//}
