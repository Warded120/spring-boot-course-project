package com.ivan.course.service.studentGroup;

import com.ivan.course.entity.StudentGroup;
import com.ivan.course.repo.StudentGroupRepository;
import com.ivan.course.validation.age.Age;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StudentGroupServiceImpl implements StudentGroupService {

    StudentGroupRepository studentGroupRepository;

    @Autowired
    public StudentGroupServiceImpl(StudentGroupRepository studentGroupRepository) {
        this.studentGroupRepository = studentGroupRepository;
    }

    @Override
    public StudentGroup save(StudentGroup studentGroup) {
        return studentGroupRepository.save(studentGroup);
    }

    @Override
    public List<StudentGroup> getGroupsByLanguageAndTeacher(String language, int teacherId) {
        return studentGroupRepository.findGroupsByLanguageAndTeacher(language, teacherId);
    }
}
