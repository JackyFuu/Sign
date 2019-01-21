package com.sign.jacky.entity;


import java.util.Date;

public class SignIn {
    private Integer signInId;

    private Integer studentId;

    private Integer teachingTaskId;

    private Integer startSignId;

    private Date signInTime;

    private Integer reSign;

    private Integer state; //1 已签到； 0 未签到

    public Integer getSignInId() {
        return signInId;
    }

    public void setSignInId(Integer signInId) {
        this.signInId = signInId;
    }

    public Integer getStudentId() {
        return studentId;
    }

    public void setStudentId(Integer studentId) {
        this.studentId = studentId;
    }

    public Integer getTeachingTaskId() {
        return teachingTaskId;
    }

    public void setTeachingTaskId(Integer teachingTaskId) {
        this.teachingTaskId = teachingTaskId;
    }

    public Integer getStartSignId() {
        return startSignId;
    }

    public void setStartSignId(Integer startSignId) {
        this.startSignId = startSignId;
    }

    public Date getSignInTime() {
        return signInTime;
    }

    public void setSignInTime(Date signInTime) {
        this.signInTime = signInTime;
    }

    public Integer getReSign() {
        return reSign;
    }

    public void setReSign(Integer reSign) {
        this.reSign = reSign;
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }
}