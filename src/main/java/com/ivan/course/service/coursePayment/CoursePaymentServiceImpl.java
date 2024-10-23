package com.ivan.course.service.coursePayment;

import com.ivan.course.entity.CoursePayment;
import com.ivan.course.repo.CoursePaymentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CoursePaymentServiceImpl implements CoursePaymentService {

    private CoursePaymentRepository coursePaymentRepository;

    @Autowired
    public CoursePaymentServiceImpl(CoursePaymentRepository coursePaymentRepository) {
        this.coursePaymentRepository = coursePaymentRepository;
    }

    @Override
    public CoursePayment save(CoursePayment coursePayment) {
        return coursePaymentRepository.save(coursePayment);
    }

    @Override
    public CoursePayment getById(int id) {
        return coursePaymentRepository.findById(id).orElse(null);
    }

    @Override
    public void deleteById(int id) {
        coursePaymentRepository.deleteById(id);
    }
}
