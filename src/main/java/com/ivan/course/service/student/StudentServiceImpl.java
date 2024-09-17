package com.ivan.course.service.student;

import com.ivan.course.entity.Keys;
import com.ivan.course.entity.student.Student;
import com.ivan.course.repo.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class StudentServiceImpl implements StudentService {

    private StudentRepository studentRepository;
    BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    public StudentServiceImpl(StudentRepository studentRepository, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.studentRepository = studentRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
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
}
