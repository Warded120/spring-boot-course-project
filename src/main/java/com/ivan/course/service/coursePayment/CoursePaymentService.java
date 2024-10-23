package com.ivan.course.service.coursePayment;

import com.ivan.course.entity.CoursePayment;

public interface CoursePaymentService {
    CoursePayment save(CoursePayment coursePayment);
    CoursePayment getById(int id);
    void deleteById(int id);
}
