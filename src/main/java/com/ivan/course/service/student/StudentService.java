package com.ivan.course.service.student;

import com.ivan.course.entity.student.Student;

public interface StudentService {
    Student save(Student student);
    boolean existsByUserId(int id);
    Student findByUserId(int id);
}
