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
    @ToString.Exclude
    private StudentData student;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "course_id", referencedColumnName = "id")
    private Course course;

    @Column(name = "debt")
    private float debt;

    public Debt(StudentData student, Course course, float debt) {
        this.id = 0;
        this.student = student;
        this.course = course;
        this.debt = debt;
    }
}
