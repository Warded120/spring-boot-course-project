package com.ivan.course.entity;

import com.ivan.course.entity.student.Student;
import com.ivan.course.entity.student.StudentData;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "student_group")
@NoArgsConstructor
@Getter
@Setter
@ToString(exclude = "students")
public class StudentGroup {
    private static final int MAX_STUDENTS = 20;
    private static final int MIN_STUDENTS = 5;

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @ManyToMany(fetch = FetchType.EAGER, cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
    @JoinTable(name = "groups_students",
            joinColumns = @JoinColumn(name = "group_id"),
            inverseJoinColumns = @JoinColumn(name = "student_id"))
    private List<StudentData> students = new ArrayList<>();

    public void addStudent(Student theStudent) {
        students.add(theStudent.getStudentData());
    }
}
