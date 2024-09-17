package com.ivan.course.dto;

import com.ivan.course.entity.Role;
import com.ivan.course.entity.teacher.Teacher;
import com.ivan.course.validation.age.Age;
import com.ivan.course.validation.nonexistent.Nonexistent;
import com.ivan.course.validation.password.Password;
import com.ivan.course.validation.passwordMatch.PasswordMatch;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@ToString
@PasswordMatch
public class TeacherDto implements Dto{
    int id = 0;

    @NotBlank(message = "required")
    @Size(max = 255, message = "cannot be longer than 255")
    @Email(message = "not an email")
    @Nonexistent
    String username;

    @NotBlank(message = "required")
    @Size(max = 255, message = "cannot be longer than 255")
    @Password
    String password;

    @NotBlank(message = "required")
    @Size(max = 255, message = "cannot be longer than 255")
    String confirmPassword;

    boolean enabled = true;

    String topRole = "teacher";

    @NotBlank(message = "required")
    @Size(max = 255, message = "cannot be longer than 255")
    String firstName;

    @NotBlank(message = "required")
    @Size(max = 255, message = "cannot be longer than 255")
    String lastName;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @NotNull(message = "required")
    @Age(min = 18, message = "you must be 18 years or older")
    LocalDate birthDate;

    public TeacherDto(Teacher teacher) {
        this.id = teacher.getId();
        this.username = teacher.getUsername();
        this.password = teacher.getPassword().getPassword();
        this.confirmPassword = teacher.getPassword().getPassword();
        this.enabled = teacher.isEnabled();
        this.topRole = getTopRoleFromRoles(teacher);
        this.firstName = teacher.getTeacherData().getFirstName();
        this.lastName = teacher.getTeacherData().getLastName();
        this.birthDate = teacher.getTeacherData().getBirthDate();
    }

    private String getTopRoleFromRoles(Teacher teacher) {
        List<Role> roles = (List<Role>) teacher.getRoles();

        for (Role role : roles) {
            if (role.getName().equals("ROLE_ADMIN")) {
                return "admin";
            }
        }

        for (Role role : roles) {
            if (role.getName().equals("ROLE_MANAGER")) {
                return "manager";
            }
        }

        return "teacher";
    }
}
