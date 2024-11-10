package com.ivan.course.validation.age;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Constraint(validatedBy = AgeConstraintValidator.class)
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Age {
    int min() default 10;
    String message() default "вам має бути 10 років або більше";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
