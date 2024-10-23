package com.ivan.course.repo;

import com.ivan.course.entity.CoursePayment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CoursePaymentRepository extends JpaRepository<CoursePayment, Integer> {
}
