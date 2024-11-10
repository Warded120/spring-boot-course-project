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
        this.teacherData = new TeacherData(teacherDto.getId(), teacherDto.getFirstName(), teacherDto.getLastName(), teacherDto.getBirthDate(), this);
    }

    private List<Role> getRolesFromTemplate(String topRole) {
        return new ArrayList<>(List.of(new Role("ROLE_TEACHER")));
    }
}
