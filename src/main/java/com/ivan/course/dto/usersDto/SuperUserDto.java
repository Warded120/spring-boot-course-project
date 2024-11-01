package com.ivan.course.dto.usersDto;

import com.ivan.course.entity.Role;
import com.ivan.course.entity.superuser.SuperUser;
import com.ivan.course.validation.age.Age;
import com.ivan.course.validation.nonexistent.Nonexistent;
import com.ivan.course.validation.password.Password;
import com.ivan.course.validation.passwordMatch.PasswordMatch;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@PasswordMatch
public class SuperUserDto implements Dto {
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

    String topRole = "operator";

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

    public SuperUserDto(SuperUser superUser) {
        this.id = superUser.getId();
        this.username = superUser.getUsername();
        this.password = superUser.getPassword().getPassword();
        this.confirmPassword = superUser.getPassword().getPassword();
        this.enabled = superUser.isEnabled();
        this.topRole = getTopRoleFromRoles(superUser);
        this.firstName = superUser.getSuperUserData().getFirstName();
        this.lastName = superUser.getSuperUserData().getLastName();
        this.birthDate = superUser.getSuperUserData().getBirthDate();
    }

    private String getTopRoleFromRoles(SuperUser superUser) {
        List<Role> roles = (List<Role>) superUser.getRoles();

        for (Role role : roles) {
            if (role.getName().equals("ROLE_ADMIN")) {
                return "admin";
            }
        }

        return "operator";
    }
}
