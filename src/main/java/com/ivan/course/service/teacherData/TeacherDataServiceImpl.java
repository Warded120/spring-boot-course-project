package com.ivan.course.service.teacherData;

import com.ivan.course.entity.teacher.TeacherData;
import com.ivan.course.repo.TeacherDataRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
}
