package com.ivan.course.controller;

import com.ivan.course.dto.CourseDto;
import com.ivan.course.entity.Course;
import com.ivan.course.entity.student.Student;
import com.ivan.course.entity.student.StudentData;
import com.ivan.course.entity.teacher.Teacher;
import com.ivan.course.entity.teacher.TeacherData;
import com.ivan.course.exceptionHandling.exception.NoStudentFoundException;
import com.ivan.course.exceptionHandling.exception.NoTeacherFoundException;
import com.ivan.course.service.course.CourseService;
import com.ivan.course.service.studentGroup.StudentGroupService;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/course")
public class CourseController {

    CourseService courseService;
    StudentGroupService studentGroupService;

    @Autowired
    public CourseController(CourseService courseService, StudentGroupService studentGroupService) {
        this.courseService = courseService;
        this.studentGroupService = studentGroupService;
    }

    @Value("${course.languages}")
    List<String> languages;

    @Value("${course.language-levels}")
    List<String> languageLevels;

    @InitBinder
    public void initBinder(WebDataBinder dataBinder) {
        StringTrimmerEditor trimmerEditor = new StringTrimmerEditor(true);
        dataBinder.registerCustomEditor(String.class, trimmerEditor);
    }

    @GetMapping("/add")
    public String addCourse(Model theModel, HttpSession session) {

        CourseDto course = new CourseDto();

        Teacher teacher = (Teacher) session.getAttribute("teacher");

        if(teacher == null) {
            throw new NoTeacherFoundException("teacher not found");
        }

        theModel.addAttribute("course", course);
        theModel.addAttribute("languages", languages);
        theModel.addAttribute("languageLevels", languageLevels);

        return "course/course-form";
    }

    @PostMapping("/add")
    public String postAddCourse(@Valid @ModelAttribute("course") CourseDto theCourse,
                                BindingResult theBindingResult,
                                Model theModel,
                                HttpSession theSession) {
        if(theBindingResult.hasErrors()) {
            return "course/course-form";
        }

        Course course = new Course(theCourse);

        course.setTeacher( ((Teacher)theSession.getAttribute("teacher")).getTeacherData() );

        Course savedCourse = courseService.save(course);
        TeacherData teacherData = savedCourse.getTeacher();
        teacherData.addCourse(course);
        Teacher sessionTeacher = (Teacher)theSession.getAttribute("teacher");
        sessionTeacher.setTeacherData(teacherData);
        theSession.setAttribute("teacher", sessionTeacher);

        return "redirect:/course/add/success";
    }

    @GetMapping("/add/success")
    public String addCourseSuccess() {
        return "course/course-form-confirmation";
    }

    @GetMapping("/{courseId}")
    public String viewCourse(@PathVariable("courseId") int courseId, Model theModel) {
        Course course = courseService.findById(courseId);

        theModel.addAttribute("course", course);

        return "course/course-page";
    }

    @GetMapping("/enroll/{courseId}")
    public String enrollToCourse(@PathVariable("courseId") int courseId, Model theModel, HttpSession theSession) {
        System.out.println("in get enroll id");
        if((theSession.getAttribute("student")) == null) {
            throw new NoStudentFoundException("student not found");
        }

        Course course = courseService.findById(courseId);

        theModel.addAttribute("course", course);

        return "course/course-enroll-form";
    }

    //TODO: detached studentData, multiple instances of a same object
    @PostMapping("/enroll/{courseId}")
    public String confirmEnrollToCourse(@PathVariable("courseId") int courseId, HttpSession theSession) {
        System.out.println("in post enroll id");

        Student theStudent = (Student) theSession.getAttribute("student");
        Course course = courseService.findById(courseId);

        // Ensure that only one instance of the student data is used
        if (theStudent != null) {
            System.out.println("student exists");
            StudentData studentData = theStudent.getStudentData();

            // Avoid enrolling a detached instance
            if (!course.getStudentGroup().getStudents().contains(studentData)) {
                System.out.println("enrolling");
                course.enroll(theStudent);
            } else {
                System.out.println("not enrolling");
            }
        }

        courseService.save(course);

        return "redirect:/course/enroll/confirm";
    }


    @GetMapping("/confirm/enroll")
    public String successEnrollToCourse() {
        return "course/course-enroll-confirmation";
    }
}