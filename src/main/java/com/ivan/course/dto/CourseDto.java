package com.ivan.course.dto;

import com.ivan.course.entity.teacher.TeacherData;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

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

    @NotBlank(message = "required")
    String language;

    @NotNull
    String languageLevel;

    @NotNull(message = "required")
    @Min(value = 0, message = "price must be bigger that 0$")
    float price;

    TeacherData teacher;
}
