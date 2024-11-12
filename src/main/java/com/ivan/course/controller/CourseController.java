package com.ivan.course.controller;

import com.ivan.course.constants.ConstantsCollection;
import com.ivan.course.constants.CourseState;
import com.ivan.course.constants.EnrollStatus;
import com.ivan.course.dto.CourseDto;
import com.ivan.course.dto.CoursePaymentDto;
import com.ivan.course.dto.examinationDto.ExaminationDto;
import com.ivan.course.dto.examinationDto.StudentMark;
import com.ivan.course.entity.Certificate;
import com.ivan.course.entity.Course;
import com.ivan.course.entity.CoursePayment;
import com.ivan.course.entity.Examination;
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
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

    List<String> languages = ConstantsCollection.getLanguages();

    List<String> languageLevels = ConstantsCollection.getLanguageLevels();

    @InitBinder
    public void initBinder(WebDataBinder dataBinder) {
        StringTrimmerEditor trimmerEditor = new StringTrimmerEditor(true);
        dataBinder.registerCustomEditor(String.class, trimmerEditor);
    }

    @GetMapping("/add")
    public String addCourse(Model theModel) {

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
            StudentData studentData = theStudent.getStudentData();

            // if student is not already enrolled...
            if (!course.getStudentGroup().getStudents().contains(studentData)) {
                enrollStatus = course.enroll(theStudent);
            }
        }

        courseService.save(course);

        return "redirect:/course/confirm/enroll/" + enrollStatus.ordinal();
    }

    @GetMapping("/confirm/enroll/{status}")
    public String successEnrollToCourse(@PathVariable(name = "status", required = false) int statusValue, Model theModel) {

        EnrollStatus status = EnrollStatus.values()[statusValue];
        theModel.addAttribute("status", status.name());

        return "course/course-enroll-confirmation";
    }

    @GetMapping("/enroll/partial/{courseId}")
    public String partialPaymentToEnroll(@PathVariable("courseId") int courseId, Model theModel) {
        Course course = courseService.findById(courseId);

        theModel.addAttribute("coursePayment", new CoursePaymentDto(studentService.getSessionStudent().getStudentData().getId(), course.getId(), course.getPrice()));

        return "course/course-partial-payment-form";
    }

    @PostMapping("/enroll/partial/{courseId}")
    public String postPartialPaymentToEnroll(@PathVariable("courseId") int courseId, @ModelAttribute("debt") CoursePaymentDto theCoursePayment) {
        Student student = studentService.getSessionStudent();
        Course course = courseService.findById(theCoursePayment.getCourseId());

        /* moved this functionality to debtEnroll() function
        CoursePayment coursePayment = new CoursePayment(student.getStudentData(), course, theCoursePayment.getPayment());

        student.getStudentData().addCoursePayment(coursePayment);
        */

        EnrollStatus enrollStatus = course.debtEnroll(student);

        studentService.save(student, false);

        return "redirect:/course/confirm/enroll/" + enrollStatus.ordinal();
    }

    @PostMapping("/start/{courseId}")
    public String start(@PathVariable("courseId") int courseId, Model theModel) {
        Course course = courseService.findById(courseId);

        course.start();

        courseService.save(course);

        return "redirect:/teacher/courses";
    }

    @GetMapping("/examination/{courseId}")
    public String examination(@PathVariable("courseId") int courseId, Model theModel) {
        Course course = courseService.findById(courseId);

        theModel.addAttribute("exam", new ExaminationDto(course));

        return "course/course-examination-form";
    }

    @PostMapping("/examination/submit")
    public String submitExamination(@ModelAttribute("exam") ExaminationDto theExamination, Model theModel) {
        Course course = courseService.findById(theExamination.getCourseId());

        Map<Student, Integer> studentMarks = new HashMap<>();

        for(StudentMark studentMark : theExamination.getStudentMarks()) {
            Student student = studentService.findByUserId(studentMark.getStudentId());

            Certificate certificate = new Certificate(0, student.getStudentData(), course, studentMark.getMark());

            student.getStudentData().addCertificate(certificate);

            studentMarks.put(student, studentMark.getMark());

            studentService.save(student, false, false);
        }

        Examination examination = new Examination(course, studentMarks);
        course.setExamination(examination);
        course.setEndDate(LocalDate.now());
        course.setState(CourseState.FINISHED);
        courseService.save(course);

        // create a next level course
        Course nextLevelCourse = course.getNextLevelCourse();
        if (nextLevelCourse != null) {
            courseService.save(nextLevelCourse);
        }

        return "redirect:/course/examination/success";
    }

    @GetMapping("/examination/success")
    public String successExamination() {
        return "course/course-examination-confirmation";
    }

    @GetMapping("/exam-result/{courseId}")
    public String courseExaminationResult(@PathVariable int courseId, Model theModel) {
        Course course = courseService.findById(courseId);

        theModel.addAttribute("course", course);

        return "course/course-examination-page";
    }

    // TODO: add "remove course" for student and teacher
}