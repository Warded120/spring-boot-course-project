package com.ivan.course.entity;

import com.ivan.course.dto.CourseDto;
import com.ivan.course.entity.teacher.TeacherData;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "course")
@NoArgsConstructor
@Getter
@Setter
@ToString()
public class Course {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "name")
    private String name;

    @Column(name = "description")
    private String description;

    @Column(name = "language")
    private String language;

    @Column(name = "language_level")
    private String languageLevel;

    @Column(name = "price")
    private float price;

    @ManyToOne
    @JoinColumn(name = "teacher_data_id", referencedColumnName = "id")
    private TeacherData teacher;

    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "student_group_id", referencedColumnName = "id")
    private StudentGroup studentGroup;

    public Course(CourseDto theCourse) {
        this.name = theCourse.getName();
        this.description = theCourse.getDescription();
        this.language = theCourse.getLanguage();
        this.languageLevel = theCourse.getLanguageLevel();
        this.price = theCourse.getPrice();
        this.teacher = theCourse.getTeacher();
        this.studentGroup = new StudentGroup();
    }
}
// TODO: after finishing the course level, examine(filter) students, increase the price and raise the level of the course
// TODO: implement enrolling students to courses