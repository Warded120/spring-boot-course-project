package com.ivan.course.validation.inList;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.beans.factory.annotation.Value;

import java.time.LocalDate;
import java.util.List;

public class InListConstraintValidator implements ConstraintValidator<InList, String> {

    @Value("${course.languages}")
    private List<String> languages;

    @Value("${course.language-levels}")
    private List<String> languageLevels;

    private List<String> list;

    @Override
    public void initialize(InList inList) {
        list = inList.list() == ListType.LANGUAGES ? languages : languageLevels;
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext constraintValidatorContext) {
        return list.contains(value) || value == null;
    }
}
