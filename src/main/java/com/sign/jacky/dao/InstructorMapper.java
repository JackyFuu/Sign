package com.sign.jacky.dao;

import com.sign.jacky.entity.Profession;
import com.sign.jacky.vo.BeLateStudentList;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;
import java.util.Map;

public interface InstructorMapper {
    Integer getIid(@Param("id") String id, @Param("password") String password,@Param("schoolId") int schoolId);

    Map<String,String> getInstructorDetail(int allId);

    List<Profession> getMajorListByInstructorId(@Param("instructorId") int instructorId);

    List<BeLateStudentList> getConditionalBeLateStudentListByInstructorIdAndSearchTime(
            @Param("instructorId") int instructorId,@Param("searchTime") Date searchTime);
}
