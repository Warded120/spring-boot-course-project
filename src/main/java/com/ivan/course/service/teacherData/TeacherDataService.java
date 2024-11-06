package com.ivan.course.service.teacherData;

import com.ivan.course.entity.teacher.TeacherData;

import java.util.List;

public interface TeacherDataService {
    TeacherData save(TeacherData teacherData);
    List<TeacherData> findAll();
    List<TeacherData> findOneTwoThreeLanguageTeachers();
}
