package com.ivan.course.entity;

import com.ivan.course.entity.student.Student;
import com.ivan.course.entity.student.StudentData;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "student_group")
@AllArgsConstructor
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
    @ManyToMany(fetch = FetchType.EAGER, cascade = {CascadeType.MERGE, CascadeType.REFRESH, CascadeType.DETACH})
    @JoinTable(name = "groups_students",
            joinColumns = @JoinColumn(name = "group_id"),
            inverseJoinColumns = @JoinColumn(name = "student_id"))
    private List<StudentData> students = new ArrayList<>();

    public StudentGroup(StudentGroup copyStudentGroup) {
        this.course = copyStudentGroup.getCourse();
        this.students = new ArrayList<>(copyStudentGroup.getStudents());
    }

    public void addStudent(Student theStudent) {
        StudentData studentData = theStudent.getStudentData();

        // Ensure that the studentData is merged into the current session
        if (!students.contains(studentData)) {
            students.add(studentData);
        }
    }

    public List<CoursePayment> getStudentsPayments() {
        List<CoursePayment> thisCoursePayments = new ArrayList<>();
        for (StudentData student : students) {
            List<CoursePayment> payments = student.getCoursePayments();
            CoursePayment thisCoursePayment = payments.stream().filter(coursePayment -> coursePayment.getCourse() == course).findFirst().orElse(null);

            if(thisCoursePayment != null) {
                thisCoursePayments.add(thisCoursePayment);
            }
        }


        return thisCoursePayments;
    }
}
