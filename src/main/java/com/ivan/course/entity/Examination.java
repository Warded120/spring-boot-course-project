package com.ivan.course.entity;

import com.ivan.course.dto.examinationDto.ExaminationDto;
import com.ivan.course.dto.examinationDto.StudentMark;
import com.ivan.course.entity.student.Student;
import com.ivan.course.service.student.StudentService;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashMap;
import java.util.Map;

@Entity
@Table(name = "examination")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class Examination {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL, mappedBy = "examination")
    private Course course;

    @ElementCollection
    @CollectionTable(name = "student_marks",
            joinColumns = @JoinColumn(name = "examination_id", referencedColumnName = "id"))
    @MapKeyJoinColumn(name = "student_id", referencedColumnName = "id") // Points to the Student entity's primary key
    @Column(name = "mark") // The column that stores the Integer value
    private Map<Student, Integer> studentMarks;

    public Examination(Course course, Map<Student, Integer> studentMarks) {
        this.course = course;
        this.studentMarks = studentMarks;
    }
}
