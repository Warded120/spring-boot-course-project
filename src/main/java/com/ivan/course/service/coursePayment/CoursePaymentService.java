package com.ivan.course.service.coursePayment;

import com.ivan.course.entity.Course;
import com.ivan.course.entity.CoursePayment;
import com.ivan.course.entity.student.Student;

public interface CoursePaymentService {
    CoursePayment save(CoursePayment coursePayment);
    CoursePayment getById(int id);
    void deleteById(int id);

    void create(Student student, Course course);
}
