package com.sign.jacky.entity;

import java.util.Date;

public class SignIn {
    private Integer signInId;

    private Integer studentId;

    private Integer courseId;

    private Integer startSignId;

    private Date signInTime;

    private Integer reSign;

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

    public Integer getCourseId() {
        return courseId;
    }

    public void setCourseId(Integer courseId) {
        this.courseId = courseId;
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
}