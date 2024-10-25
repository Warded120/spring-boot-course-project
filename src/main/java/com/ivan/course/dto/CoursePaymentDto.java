package com.ivan.course.dto;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class CoursePaymentDto {
    private int id;

    private int studentDataId;

    private int courseId;

    private float payment;

    public CoursePaymentDto(int studentDataId, int courseId, float coursePrice) {
        this.id = 0;
        this.studentDataId = studentDataId;
        this.courseId = courseId;
        this.payment = Math.round(coursePrice * 1.05f * 100.0f) / 100.0f;// 5% more
    }
}
