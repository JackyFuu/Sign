package com.sign.jacky.entity;

import java.util.Date;

public class ReSign {
    private Integer reSignId;

    private Integer studentId;

    private Integer teachingId;

    private Integer state;

    private Integer startSignId;

    private Date reSignTime;

    public Integer getReSignId() {
        return reSignId;
    }

    public void setReSignId(Integer reSignId) {
        this.reSignId = reSignId;
    }

    public Integer getStudentId() {
        return studentId;
    }

    public void setStudentId(Integer studentId) {
        this.studentId = studentId;
    }

    public Integer getTeachingId() {
        return teachingId;
    }

    public void setTeachingId(Integer teachingId) {
        this.teachingId = teachingId;
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    public Integer getStartSignId() {
        return startSignId;
    }

    public void setStartSignId(Integer startSignId) {
        this.startSignId = startSignId;
    }

    public Date getReSignTime() {
        return reSignTime;
    }

    public void setReSignTime(Date reSignTime) {
        this.reSignTime = reSignTime;
    }
}