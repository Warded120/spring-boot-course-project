package com.ivan.course.service.student;

import com.ivan.course.entity.Course;
import com.ivan.course.entity.Keys;
import com.ivan.course.entity.StudentGroup;
import com.ivan.course.entity.student.Student;
import com.ivan.course.repo.StudentRepository;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class StudentServiceImpl implements StudentService {

    private StudentRepository studentRepository;
    private BCryptPasswordEncoder bCryptPasswordEncoder;
    private HttpSession theSession;

    @Autowired
    public StudentServiceImpl(StudentRepository studentRepository, BCryptPasswordEncoder bCryptPasswordEncoder, HttpSession theSession) {
        this.studentRepository = studentRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.theSession = theSession;
    }

    @Override
    @Transactional
    public Student save(Student student) {

        student.setPassword(new Keys(bCryptPasswordEncoder.encode(student.getPassword().getPassword())));

        return studentRepository.save(student);
    }

    @Override
    public boolean existsByUserId(int id) {
        return studentRepository.existsById(id);
    }

    @Override
    public Student findByUserId(int id) {
        return studentRepository.findById(id).orElse(null);
    }

    @Override
    public Student getStudentBySessionStudent(Student sessionStudent) {
        return studentRepository.findById(sessionStudent.getId()).orElse(null);
    }

    @Override
    public List<Course> getCoursesByStudent(Student student) {

        Student sessionStudent = (Student) theSession.getAttribute("student");

        Student dbStudent = getStudentBySessionStudent(sessionStudent);

        List<Course> studentCourses = dbStudent.getStudentData().getGroups().stream().map(StudentGroup::getCourse).toList();

        return studentCourses;
    }
}
