package com.ivan.course.service.teacher;


import com.ivan.course.entity.teacher.Teacher;

public interface TeacherService {
    Teacher save(Teacher teacher);
    Teacher save(Teacher teacher, boolean encryptPassword);
    boolean existsByUserId(int id);

    Teacher findByUserId(int id);
}
