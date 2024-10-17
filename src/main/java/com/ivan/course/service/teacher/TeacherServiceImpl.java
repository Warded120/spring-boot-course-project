package com.ivan.course.service.teacher;

import com.ivan.course.entity.Keys;
import com.ivan.course.entity.teacher.Teacher;
import com.ivan.course.repo.TeacherRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class TeacherServiceImpl implements TeacherService {

    TeacherRepository teacherRepository;
    BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    public TeacherServiceImpl(TeacherRepository teacherRepository, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.teacherRepository = teacherRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    @Override
    @Transactional
    public Teacher save(Teacher teacher) {
        return save(teacher, true);
    }

    @Override
    @Transactional
    public Teacher save(Teacher teacher, boolean encryptPassword) {

        if (encryptPassword) {
            teacher.setPassword(new Keys(bCryptPasswordEncoder.encode(teacher.getPassword().getPassword())));
        }

        return teacherRepository.save(teacher);
    }

    @Override
    public boolean existsByUserId(int id) {
        return teacherRepository.existsById(id);
    }

    @Override
    public Teacher findByUserId(int id) {
        return teacherRepository.findById(id).orElse(null);
    }
}
