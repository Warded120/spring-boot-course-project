package com.ivan.course.controller;

import com.ivan.course.entity.Course;
import com.ivan.course.entity.StudentGroup;
import com.ivan.course.service.course.CourseService;
import com.ivan.course.service.studentData.StudentDataService;
import com.ivan.course.service.studentGroup.StudentGroupService;
import com.ivan.course.service.teacherData.TeacherDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/queries")
public class QueriesController {

    StudentGroupService studentGroupService;
    TeacherDataService teacherDataService;
    StudentDataService studentDataService;
    CourseService courseService;

    @Value("${course.languages}")
    List<String> languages;

    @Value("${course.language-levels}")
    List<String> languageLevels;

    @Autowired
    public QueriesController(StudentGroupService studentGroupService,
                             TeacherDataService teacherDataService,
                             CourseService courseService,
                             StudentDataService studentDataService) {
        this.studentGroupService = studentGroupService;
        this.teacherDataService = teacherDataService;
        this.courseService = courseService;
        this.studentDataService = studentDataService;
    }

    // TODO: implement all queries

    // query 1
    // output course information, because student_group table doesn't have enough data in it
    @GetMapping("/groupsByLanguageAndTeacher")
    public String findGroupsByLanguageAndTeacher(
            Model theModel) {

        theModel.addAttribute("studentGroupCourses", new ArrayList<>());
        theModel.addAttribute("languages", languages);
        theModel.addAttribute("teachers", teacherDataService.findAll());

        return "queries/groups-by-language-and-teacher";
    }

    @PostMapping("/groupsByLanguageAndTeacher")
    public String findGroupsByLanguageAndTeacher(
            @RequestParam("selectedLanguage") String language,
            @RequestParam("selectedTeacherId") int teacherId,
            Model theModel) {

        List<Course> studentGroupCourses = studentGroupService.getGroupsByLanguageAndTeacher(language, teacherId).stream().map(StudentGroup::getCourse).toList();

        theModel.addAttribute("studentGroupCourses", studentGroupCourses);
        theModel.addAttribute("languages", languages);
        theModel.addAttribute("teachers", teacherDataService.findAll());

        return "queries/groups-by-language-and-teacher";
    }

    // query 2
    @GetMapping("/courseFullPriceByLevel")
    public String findCourseFullPriceByLevel(Model theModel) {

        List<Course> all = courseService.findAll();

        theModel.addAttribute("coursesParam", all);
        theModel.addAttribute("languageLevels", languageLevels);
        theModel.addAttribute("selectedCourseId", all.getFirst());
        theModel.addAttribute("selectedLanguageLevel", all.getFirst().getLanguageLevel());
        theModel.addAttribute("courseFullPrice", 0);

        return "queries/course-full-price";
    }

    @PostMapping("/courseFullPriceByLevel")
    public String findCourseFullPriceByLevel(@RequestParam("selectedCourseId") int selectedCourseId,
                                             @RequestParam("selectedLanguageLevel") String selectedLanguageLevel,
                                             Model theModel) {

        Course selectedCourse = courseService.findById(selectedCourseId);

        theModel.addAttribute("coursesParam", courseService.findAll());
        theModel.addAttribute("languageLevels", languageLevels);
        theModel.addAttribute("selectedCourseId", selectedCourseId);
        theModel.addAttribute("selectedLanguageLevel", selectedLanguageLevel);
        theModel.addAttribute("courseFullPrice", selectedCourse.calculateCoursePriceToLevel(selectedLanguageLevel));

        return "queries/course-full-price";
    }

    // query 3
    @GetMapping("/failedStudents")
    public String findFailedStudents(Model theModel) {
        theModel.addAttribute("students", studentDataService.findFailedStudents());
        return "queries/failed-students";
    }

    // query 4
    @GetMapping("/teachersLanguages")
    public String findTeachersLanguages(Model theModel) {
        theModel.addAttribute("teachers", teacherDataService.findOneTwoThreeLanguageTeachers());

        return "queries/teachers-languages";
    }

    // query 5
    @GetMapping("/studentsCoursePayments")
    public String findStudentsWithCoursePayments(Model theModel) {
        System.out.println("in get");
        theModel.addAttribute("cpType", "full"); // full or 50percent
        theModel.addAttribute("students", new ArrayList<>());

        return "queries/students-course-payments";
    }

    @PostMapping("/studentsCoursePayments")
    public String findStudentsWithCoursePayments(@RequestParam("cpType") String cpType, Model theModel) {
        System.out.println("in post: " + cpType);
        theModel.addAttribute("cpType", cpType);
        switch (cpType) {
            case "full":
                theModel.addAttribute("students", studentDataService.findStudentsWithoutCoursePayments());
                break;
            case "50p":
                theModel.addAttribute("students", studentDataService.findStudentsWithCoursePaymentsOf50Percent());
                break;
        }

        return "queries/students-course-payments";
    }
}
