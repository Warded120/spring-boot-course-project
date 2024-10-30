package com.ivan.course.entity;

import com.ivan.course.constants.CourseState;
import com.ivan.course.constants.EnrollStatus;
import com.ivan.course.dto.CourseDto;
import com.ivan.course.entity.student.Student;
import com.ivan.course.entity.student.StudentData;
import com.ivan.course.entity.teacher.TeacherData;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.List;

// TODO: add course schedule (optional) and course duration
@Entity
@Table(name = "course")
@NoArgsConstructor
@AllArgsConstructor
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

    @Column(name = "end_date")
    private LocalDate endDate;

    @ToString.Exclude
    @ManyToOne
    @JoinColumn(name = "teacher_data_id", referencedColumnName = "id")
    private TeacherData teacher;

    @ToString.Exclude
    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "student_group_id", referencedColumnName = "id")
    private StudentGroup studentGroup;

    @ToString.Exclude
    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "examination_id", referencedColumnName = "id")
    private Examination examination = null;

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

    // constructor for next level course
    public Course(String name, String description, String language, String languageLevel, float price, CourseState state, LocalDate startDate, LocalDate endDate, TeacherData teacher, StudentGroup studentGroup, Examination examination) {
        this.name = name;
        this.description = description;
        this.language = language;
        this.languageLevel = languageLevel;
        this.price = price;
        this.state = state;
        this.startDate = startDate;
        this.endDate = endDate;
        this.teacher = teacher;
        this.studentGroup = studentGroup;
        this.examination = examination;

        sendPaymentsToStudents();
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
    public boolean isFinished() {
        return state == CourseState.FINISHED;
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

    public Course getNextLevelCourse() {
        if (languageLevel.equals("C1")) {
            return null;
        }
        return new Course(name, description, language, getNextlanguageLevel(), (float) (price*1.25), CourseState.CREATED, null, null, teacher, new StudentGroup(studentGroup), null);
    }

    private String getNextlanguageLevel() {
        switch(languageLevel) {
            case "A1" -> {
                return "A2";
            }
            case "A2" -> {
                return "B1";
            }
            case "B1" -> {
                return "B2";
            }
            case "B2" -> {
                return "C1";
            }
            default -> {
                return "A1";
            }
        }
    }

    private void sendPaymentsToStudents() {
        List<StudentData> students = this.studentGroup.getStudents();
        for(StudentData student : students) {
            student.addCoursePayment(new CoursePayment(student, this, this.price));
        }
    }

    public String getStateMessage() {
        switch (state.name()) {
            case "STARTED":
                return "Course started";
            case "FINISHED":
                return "Course finished";
            default:
                return "Course not started yet";
        }
    }

    public Schedule getSchedule() {
        if(startDate == null) {
            return null;
        }
        return new Schedule(startDate);
    }
}