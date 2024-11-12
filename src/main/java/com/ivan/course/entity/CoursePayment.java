package com.ivan.course.entity;

import com.ivan.course.entity.student.StudentData;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "payment")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class CoursePayment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @ManyToOne
    @JoinColumn(name = "student_id", referencedColumnName = "id")
    @ToString.Exclude
    private StudentData student;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "course_id", referencedColumnName = "id")
    private Course course;

    @Column(name = "payment")
    private float payment;

    public CoursePayment(StudentData student, Course course) {
        this.id = 0;
        this.student = student;
        this.course = course;
        this.payment = Math.round(course.getPrice() * 1.05f * 100.0f) / 100.0f;// 5% more
    }

    public CoursePayment(StudentData student, Course course, float payment) {
        this.id = 0;
        this.student = student;
        this.course = course;
        this.payment = payment;
    }

    public boolean payOff(float payment) {
        if(this.student.payCoursePayment(payment)) {
            this.payment -= payment;
            return true;
        }
        return false;
    }
}
