package com.ivan.course.service.course;

import com.ivan.course.constants.CourseState;
import com.ivan.course.entity.Course;
import com.ivan.course.entity.student.Student;

import java.util.List;

public interface CourseService {
    Course save(Course course);
    void saveAll(List<Course> courses);
    List<Course> findAll();
    List<Course> findByCourseStateNotEqual(CourseState state);
    Course findById(int id);
    List<Course> findNotEnrolledCoursesByStudent(Student sessionStudent);
    List<Course> findNotEnrolledCoursesByStudentAndStateNot(Student sessionStudent, CourseState state);
    boolean isStudentEnrolled(Course course, Student sessionStudent);
    List<Course> findCoursesWithStudentCountLessThan(int count);
    List<Course> findCoursesWithStudentCountEquals(int count);
}
