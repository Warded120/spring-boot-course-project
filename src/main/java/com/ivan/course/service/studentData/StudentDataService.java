package com.ivan.course.service.studentData;

import com.ivan.course.entity.student.StudentData;

import java.util.List;

public interface StudentDataService {
    StudentData save(StudentData studentData);
    List<StudentData> findFailedStudents();
    List<StudentData> findStudentsWithoutCoursePayments();
    List<StudentData> findStudentsWithCoursePaymentsOf50Percent();
    List<StudentData> findStudentsWithThreeOrMoreCourseLevels();
    List<StudentData> findStudentsWhoLearnGermanOrTwoLanguages();
}
