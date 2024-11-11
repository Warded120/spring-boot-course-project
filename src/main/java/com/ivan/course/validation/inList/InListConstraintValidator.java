package com.ivan.course.validation.inList;

import com.ivan.course.constants.ConstantsCollection;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import java.util.List;

public class InListConstraintValidator implements ConstraintValidator<InList, String> {

    private List<String> languages = ConstantsCollection.getLanguages();

    private List<String> languageLevels = ConstantsCollection.getLanguageLevels();

    private List<String> list;

    @Override
    public void initialize(InList inList) {
        list = inList.list() == ListType.LANGUAGES ? languages : languageLevels;
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext constraintValidatorContext) {
        return value == null || list.contains(value);
    }
}
