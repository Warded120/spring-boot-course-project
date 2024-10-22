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
@ToString
public class StudentGroup {
    public static final int MAX_STUDENTS = 20;
    public static final int MIN_STUDENTS = 5;

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @OneToOne(mappedBy = "studentGroup")
    private Course course;

    //remove CascadeType.MERGE to avoid detached studentData exception
    @ToString.Exclude
    @ManyToMany(fetch = FetchType.EAGER, cascade = {CascadeType.DETACH, CascadeType.PERSIST, CascadeType.REFRESH})
    @JoinTable(name = "groups_students",
            joinColumns = @JoinColumn(name = "group_id"),
            inverseJoinColumns = @JoinColumn(name = "student_id"))
    private List<StudentData> students = new ArrayList<>();

    public void addStudent(Student theStudent) {
        System.out.println("adding student: " + theStudent);
        StudentData studentData = theStudent.getStudentData();

        // Ensure that the studentData is merged into the current session
        if (!students.contains(studentData)) {
            System.out.println("adding student");
            students.add(studentData);

        } else {
            System.out.println("student already exists");
        }
    }
}
