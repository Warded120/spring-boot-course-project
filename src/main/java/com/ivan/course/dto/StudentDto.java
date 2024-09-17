package com.ivan.course.dto;

import com.ivan.course.entity.Role;
import com.ivan.course.entity.student.Student;
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
public class StudentDto implements Dto {
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

    String topRole = "student";

    @NotBlank(message = "required")
    @Size(max = 255, message = "cannot be longer than 255")
    String firstName;

    @NotBlank(message = "required")
    @Size(max = 255, message = "cannot be longer than 255")
    String lastName;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @NotNull(message = "required")
    @Age
    LocalDate birthDate;

    public StudentDto(Student student) {
        this.id = student.getId();
        this.username = student.getUsername();
        this.password = student.getPassword().getPassword();
        this.confirmPassword = student.getPassword().getPassword();
        this.enabled = student.isEnabled();
        this.topRole = getTopRoleFromRoles(student);
        this.firstName = student.getStudentData().getFirstName();
        this.lastName = student.getStudentData().getLastName();
        this.birthDate = student.getStudentData().getBirthDate();
    }

    private String getTopRoleFromRoles(Student student) {
        List<Role> roles = (List<Role>) student.getRoles();

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

        return "student";
    }
}
