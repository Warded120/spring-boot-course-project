package com.ivan.course.entity.teacher;

import com.ivan.course.entity.Course;
import com.ivan.course.entity.user.UserData;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "teacher_data")
@Getter
@Setter
@ToString(callSuper = true, exclude = "courses")
@NoArgsConstructor
public class TeacherData extends UserData {

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "teacher", cascade = {CascadeType.DETACH, CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH}, orphanRemoval = true)
    private List<Course> courses;

    public TeacherData(int id, String firstName, String lastName, LocalDate birthDate) {
        super(id, firstName, lastName, birthDate);
        courses = new ArrayList<>();
    }

    public void addCourse(Course course) {
        courses.add(course);
    }

    public boolean isMyCourse(Course course) {
        return courses.contains(course);
    }
}
