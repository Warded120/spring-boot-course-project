package com.ivan.course.entity.teacher;

import com.ivan.course.entity.Course;
import com.ivan.course.entity.user.UserData;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "teacher_data")
@Getter
@Setter
@ToString
@NoArgsConstructor
public class TeacherData extends UserData {

    @OneToMany(mappedBy = "teacher", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Course> courses;

    public TeacherData(int id, String firstName, String lastName, LocalDate birthDate) {
        super(id, firstName, lastName, birthDate);
    }
}
