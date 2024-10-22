package com.ivan.course.service.course;

import com.ivan.course.entity.Course;
import com.ivan.course.entity.StudentGroup;
import com.ivan.course.entity.student.Student;
import com.ivan.course.repo.CourseRepository;
import com.ivan.course.repo.StudentRepository;
import com.ivan.course.service.student.StudentService;
import jakarta.persistence.EntityManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class CourseServiceImpl implements CourseService {

    private final CourseRepository courseRepository;
    private final StudentService studentService;

    @Autowired
    public CourseServiceImpl(CourseRepository courseRepository, StudentService studentService) {
        this.courseRepository = courseRepository;
        this.studentService = studentService;
    }

    @Override
    public Course save(Course course) {
        // to solve problem with detached studentData when enrolling a student
        //return entityManager.merge(course);

        return courseRepository.save(course);
    }

    @Override
    public List<Course> findAll() {
        return courseRepository.findAllBy();
    }

    @Override
    public Course findById(int id) {
        return courseRepository.findById(id).orElse(null);
    }

    @Override
    public List<Course> findNotEnrolledCoursesByStudent(Student sessionStudent) {

        Student dbStudent = studentService.getStudentBySessionStudent(sessionStudent);
        List<Course> studentCourses = studentService.getCoursesByStudent(dbStudent);

        List<Course> courses = findAll().stream().filter(course -> !studentCourses.contains(course)).toList();
        System.out.println("Not enrolled courses: " + courses);

        return courses;
    }

    @Override
    public boolean isStudentEnrolled(Course course, Student sessionStudent) {

        boolean isEnrolled = false;

        StudentGroup studentGroup = course.getStudentGroup();
        Student dbStudent = studentService.getStudentBySessionStudent(sessionStudent);
        isEnrolled = studentGroup.getStudents().contains(dbStudent.getStudentData());
        System.out.println("isEnrolled: " + isEnrolled);

        return isEnrolled;
    }
}
