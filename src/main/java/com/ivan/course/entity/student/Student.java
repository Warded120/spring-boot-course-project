package com.ivan.course.entity.student;

import com.ivan.course.dto.usersDto.StudentDto;
import com.ivan.course.entity.Role;
import com.ivan.course.entity.user.User;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "student")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class Student extends User {

    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "student_data_id", referencedColumnName = "id")
    StudentData studentData;

    public Student(StudentDto studentDto) {
        super(studentDto.getId(), studentDto.getUsername(), studentDto.getPassword(), studentDto.isEnabled());
        this.roles = getRolesFromTemplate(studentDto.getTopRole());
        studentData = new StudentData(studentDto.getId(), studentDto.getFirstName(), studentDto.getLastName(), studentDto.getBirthDate());
    }

    private List<Role> getRolesFromTemplate(String topRole) {
        return new ArrayList<>(List.of(new Role("ROLE_STUDENT")));
    }
}
