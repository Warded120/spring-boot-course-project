package com.ivan.course.controller;

import com.ivan.course.constants.EnrollStatus;
import com.ivan.course.dto.CourseDto;
import com.ivan.course.dto.CoursePaymentDto;
import com.ivan.course.entity.Course;
import com.ivan.course.entity.CoursePayment;
import com.ivan.course.entity.student.Student;
import com.ivan.course.entity.student.StudentData;
import com.ivan.course.entity.teacher.Teacher;
import com.ivan.course.entity.teacher.TeacherData;
import com.ivan.course.exceptionHandling.exception.NoStudentFoundException;
import com.ivan.course.exceptionHandling.exception.NoTeacherFoundException;
import com.ivan.course.service.course.CourseService;
import com.ivan.course.service.coursePayment.CoursePaymentService;
import com.ivan.course.service.student.StudentService;
import com.ivan.course.service.studentGroup.StudentGroupService;
import com.ivan.course.service.teacher.TeacherService;
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

// TODO: add course status (prepareState, StartedState, EndedState etc.)
// TODO: AND/OR
// TODO: add course startDate, endDate
@Controller
@RequestMapping("/course")
public class CourseController {

    CourseService courseService;
    StudentGroupService studentGroupService;
    StudentService studentService;
    CoursePaymentService coursePaymentService;
    TeacherService teacherService;

    @Autowired
    public CourseController(CourseService courseService,
                            StudentGroupService studentGroupService,
                            StudentService studentService,
                            CoursePaymentService coursePaymentService,
                            TeacherService teacherService) {
        this.courseService = courseService;
        this.studentGroupService = studentGroupService;
        this.studentService = studentService;
        this.coursePaymentService = coursePaymentService;
        this.teacherService = teacherService;
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

        Teacher teacher = teacherService.getSessionTeacher();

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

        course.setTeacher( teacherService.getSessionTeacher().getTeacherData() );

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
        theModel.addAttribute("service", courseService);

        return "course/course-page";
    }

    @GetMapping("/enroll/{courseId}")
    public String enrollToCourse(@PathVariable("courseId") int courseId, Model theModel, HttpSession theSession) {
        if(studentService.getStudentBySessionStudent((Student) theSession.getAttribute("student")) == null) {
            throw new NoStudentFoundException("student not found");
        }

        Course course = courseService.findById(courseId);

        theModel.addAttribute("course", course);

        return "course/course-enroll-form";
    }

    @PostMapping("/enroll/{courseId}")
    public String confirmEnrollToCourse(@PathVariable("courseId") int courseId, HttpSession theSession) {
        Student theStudent = (Student) theSession.getAttribute("student");
        Course course = courseService.findById(courseId);

        EnrollStatus enrollStatus = EnrollStatus.CANNOT_ENROLL;
        if (theStudent != null) {
            System.out.println("student exists");
            StudentData studentData = theStudent.getStudentData();

            // if student is not already enrolled...
            if (!course.getStudentGroup().getStudents().contains(studentData)) {
                System.out.println("enrolling");
                enrollStatus = course.enroll(theStudent);
            } else {
                System.out.println("not enrolling");
            }
        }

        courseService.save(course);

        System.out.println("enroll status: " + enrollStatus.name());
        return "redirect:/course/confirm/enroll/" + enrollStatus.ordinal();
    }

    @GetMapping("/confirm/enroll/{status}")
    public String successEnrollToCourse(@PathVariable(name = "status", required = false) int status, Model theModel) {

        theModel.addAttribute("status", status);

        return "course/course-enroll-confirmation";
    }

    @GetMapping("/enroll/partial/{courseId}")
    public String partialPaymentToEnroll(@PathVariable("courseId") int courseId, Model theModel) {
        Course course = courseService.findById(courseId);

        System.out.println("get /enroll/partial/{courseId}: " + course);
        theModel.addAttribute("coursePayment", new CoursePaymentDto(studentService.getSessionStudent().getStudentData().getId(), course.getId(), course.getPrice()));

        return "course/course-partial-payment-form";
    }

    @PostMapping("/enroll/partial/{courseId}")
    public String postPartialPaymentToEnroll(@PathVariable("courseId") int courseId, @ModelAttribute("debt") CoursePaymentDto theCoursePayment) {
        System.out.println("in postPartialPaymentToEnroll()");
        Student student = studentService.getSessionStudent();
        Course course = courseService.findById(theCoursePayment.getCourseId());

        System.out.println("post /enroll/partial/{courseId}: " + course);

        CoursePayment coursePayment = new CoursePayment(student.getStudentData(), course, theCoursePayment.getPayment());

        student.getStudentData().addCoursePayment(coursePayment);
        EnrollStatus enrollStatus = course.enrollDebtStudent(student);

        studentService.save(student, false);

        return "redirect:/course/confirm/enroll/" + enrollStatus.ordinal();
    }

    @PostMapping("/start/{courseId}")
    public String start(@PathVariable("courseId") int courseId, Model theModel) {
        Course course = courseService.findById(courseId);

        course.start();

        courseService.save(course);

        return "redirect:/teacher/courses-list-page";
    }

    // TODO: add "remove course" for student and teacher
}