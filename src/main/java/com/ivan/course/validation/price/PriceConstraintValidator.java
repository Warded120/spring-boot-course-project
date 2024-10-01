package com.ivan.course.validation.price;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class PriceConstraintValidator implements ConstraintValidator<Price, Float> {

    float min;
    float max;

    @Override
    public void initialize(Price price) {
        this.min = price.min();
        this.max = price.max();
    }

    @Override
    public boolean isValid(Float price, ConstraintValidatorContext constraintValidatorContext) {
        return price >= min && price <= max;
    }
}
