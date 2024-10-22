package com.ivan.course.entity.student;

import com.ivan.course.constants.EnrollStatus;
import com.ivan.course.entity.Course;
import com.ivan.course.entity.Debt;
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
    @JoinTable(name = "students_debts",
            joinColumns = @JoinColumn(name = "student_data_id"),
            inverseJoinColumns = @JoinColumn(name = "debt_id"))
    private List<Debt> debts;

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

    public void addDebt(Debt debt) {
        if (this.debts == null) {
            this.debts = new ArrayList<>();
        }
        this.debts.add(debt);
    }
}
