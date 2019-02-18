package com.sign.jacky.service;

import com.sign.jacky.entity.Profession;

import java.util.List;

public interface InstructorService {
    List<Profession> getMajorList(String userId);
}
