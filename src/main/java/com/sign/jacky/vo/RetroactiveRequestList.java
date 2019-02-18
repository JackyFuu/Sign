package com.sign.jacky.vo;

import java.util.Date;

public class RetroactiveRequestList {
    private Integer teacherId;

    private String studentName;

    private String courseName;

    private String resignReason;

    private Integer signInId;

    private Date createTime;

    public Integer getTeacherId() {
        return teacherId;
    }

    public void setTeacherId(Integer teacherId) {
        this.teacherId = teacherId;
    }

    public String getStudentName() {
        return studentName;
    }

    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public String getResignReason() {
        return resignReason;
    }

    public void setResignReason(String resignReason) {
        this.resignReason = resignReason;
    }

    public Integer getSignInId() {
        return signInId;
    }

    public void setSignInId(Integer signInId) {
        this.signInId = signInId;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}
