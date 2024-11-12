package com.ivan.course.dto.usersDto;

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

@Getter
@Setter
@NoArgsConstructor
@ToString
@PasswordMatch
public class TeacherDto implements Dto{
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

    String topRole = "teacher";

    @NotBlank(message = "обов'язково")
    @Size(max = 255, message = "к-сть символів повинна бути менша 255")
    String firstName;

    @NotBlank(message = "обов'язково")
    @Size(max = 255, message = "к-сть символів повинна бути менша 255")
    String lastName;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @NotNull(message = "обов'язково")
    @Age(min = 18, message = "Вам має бути 18 років або більше")
    LocalDate birthDate;

    public TeacherDto(Teacher teacher) {
        this.id = teacher.getId();
        this.username = teacher.getUsername();
        this.password = teacher.getPassword().getPassword();
        this.confirmPassword = teacher.getPassword().getPassword();
        this.enabled = teacher.isEnabled();
        this.firstName = teacher.getTeacherData().getFirstName();
        this.lastName = teacher.getTeacherData().getLastName();
        this.birthDate = teacher.getTeacherData().getBirthDate();
    }

    public TeacherDto(String username, String password, String firstName, String lastName, LocalDate birthDate) {
        this.username = username;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
        this.birthDate = birthDate;
    }
}
