package com.ivan.course.entity.teacher;

import com.ivan.course.entity.Course;
import com.ivan.course.entity.user.UserData;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "teacher_data")
@Getter
@Setter
@ToString(callSuper = true, exclude = "courses")
@AllArgsConstructor
@NoArgsConstructor
public class TeacherData extends UserData {

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "teacher", cascade = {CascadeType.ALL}, orphanRemoval = true)
    private List<Course> courses;

    @OneToOne(mappedBy = "teacherData", orphanRemoval = true)
    private Teacher teacher;

    public TeacherData(int id, String firstName, String lastName, LocalDate birthDate, Teacher teacher) {
        super(id, firstName, lastName, birthDate);
        courses = new ArrayList<>();
        this.teacher = teacher;
    }

    public void addCourse(Course course) {
        courses.add(course);
    }

    public boolean isMyCourse(Course course) {
        return courses.contains(course);
    }
}
