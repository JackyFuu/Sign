package com.sign.jacky.entity;

public class Instructor {
    private Integer instructorId;

    private String instructorNum;

    private String instructorPass;

    private String instructorName;

    private Integer schoolId;

    private Integer academyId;

    public Integer getInstructorId() {
        return instructorId;
    }

    public void setInstructorId(Integer instructorId) {
        this.instructorId = instructorId;
    }

    public String getInstructorNum() {
        return instructorNum;
    }

    public void setInstructorNum(String instructorNum) {
        this.instructorNum = instructorNum == null ? null : instructorNum.trim();
    }

    public String getInstructorPass() {
        return instructorPass;
    }

    public void setInstructorPass(String instructorPass) {
        this.instructorPass = instructorPass == null ? null : instructorPass.trim();
    }

    public String getInstructorName() {
        return instructorName;
    }

    public void setInstructorName(String instructorName) {
        this.instructorName = instructorName == null ? null : instructorName.trim();
    }

    public Integer getSchoolId() {
        return schoolId;
    }

    public void setSchoolId(Integer schoolId) {
        this.schoolId = schoolId;
    }

    public Integer getAcademyId() {
        return academyId;
    }

    public void setAcademyId(Integer academyId) {
        this.academyId = academyId;
    }
}