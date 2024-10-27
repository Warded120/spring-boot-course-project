package com.ivan.course.entity;

import com.ivan.course.constants.CourseState;
import com.ivan.course.constants.EnrollStatus;
import com.ivan.course.dto.CourseDto;
import com.ivan.course.entity.student.Student;
import com.ivan.course.entity.teacher.TeacherData;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import java.time.LocalDate;
import java.util.List;

// TODO: add course schedule (optional) and course duration
@Entity
@Table(name = "course")
@NoArgsConstructor
@Getter
@Setter
@ToString
public class Course {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "name")
    private String name;

    @Column(name = "description")
    private String description;

    @Column(name = "language")
    private String language;

    @Column(name = "language_level")
    private String languageLevel;

    @Column(name = "price")
    private float price;
    
    @Column(name = "state")
    @Enumerated(EnumType.STRING)
    private CourseState state;

    @Column(name = "start_date")
    private LocalDate startDate;

    @ManyToOne
    @JoinColumn(name = "teacher_data_id", referencedColumnName = "id")
    @ToString.Exclude
    private TeacherData teacher;

    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "student_group_id", referencedColumnName = "id")
    @ToString.Exclude
    private StudentGroup studentGroup;

    public Course(CourseDto theCourse) {
        this.id = theCourse.getId();
        this.name = theCourse.getName();
        this.description = theCourse.getDescription();
        this.language = theCourse.getLanguage();
        this.languageLevel = theCourse.getLanguageLevel();
        this.price = theCourse.getPrice();
        this.state = theCourse.getState();
        this.startDate = null;
        this.teacher = theCourse.getTeacher();
        this.studentGroup = new StudentGroup();
    }

    public EnrollStatus enroll(Student theStudent) {

        EnrollStatus validationStatus = validateTheStudent(theStudent);
        if (validationStatus == EnrollStatus.SUCCESS) {
            EnrollStatus status = theStudent.getStudentData().payForCourse(this);

            if(status == EnrollStatus.SUCCESS) {
                studentGroup.addStudent(theStudent);
                return EnrollStatus.SUCCESS;
            }
            return status;
        }
        return validationStatus;
    }

    public EnrollStatus enrollDebtStudent(Student theStudent) {
        EnrollStatus validationStatus = validateTheStudent(theStudent);
        if (validationStatus == EnrollStatus.SUCCESS) {
            studentGroup.addStudent(theStudent);
            return EnrollStatus.SUCCESS;
        }
        return validationStatus;
    }

    public boolean canBeStarted() {
        return state == CourseState.CREATED
                && startDate == null
                && studentGroup.getStudents().size() >= StudentGroup.MIN_STUDENTS;
    }

    public boolean start() {

        if(!canBeStarted()) {
            return false;
        }

        state = CourseState.STARTED;
        startDate = LocalDate.now();
        return true;
    }

    public boolean isStarted() {
        return state == CourseState.STARTED;
    }

    private EnrollStatus validateTheStudent(Student theStudent) {
        if(studentGroup.getStudents().size() >= StudentGroup.MAX_STUDENTS) {
            System.out.println("course already have 20 students:" + this);
            return EnrollStatus.GROUP_FULL;
        }

        if(studentAlreadyLearnsThisLanguage(theStudent)) {
            System.out.println("Cannot enroll. Student already learns this language:" + this.language);
            return EnrollStatus.LANGUAGE_IS_LEARNED;
        }
        return EnrollStatus.SUCCESS;
    }

    private boolean studentAlreadyLearnsThisLanguage(Student theStudent) {

        System.out.println(this);

        List<Course> studentCourses = theStudent.getStudentData().getGroups()
                .stream()
                .map(StudentGroup::getCourse).toList();

        List<Course> listWithLearnedLanguage = studentCourses.stream().filter(course -> course.language.equals(this.language))
                .toList();

        return ! listWithLearnedLanguage.isEmpty();
    }
}
// TODO: after finishing the course level, examine(filter) students, increase the price and raise the level of the course