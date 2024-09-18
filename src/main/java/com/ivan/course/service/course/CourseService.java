package com.ivan.course.service.course;

import com.ivan.course.entity.Course;

import java.util.List;

public interface CourseService {
    Course save(Course course);
    List<Course> findAll();
}
