package com.ivan.course.service.studentData;

import com.ivan.course.entity.student.StudentData;
import com.ivan.course.repo.StudentDataRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

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

    @Override
    public List<StudentData> findFailedStudents() {
        return studentDataRepository.findFailedStudents();
    }

    @Override
    public List<StudentData> findStudentsWithoutCoursePayments() {
        return studentDataRepository.findStudentsWithoutCoursePayments();
    }

    @Override
    public List<StudentData> findStudentsWithCoursePaymentsOf50Percent() {
        return studentDataRepository.findStudentsWithCoursePaymentsOf50Percent();
    }

    @Override
    public List<StudentData> findStudentsWithThreeOrMoreCourseLevels() {
        return studentDataRepository.findStudentsWithThreeOrMoreCourseLevels();
    }

    @Override
    public List<StudentData> findStudentsWhoLearnGermanOrTwoLanguages() {
        return studentDataRepository.findStudentsWhoLearnGermanOrTwoLanguages();
    }
}
