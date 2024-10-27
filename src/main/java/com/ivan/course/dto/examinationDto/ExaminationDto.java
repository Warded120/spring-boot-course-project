package com.ivan.course.dto.examinationDto;

import com.ivan.course.entity.Course;
import com.ivan.course.entity.student.Student;
import com.ivan.course.entity.student.StudentData;
import lombok.*;
import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@Getter
@Setter
@ToString
public class ExaminationDto {

    private List<StudentMark> studentMarks;
    private int courseId;
    private String courseName;

    public ExaminationDto(Course course) {

        // retrieve Student object from StudentData object
        List<Student> students = course.getStudentGroup().getStudents()
                .stream()
                .map(StudentData::getStudent)
                .toList();

        this.studentMarks = new ArrayList<>();
        for (Student student : students) {

            int studentId = student.getId();
            String studentFullName = student.getStudentData().getFirstName() + " " + student.getStudentData().getLastName();

            this.studentMarks.add(new StudentMark(studentId, studentFullName, 0));
        }

        this.courseId = course.getId();
        this.courseName = course.getName();
    }
}
