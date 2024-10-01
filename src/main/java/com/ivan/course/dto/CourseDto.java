package com.ivan.course.dto;

import com.ivan.course.entity.teacher.TeacherData;
import com.ivan.course.validation.inList.InList;
import com.ivan.course.validation.inList.ListType;
import com.ivan.course.validation.price.Price;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.springframework.beans.factory.annotation.Value;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class CourseDto {

    int id;

    @NotBlank(message = "required")
    @Size(max = 255, message = "cannot be longer than 255")
    String name;

    @NotBlank(message = "required")
    String description;

    @NotNull(message = "required")
    @InList
    String language;

    @NotNull(message = "required")
    @InList(list = ListType.LANGUAGE_LEVELS, message = "not a language level")
    String languageLevel;

    @NotNull(message = "required")
    @Price
    float price;

    TeacherData teacher;
}
