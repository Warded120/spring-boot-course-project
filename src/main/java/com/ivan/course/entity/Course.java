package com.ivan.course.entity;

import com.ivan.course.constants.LanguageLevel;
import com.ivan.course.entity.student.Student;
import com.ivan.course.entity.teacher.TeacherData;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Entity
@Table(name = "course")
@NoArgsConstructor
@Getter
@Setter
@ToString
public class Course {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "language")
    private String language;

    @Column(name = "language_level")
    @Enumerated(EnumType.ORDINAL)
    private LanguageLevel languageLevel;

    @Column(name = "price")
    private float price;

    @ManyToOne
    @JoinColumn(name = "teacher_data_id", referencedColumnName = "id")
    private TeacherData teacher;

    @ManyToMany
    @JoinTable(name = "courses_students",
            joinColumns = @JoinColumn(name = "course_id"),
            inverseJoinColumns = @JoinColumn(name = "student_id"))
    private List<Student> students;
}
