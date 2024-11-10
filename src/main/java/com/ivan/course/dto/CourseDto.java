package com.ivan.course.dto;

import com.ivan.course.constants.CourseState;
import com.ivan.course.entity.teacher.TeacherData;
import com.ivan.course.validation.inList.InList;
import com.ivan.course.validation.inList.ListType;
import com.ivan.course.validation.price.Price;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class CourseDto {

    int id;

    @NotBlank(message = "обов'язково")
    @Size(max = 255, message = "к-сть символів повинна бути менша 255")
    String name;

    @NotBlank(message = "обов'язково")
    String description;

    @NotNull(message = "обов'язково")
    @InList
    String language;

    @NotNull(message = "обов'язково")
    @InList(list = ListType.LANGUAGE_LEVELS, message = "не є рівнем мови")
    String languageLevel;

    @NotNull(message = "обов'язково")
    @Price
    float price;

    private CourseState state = CourseState.CREATED;

    TeacherData teacher;
}
