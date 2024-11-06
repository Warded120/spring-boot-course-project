package com.ivan.course.entity.student;

import com.ivan.course.constants.EnrollStatus;
import com.ivan.course.entity.Certificate;
import com.ivan.course.entity.Course;
import com.ivan.course.entity.CoursePayment;
import com.ivan.course.entity.StudentGroup;
import com.ivan.course.entity.user.UserData;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "student_data")
@Getter
@Setter
@ToString(callSuper = true)
@NoArgsConstructor
public class StudentData extends UserData {
    @ManyToMany(mappedBy = "students", fetch = FetchType.EAGER)
    List<StudentGroup> groups;

    @ToString.Exclude
    @OneToOne(mappedBy = "studentData", orphanRemoval = true)
    private Student student;

    @Column(name = "balance")
    private float balance;

    @ToString.Exclude
    @ManyToMany(fetch = FetchType.EAGER, cascade = {CascadeType.ALL})
    @JoinTable(name = "students_payments",
            joinColumns = @JoinColumn(name = "student_data_id"),
            inverseJoinColumns = @JoinColumn(name = "debt_id"))
    private List<CoursePayment> coursePayments;

    @ToString.Exclude
    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL,mappedBy = "student")
    private List<Certificate> certificates;


    public StudentData(int id, String firstName, String lastName, LocalDate birthDate) {
        super(id, firstName, lastName, birthDate);
        groups = new ArrayList<>();
    }
    
    public void addBalance(float depositAmount) {
        this.balance += depositAmount;
    }

    public EnrollStatus payForCourse(Course course) {
        if (balance >= course.getPrice()) {
            balance -= course.getPrice();
            return EnrollStatus.SUCCESS;
        }
        return EnrollStatus.NOT_ENOUGH_BALANCE;
    }

    public boolean payCoursePayment(float payOffAmount) {
        if(payOffAmount > this.balance) {
            return false;
        }
        this.balance -= payOffAmount;
        return true;
    }

    public void addCoursePayment(CoursePayment coursePayment) {
        if (this.coursePayments == null) {
            this.coursePayments = new ArrayList<>();
        }
        this.coursePayments.add(coursePayment);
    }

    public void removeCoursePayment(CoursePayment coursePayment) {
        if (this.coursePayments != null) {
            this.coursePayments.remove(coursePayment);
        }
    }

    public void addCertificate(Certificate certificate) {
        if (this.certificates == null) {
            this.certificates = new ArrayList<>();
        }
        this.certificates.add(certificate);
    }
}
