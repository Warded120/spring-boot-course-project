package com.ivan.course.validation.passwordMatch;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Constraint(validatedBy = PasswordMatchConstraintValidator.class)
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface PasswordMatch {
    String message() default "паролі не збігаються";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
