package com.ivan.course.entity;

import com.ivan.course.entity.student.StudentData;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "debt")
@NoArgsConstructor
@Getter
@Setter
@ToString
public class Debt {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "student_id", referencedColumnName = "id")
    private StudentData student;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "course_id", referencedColumnName = "id")
    private Course course;

    @Column(name = "debt")
    private float debt;
}
