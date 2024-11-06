package com.ivan.course.service.studentGroup;

import com.ivan.course.entity.StudentGroup;

import java.util.List;

public interface StudentGroupService {
    StudentGroup save(StudentGroup studentGroup);
    List<StudentGroup> getGroupsByLanguageAndTeacher(String language, int teacherId);
}
