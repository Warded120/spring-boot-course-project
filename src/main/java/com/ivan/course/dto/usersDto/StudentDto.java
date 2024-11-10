package com.ivan.course.dto.usersDto;

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

@Getter
@Setter
@NoArgsConstructor
@ToString
@PasswordMatch
public class StudentDto implements Dto {
    int id = 0;

    @NotBlank(message = "обов'язково")
    @Size(max = 255, message = "к-сть символів повинна бути менша 255")
    @Email(message = "неправильний формат пошти")
    @Nonexistent
    String username;

    @NotBlank(message = "обов'язково")
    @Size(max = 255, message = "к-сть символів повинна бути менша 255")
    @Password
    String password;

    @NotBlank(message = "обов'язково")
    @Size(max = 255, message = "к-сть символів повинна бути менша 255")
    String confirmPassword;

    boolean enabled = true;

    String topRole = "student";

    @NotBlank(message = "обов'язково")
    @Size(max = 255, message = "к-сть символів повинна бути менша 255")
    String firstName;

    @NotBlank(message = "обов'язково")
    @Size(max = 255, message = "к-сть символів повинна бути менша 255")
    String lastName;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @NotNull(message = "обов'язково")
    @Age
    LocalDate birthDate;

    public StudentDto(Student student) {
        this.id = student.getId();
        this.username = student.getUsername();
        this.password = student.getPassword().getPassword();
        this.confirmPassword = student.getPassword().getPassword();
        this.enabled = student.isEnabled();
        this.firstName = student.getStudentData().getFirstName();
        this.lastName = student.getStudentData().getLastName();
        this.birthDate = student.getStudentData().getBirthDate();
    }
}
