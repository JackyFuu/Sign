package com.sign.jacky.vo;

import com.sign.jacky.entity.Academy;
import com.sign.jacky.entity.University;


public class UserDetail {
    /**
        用户头像地址：可更改
     * 姓名
     * 身份（学生，老师，辅导员）
     * 唯一识别号（学号，教工号）
     * 手机号
     * 性别 1男 2女
     */
    private String image;   //头像地址
    private String name;   //姓名
    private Integer gender;  //性别
    private Integer position; //  身份（学生，老师，辅导员）
    private Integer allId;    //学号/功耗
    private String phoneNum;  //手机号
    private University university; //学校

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getGender() {
        return gender;
    }

    public void setGender(Integer gender) {
        this.gender = gender;
    }

    public Integer getPosition() {
        return position;
    }

    public void setPosition(Integer position) {
        this.position = position;
    }

    public Integer getAllId() {
        return allId;
    }

    public void setAllId(Integer allId) {
        this.allId = allId;
    }

    public String getPhoneNum() {
        return phoneNum;
    }

    public void setPhoneNum(String phoneNum) {
        this.phoneNum = phoneNum;
    }

    public University getUniversity() {
        return university;
    }

    public void setUniversity(University university) {
        this.university = university;
    }

    @Override
    public String toString() {
        return "UserDetail{" +
                "image='" + image + '\'' +
                ", name='" + name + '\'' +
                ", gender=" + gender +
                ", position=" + position +
                ", allId=" + allId +
                ", phoneNum='" + phoneNum + '\'' +
                ", university=" + university +
                '}';
    }
}
