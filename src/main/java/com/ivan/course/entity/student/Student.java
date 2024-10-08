package com.ivan.course.entity.student;

import com.ivan.course.dto.usersDto.StudentDto;
import com.ivan.course.entity.Role;
import com.ivan.course.entity.user.User;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "student")
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
        List<Role> tempAuthorities = new ArrayList<>();

        System.out.println(topRole);

        switch (topRole) {
            case "student":
                tempAuthorities.add(new Role("ROLE_STUDENT"));
                break;
            case "manager":
                tempAuthorities.add(new Role("ROLE_STUDENT"));
                tempAuthorities.add(new Role("ROLE_MANAGER"));
                break;
            case "admin":
                tempAuthorities.add(new Role("ROLE_STUDENT"));
                tempAuthorities.add(new Role("ROLE_MANAGER"));
                tempAuthorities.add(new Role("ROLE_ADMIN"));
                break;
        }
        return tempAuthorities;
    }
}
