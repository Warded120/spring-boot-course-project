package com.ivan.course.validation.price;

import com.ivan.course.validation.age.AgeConstraintValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Constraint(validatedBy = PriceConstraintValidator.class)
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Price {
    float min() default (float)0.0;
    float max() default Float.MAX_VALUE;
    String message() default "minimum price is 0";

    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
