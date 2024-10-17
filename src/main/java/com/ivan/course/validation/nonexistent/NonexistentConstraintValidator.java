package com.ivan.course.validation.nonexistent;

import com.ivan.course.entity.user.User;
import com.ivan.course.service.user.UserService;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.beans.factory.annotation.Autowired;

public class NonexistentConstraintValidator implements ConstraintValidator<Nonexistent, String> {

    UserService userService;

    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    @Override
    public boolean isValid(String username, ConstraintValidatorContext constraintValidatorContext) {
        User existing = userService.findByUsername(username);

        return existing == null;
    }
}
