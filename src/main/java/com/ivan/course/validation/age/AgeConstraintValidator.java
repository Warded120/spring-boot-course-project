package com.ivan.course.validation.age;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import java.time.LocalDate;

public class AgeConstraintValidator implements ConstraintValidator<Age, LocalDate> {

    int minAge;

    @Override
    public void initialize(Age age) {
        minAge = age.min();
    }

    @Override
    public boolean isValid(LocalDate birthDate, ConstraintValidatorContext constraintValidatorContext) {

        if(birthDate == null) {
            return true;
        }

        int age = LocalDate.now().getYear() - birthDate.getYear();
        return age >= minAge;
    }
}
