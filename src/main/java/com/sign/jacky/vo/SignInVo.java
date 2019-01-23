package com.sign.jacky.vo;

import com.sign.jacky.entity.Student;

import java.util.Date;

public class SignInVo {
    private Integer signInId;

    private Student student;

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

    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
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
