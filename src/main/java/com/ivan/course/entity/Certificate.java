package com.ivan.course.entity;

import com.ivan.course.entity.student.StudentData;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "certificate")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class Certificate {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @ManyToOne
    @JoinColumn(name = "student_id", referencedColumnName = "id")
    private StudentData student;

    @ManyToOne
    @JoinColumn(name = "course_id", referencedColumnName = "id")
    private Course course;

    @Column(name = "mark")
    private int mark;
}
