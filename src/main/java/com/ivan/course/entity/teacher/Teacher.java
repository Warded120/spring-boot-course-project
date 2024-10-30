package com.ivan.course.entity.teacher;

import com.ivan.course.dto.usersDto.TeacherDto;
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
@Table(name = "teacher")
@NoArgsConstructor
@Getter
@Setter
@ToString(callSuper = true)
public class Teacher extends User {

    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "teacher_data_id", referencedColumnName = "id")
    TeacherData teacherData;

    public Teacher(TeacherDto teacherDto) {
        super(teacherDto.getId(), teacherDto.getUsername(), teacherDto.getPassword(), teacherDto.isEnabled());
        this.id = teacherDto.getId();
        this.roles = getRolesFromTemplate(teacherDto.getTopRole());
        this.teacherData = new TeacherData(teacherDto.getId(), teacherDto.getFirstName(), teacherDto.getLastName(), teacherDto.getBirthDate());
    }

    private List<Role> getRolesFromTemplate(String topRole) {
        List<Role> tempAuthorities = new ArrayList<>();

        switch (topRole) {
            case "teacher":
                tempAuthorities.add(new Role("ROLE_TEACHER"));
                break;
            case "operator":
                tempAuthorities.add(new Role("ROLE_TEACHER"));
                tempAuthorities.add(new Role("ROLE_OPERATOR"));
                break;
            case "admin":
                tempAuthorities.add(new Role("ROLE_TEACHER"));
                tempAuthorities.add(new Role("ROLE_OPERATOR"));
                tempAuthorities.add(new Role("ROLE_ADMIN"));
                break;
        }
        return tempAuthorities;
    }
}
