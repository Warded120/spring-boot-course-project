package com.ivan.course.service.coursePayment;

import com.ivan.course.entity.Course;
import com.ivan.course.entity.CoursePayment;
import com.ivan.course.entity.student.Student;
import com.ivan.course.repo.CoursePaymentRepository;
import com.ivan.course.repo.CourseRepository;
import com.ivan.course.repo.StudentRepository;
import jakarta.persistence.EntityManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class CoursePaymentServiceImpl implements CoursePaymentService {

    private CoursePaymentRepository coursePaymentRepository;
    private StudentRepository studentRepository;
    private CourseRepository courseRepository;

    @Autowired
    public CoursePaymentServiceImpl(CoursePaymentRepository coursePaymentRepository,
                                    StudentRepository studentRepository,
                                    CourseRepository courseRepository) {
        this.coursePaymentRepository = coursePaymentRepository;
        this.studentRepository = studentRepository;
        this.courseRepository = courseRepository;
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

    @Override
    @Transactional
    public void create(Student student, Course course) {
        Student dbStudent = studentRepository.findByUsername(student.getUsername());
        Course dbCourse = courseRepository.findByName(course.getName());

        dbCourse.smartEnroll(dbStudent);

        System.out.println(dbStudent.getStudentData().getCoursePayments());
        courseRepository.save(dbCourse);
        studentRepository.save(dbStudent);
    }
}
