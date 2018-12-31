package com.sign.jacky.entity;

public class TeachingTask {
    private Integer teachingTaskId;

    private Integer teacherId;

    private Integer courseId;

    public Integer getTeachingTaskId() {
        return teachingTaskId;
    }

    public void setTeachingTaskId(Integer teachingTaskId) {
        this.teachingTaskId = teachingTaskId;
    }

    public Integer getTeacherId() {
        return teacherId;
    }

    public void setTeacherId(Integer teacherId) {
        this.teacherId = teacherId;
    }

    public Integer getCourseId() {
        return courseId;
    }

    public void setCourseId(Integer courseId) {
        this.courseId = courseId;
    }
}