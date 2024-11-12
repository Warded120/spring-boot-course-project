package com.ivan.course.service.student;

import com.ivan.course.entity.Course;
import com.ivan.course.entity.student.Student;

import java.util.List;


public interface StudentService {
    Student save(Student student);
    Student save(Student student, boolean isEncodePassword);
    Student save(Student student, boolean isEncodePassword, boolean isSetSession);
    void saveAll(List<Student> students);
    boolean existsByUserId(int id);
    Student findByUserId(int id);
    Student getStudentBySessionStudent(Student sessionStudent);
    List<Course> getCoursesByStudent(Student student);
    Student getSessionStudent();
}
