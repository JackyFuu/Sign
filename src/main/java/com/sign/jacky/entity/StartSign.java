package com.sign.jacky.entity;

import java.util.Date;

public class StartSign {
    private Integer startSignId;

    private Integer teachingTaskId;

    private Date sponsorTime;

    private String routeSeq;

    public Integer getStartSignId() {
        return startSignId;
    }

    public void setStartSignId(Integer startSignId) {
        this.startSignId = startSignId;
    }

    public Integer getTeachingTaskId() {
        return teachingTaskId;
    }

    public void setTeachingTaskId(Integer teachingTaskId) {
        this.teachingTaskId = teachingTaskId;
    }

    public Date getSponsorTime() {
        return sponsorTime;
    }

    public void setSponsorTime(Date sponsorTime) {
        this.sponsorTime = sponsorTime;
    }

    public String getRouteSeq() {
        return routeSeq;
    }

    public void setRouteSeq(String routeSeq) {
        this.routeSeq = routeSeq == null ? null : routeSeq.trim();
    }
}