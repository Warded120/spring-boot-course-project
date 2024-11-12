package com.ivan.course.service.course;

import com.ivan.course.constants.CourseState;
import com.ivan.course.entity.Course;
import com.ivan.course.entity.StudentGroup;
import com.ivan.course.entity.student.Student;
import com.ivan.course.repo.CourseRepository;
import com.ivan.course.service.student.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

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
    @Transactional
    public Course save(Course course) {
        // to solve problem with detached studentData when enrolling a student
        //return entityManager.merge(course);

        return courseRepository.save(course);
    }

    @Override
    @Transactional
    public void saveAll(List<Course> courses) {
        courses.forEach(course -> courseRepository.save(course));
    }

    @Override
    public List<Course> findAll() {
        return courseRepository.findAllBy();
    }

    @Override
    public List<Course> findByCourseStateNotEqual(CourseState state) {
        return courseRepository.findAllByStateNot(state);
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

        return courses;
    }

    @Override
    public List<Course> findNotEnrolledCoursesByStudentAndStateNot(Student sessionStudent, CourseState state) {

        Student dbStudent = studentService.getStudentBySessionStudent(sessionStudent);
        List<Course> studentCourses = studentService.getCoursesByStudent(dbStudent);

        List<Course> courses = findByCourseStateNotEqual(state).stream().filter(course -> !studentCourses.contains(course)).toList();

        return courses;
    }

    @Override
    public boolean isStudentEnrolled(Course course, Student sessionStudent) {

        boolean isEnrolled = false;

        StudentGroup studentGroup = course.getStudentGroup();
        Student dbStudent = studentService.getStudentBySessionStudent(sessionStudent);
        isEnrolled = studentGroup.getStudents().contains(dbStudent.getStudentData());

        return isEnrolled;
    }

    @Override
    public List<Course> findCoursesWithStudentCountLessThan(int minStudents) {
        return courseRepository.findCoursesWithStudentCountLessThan(minStudents);
    }

    @Override
    public List<Course> findCoursesWithStudentCountEquals(int count) {
        return courseRepository.findCoursesWithStudentCountEquals(count);
    }
}
