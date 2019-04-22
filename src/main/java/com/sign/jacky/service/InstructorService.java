package com.sign.jacky.service;

import com.sign.jacky.entity.Profession;
import com.sign.jacky.vo.BeLateStudentList;

import java.util.Date;
import java.util.List;

public interface InstructorService {
    List<Profession> getMajorList(String userId);

    List<BeLateStudentList> getConditionalBeLateStudentList(String userId, Date searchTime);
}
