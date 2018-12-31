package com.sign.jacky.entity;

public class User {
    private String userId;

    private String phoneNum;

    private String pwd;

    private Integer allId;

    private Integer position;

    private String image;

    private String code;

    private Integer state;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId == null ? null : userId.trim();
    }

    public String getPhoneNum() {
        return phoneNum;
    }

    public void setPhoneNum(String phoneNum) {
        this.phoneNum = phoneNum == null ? null : phoneNum.trim();
    }

    public String getPwd() {
        return pwd;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd == null ? null : pwd.trim();
    }

    public Integer getAllId() {
        return allId;
    }

    public void setAllId(Integer allId) {
        this.allId = allId;
    }

    public Integer getPosition() {
        return position;
    }

    public void setPosition(Integer position) {
        this.position = position;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image == null ? null : image.trim();
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code == null ? null : code.trim();
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    @Override
    public String toString() {
        return "User{" +
                "userId='" + userId + '\'' +
                ", phoneNum='" + phoneNum + '\'' +
                ", pwd='" + pwd + '\'' +
                ", allId=" + allId +
                ", position=" + position +
                ", image='" + image + '\'' +
                ", code='" + code + '\'' +
                ", state=" + state +
                '}';
    }
}