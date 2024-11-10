package com.ivan.course.dto.usersDto;

import com.ivan.course.entity.user.User;
import com.ivan.course.validation.password.Password;
import com.ivan.course.validation.passwordMatch.PasswordMatch;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@NoArgsConstructor
@Getter
@Setter
@ToString
@PasswordMatch
public class UserDto implements Dto {

    int id = 0;

    String username;

    @NotBlank(message = "обов'язково")
    @Size(max = 255, message = "к-сть символів повинна бути менша 255")
    @Password
    String password;

    @NotBlank(message = "обов'язково")
    @Size(max = 255, message = "к-сть символів повинна бути менша 255")
    String confirmPassword;

    public UserDto(User user) {
        this.id = user.getId();
        this.username = user.getUsername();
        this.password = user.getPassword().getPassword();
        this.confirmPassword = user.getPassword().getPassword();
    }
}
