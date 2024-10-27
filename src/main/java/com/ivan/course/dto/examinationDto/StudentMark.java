package com.ivan.course.dto.examinationDto;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class StudentMark {
    private int studentId;
    private String studentFullName;
    private int mark;
}
