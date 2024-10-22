package com.ivan.course.dto;

import com.ivan.course.entity.Course;
import com.ivan.course.entity.student.StudentData;
import com.ivan.course.validation.price.Price;
import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class DebtDto {
    private int id;

    private int studentDataId;

    private int courseId;

    private float debt;

    public DebtDto(int studentDataId, int courseId, float coursePrice) {
        this.id = 0;
        this.studentDataId = studentDataId;
        this.courseId = courseId;
        this.debt = (coursePrice * (float) 1.05); // 5% more
    }
}
