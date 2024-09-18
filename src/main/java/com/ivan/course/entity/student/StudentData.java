package com.ivan.course.entity.student;

import com.ivan.course.entity.user.UserData;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDate;

@Entity
@Table(name = "student_data")
@Getter
@Setter
@ToString(callSuper = true)
@NoArgsConstructor
public class StudentData extends UserData {

    //courses(joinTable courses_students) debts examinations certificates

    public StudentData(int id, String firstName, String lastName, LocalDate birthDate) {
        super(id, firstName, lastName, birthDate);
    }
}
