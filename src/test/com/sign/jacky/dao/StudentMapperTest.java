package com.sign.jacky.dao;

import com.sign.jacky.entity.ResignNews;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;

import static org.junit.Assert.*;

public class StudentMapperTest {
    @Autowired
    StudentMapper studentMapper;

    @Test
    public void saveResignNews() {
        ResignNews resignNews = new ResignNews();
//        private Integer resignNewsId;
//        private Integer signInId;
        resignNews.setSignInId(356);
//        private String resignReason;
        resignNews.setResignReason("我就是迟到了而已！");
//        private Integer state;
        resignNews.setState(0); //0 未处理； 1 已处理
//        private Date createTime;
        resignNews.setCreateTime(new Date());
        studentMapper.saveResignNews(resignNews);
    }
}