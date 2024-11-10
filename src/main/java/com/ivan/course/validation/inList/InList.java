package com.ivan.course.validation.inList;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.util.List;

@Constraint(validatedBy = InListConstraintValidator.class)
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface InList {
    ListType list() default ListType.LANGUAGES;
    String message() default "не є мовою";

    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
