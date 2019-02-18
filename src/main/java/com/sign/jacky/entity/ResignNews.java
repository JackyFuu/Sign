package com.sign.jacky.entity;

import java.util.Date;

public class ResignNews {
    private Integer resignNewsId;

    private Integer signInId;

    private String resignReason;

    private Integer state;

    private Date createTime;

    public Integer getResignNewsId() {
        return resignNewsId;
    }

    public void setResignNewsId(Integer resignNewsId) {
        this.resignNewsId = resignNewsId;
    }

    public Integer getSignInId() {
        return signInId;
    }

    public void setSignInId(Integer signInId) {
        this.signInId = signInId;
    }

    public String getResignReason() {
        return resignReason;
    }

    public void setResignReason(String resignReason) {
        this.resignReason = resignReason == null ? null : resignReason.trim();
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}