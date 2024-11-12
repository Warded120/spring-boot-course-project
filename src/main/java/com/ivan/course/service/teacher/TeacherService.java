package com.ivan.course.service.teacher;


import com.ivan.course.entity.Course;
import com.ivan.course.entity.student.Student;
import com.ivan.course.entity.teacher.Teacher;

import java.util.List;

public interface TeacherService {
    Teacher save(Teacher teacher);
    Teacher save(Teacher teacher, boolean encryptPassword);
    void saveAll(List<Teacher> teachers);
    boolean existsByUserId(int id);
    Teacher findByUserId(int id);
    Teacher getTeacherBySessionTeacher(Teacher sessionTeacher);
    List<Course> getCoursesByTeacher(Teacher teacher);
    Teacher getSessionTeacher();
}
