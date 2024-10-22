package com.ivan.course.service.course;

import com.ivan.course.entity.Course;
import com.ivan.course.entity.student.Student;

import java.util.List;

public interface CourseService {
    Course save(Course course);
    List<Course> findAll();
    Course findById(int id);
    List<Course> findNotEnrolledCoursesByStudent(Student sessionStudent);
    boolean isStudentEnrolled(Course course, Student sessionStudent);
}
