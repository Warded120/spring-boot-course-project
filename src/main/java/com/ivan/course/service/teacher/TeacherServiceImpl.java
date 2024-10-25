package com.ivan.course.service.teacher;

import com.ivan.course.entity.Course;
import com.ivan.course.entity.Keys;
import com.ivan.course.entity.teacher.Teacher;
import com.ivan.course.repo.TeacherRepository;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class TeacherServiceImpl implements TeacherService {

    TeacherRepository teacherRepository;
    BCryptPasswordEncoder bCryptPasswordEncoder;
    HttpSession theSession;

    @Autowired
    public TeacherServiceImpl(TeacherRepository teacherRepository, BCryptPasswordEncoder bCryptPasswordEncoder, HttpSession theSession) {
        this.teacherRepository = teacherRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.theSession = theSession;
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

    @Override
    public Teacher getTeacherBySessionTeacher(Teacher sessionTeacher) {
        return teacherRepository.findById(sessionTeacher.getId()).orElse(null);
    }

    @Override
    public List<Course> getCoursesByTeacher(Teacher teacher) {
        Teacher sessionTeacher = getSessionTeacher();

        if (sessionTeacher == null) {
            return null;
        }

        Teacher dbTeacher = teacherRepository.findById(sessionTeacher.getId()).orElse(null);

        if (dbTeacher == null) {
            return null;
        }

        return dbTeacher.getTeacherData().getCourses();
    }

    @Override
    public Teacher getSessionTeacher() {
        Teacher sessionTeacher = (Teacher) theSession.getAttribute("teacher");

        return getTeacherBySessionTeacher(sessionTeacher);
    }
}
