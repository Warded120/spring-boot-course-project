package com.ivan.course.service.teacherData;

import com.ivan.course.entity.teacher.TeacherData;
import com.ivan.course.repo.TeacherDataRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TeacherDataServiceImpl implements TeacherDataService {

    TeacherDataRepository teacherDataRepository;

    @Autowired
    public TeacherDataServiceImpl(TeacherDataRepository teacherDataRepository) {
        this.teacherDataRepository = teacherDataRepository;
    }

    @Override
    public TeacherData save(TeacherData teacherData) {
        return teacherDataRepository.save(teacherData);
    }

    @Override
    public List<TeacherData> findAll() {
        return teacherDataRepository.findAll();
    }

    @Override
    public List<TeacherData> findOneTwoThreeLanguageTeachers() {
        return teacherDataRepository.findOneTwoThreeLanguageTeachers();
    }

    @Override
    public TeacherData findById(int teacherId) {
        return teacherDataRepository.findById(teacherId).orElse(null);
    }
}
