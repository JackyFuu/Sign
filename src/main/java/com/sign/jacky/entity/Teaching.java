package com.sign.jacky.entity;

public class Teaching {
    private Integer teachingId;

    private Integer teachingTaskId;

    private String weekTime;

    private Integer classTime;

    private Integer startTime;

    private Integer courseLength;

    private String teachingPlace;

    public Integer getTeachingId() {
        return teachingId;
    }

    public void setTeachingId(Integer teachingId) {
        this.teachingId = teachingId;
    }

    public Integer getTeachingTaskId() {
        return teachingTaskId;
    }

    public void setTeachingTaskId(Integer teachingTaskId) {
        this.teachingTaskId = teachingTaskId;
    }

    public String getWeekTime() {
        return weekTime;
    }

    public void setWeekTime(String weekTime) {
        this.weekTime = weekTime == null ? null : weekTime.trim();
    }

    public Integer getClassTime() {
        return classTime;
    }

    public void setClassTime(Integer classTime) {
        this.classTime = classTime;
    }

    public Integer getStartTime() {
        return startTime;
    }

    public void setStartTime(Integer startTime) {
        this.startTime = startTime;
    }

    public Integer getCourseLength() {
        return courseLength;
    }

    public void setCourseLength(Integer courseLength) {
        this.courseLength = courseLength;
    }

    public String getTeachingPlace() {
        return teachingPlace;
    }

    public void setTeachingPlace(String teachingPlace) {
        this.teachingPlace = teachingPlace == null ? null : teachingPlace.trim();
    }
}