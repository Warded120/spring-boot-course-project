package com.ivan.course.entity.student;

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
@ToString(callSuper = true, exclude = "student")
@NoArgsConstructor
public class StudentData extends UserData {


    @ManyToMany(mappedBy = "students", fetch = FetchType.EAGER)
    List<StudentGroup> groups;

    @OneToOne(mappedBy = "studentData", orphanRemoval = true)
    private Student student;

    public StudentData(int id, String firstName, String lastName, LocalDate birthDate) {
        super(id, firstName, lastName, birthDate);
        groups = new ArrayList<>();
    }
}
