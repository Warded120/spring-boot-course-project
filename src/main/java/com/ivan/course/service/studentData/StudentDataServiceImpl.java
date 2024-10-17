package com.ivan.course.service.studentData;

import com.ivan.course.entity.student.StudentData;
import com.ivan.course.repo.StudentDataRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class StudentDataServiceImpl implements StudentDataService {

    StudentDataRepository studentDataRepository;

    @Autowired
    public StudentDataServiceImpl(StudentDataRepository studentDataRepository) {
        this.studentDataRepository = studentDataRepository;
    }

    @Override
    public StudentData save(StudentData studentData) {
        return studentDataRepository.save(studentData);
    }
}
