package com.ivan.course.controller;

import com.ivan.course.dto.usersDto.StudentDto;
import com.ivan.course.entity.Course;
import com.ivan.course.entity.CoursePayment;
import com.ivan.course.entity.student.Student;
import com.ivan.course.service.coursePayment.CoursePaymentService;
import com.ivan.course.service.student.StudentService;
import com.ivan.course.service.studentData.StudentDataService;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.List;

@Controller
@RequestMapping("/student")
public class StudentController {

    StudentService studentService;
    StudentDataService studentDataService;
    CoursePaymentService coursePaymentService;

    // custom properties
    StudentDto currentStudentDto;

    @Autowired
    public StudentController(StudentService studentService, StudentDataService studentDataService, CoursePaymentService coursePaymentService) {
        this.studentService = studentService;
        this.studentDataService = studentDataService;
        this.coursePaymentService = coursePaymentService;
    }

    @GetMapping("/profile")
    public String profile() {
        return "user/profile-page";
    }

    @GetMapping("/profile/update")
    public String update(Model theModel, HttpSession session) {

        Student student = (Student) session.getAttribute("student");

        StudentDto studentDto = new StudentDto(student);

        theModel.addAttribute("student", studentDto);

        return "user/update-student-form";
    }

    @PostMapping("/profile/update")
    public String update(@Valid @ModelAttribute("student") StudentDto studentDto,
                         BindingResult theBindingResult,
                         HttpSession session) {

        if(thereAreErrorsIn(theBindingResult, List.of("username", "password", "confirmPassword"))) {
            return "user/update-student-form";
        }

        Student dbStudent = studentService.findByUserId(studentDto.getId());

        dbStudent.getStudentData().update(studentDto.getFirstName(), studentDto.getLastName(), studentDto.getBirthDate());

        //student.save(updatedStudent, false);
        studentDataService.save(dbStudent.getStudentData());

        session.setAttribute("student", dbStudent);
        currentStudentDto = studentDto;

        return "redirect:/student/profile/success";
    }

    @GetMapping("/profile/success")
    public String success(Model theModel) {

        theModel.addAttribute("student", currentStudentDto);

        return "user/update-confirm-page";
    }

    @GetMapping("/courses")
    public String myCourses(Model theModel, HttpSession theSession) {

        List<Course> studentCourses = studentService.getCoursesByStudent((Student) theSession.getAttribute("student"));

        theModel.addAttribute("courses", studentCourses);

        return "student/courses-list-page";
    }

    @GetMapping("/balance")
    public String getBalance(Model theModel, HttpSession theSession) {

        Student student = (Student) theSession.getAttribute("student");
        Student dbStudent = studentService.getStudentBySessionStudent(student);

        theSession.setAttribute("student", dbStudent);

        theModel.addAttribute("depositAmount", (float)0.0);

        return "student/balance-page";
    }

    @PostMapping("/balance")
    public String postBalance(@ModelAttribute("depositAmount") Float depositAmount, HttpSession theSession) {

        Student student = studentService.getStudentBySessionStudent((Student) theSession.getAttribute("student"));
        student.getStudentData().addBalance(depositAmount);

        studentService.save(student, false);

        return "redirect:/student/balance/success";
    }

    @GetMapping("/balance/success")
    public String successBalance(Model theModel) {
        return "student/balance-confirm-page";
    }

    @GetMapping("/payments")
    public String payments(Model theModel, HttpSession theSession) {
        Student student = studentService.getSessionStudent();

        List<CoursePayment> coursePayments = student.getStudentData().getCoursePayments();

        theModel.addAttribute("coursePayments", coursePayments);

        return "student/course-payments-page";
    }

    @GetMapping("/payments/pay-off/{paymentId}")
    public String payOff(@PathVariable Integer paymentId, Model theModel, HttpSession theSession) {
        CoursePayment payment = coursePaymentService.getById(paymentId);

        float defaultPayOffAmount = payment.getStudent().getBalance() >= payment.getPayment() ? payment.getPayment() : payment.getStudent().getBalance();

        theModel.addAttribute("payment", payment);
        theModel.addAttribute("payOffAmount", defaultPayOffAmount);

        return "student/payment-pay-off-page";
    }

    @PostMapping("/payments/pay-off/{paymentId}")
    public String postPayOff(@PathVariable("paymentId") Integer paymentId,
                             @RequestParam("payOffAmount") Float payOffAmount) {

        CoursePayment coursePayment = coursePaymentService.getById(paymentId);
        Student student = studentService.getSessionStudent();


        if (coursePayment.getPayment() < payOffAmount) {
            return "redirect:/student/payments/pay-off/" + coursePayment.getId() + "?payOffAmountError"; // return to previous mapping
        } else if (payOffAmount > student.getStudentData().getBalance()) {
            return "redirect:/student/payments/pay-off/" + coursePayment.getId() + "?notEnoughBalanceError"; // return to previous mapping
        }

        if (coursePayment.getPayment() > payOffAmount) {
            // if not paid, then return to previous mapping
            if (!coursePayment.payOff(payOffAmount)) {
                return "redirect:/student/payments/pay-off/" + coursePayment.getId() + "?notEnoughBalanceError"; // return to previous mapping
            }

            coursePaymentService.save(coursePayment);
            String message = "Вам залишилось оплатити $" + coursePayment.getPayment();

            return "redirect:/student/payments/pay-off/success?payOffAmount=" + payOffAmount + "&message=" + encodeURIComponent(message);

        } else if (coursePayment.getPayment() == payOffAmount) {
            // successfully paid off the payment for the course
            student.getStudentData().removeCoursePayment(coursePayment);
            studentService.save(student, false);
            coursePaymentService.deleteById(coursePayment.getId());

            return "redirect:/student/payments/pay-off/success?payOffAmount=" + payOffAmount + "&message=" + encodeURIComponent("Ви повністю оплатили за курс");
        }

        return "redirect:/student/payments?error";
    }

    @GetMapping("/payments/pay-off/success")
    public String payOffSuccess(@RequestParam(value = "payOffAmount", required = false) Float payOffAmount,
                                @RequestParam(value = "message", required = false) String message,
                                Model theModel) {

        if (payOffAmount == null || message == null) {
        }

        theModel.addAttribute("payOffAmount", payOffAmount);
        theModel.addAttribute("message", message);

        return "student/payment-pay-off-confirmation";
    }

    @GetMapping("/certificates")
    public String viewCertificates(Model theModel) {

        Student student = studentService.getSessionStudent();

        theModel.addAttribute("certificates", student.getStudentData().getCertificates());

        return "student/certificates-page";
    }

    boolean thereAreErrorsIn(BindingResult theBindingResult, List<String> fieldsToIgnore) {
        if (!theBindingResult.hasErrors()) {
            return false;
        }

        for(FieldError theFieldError : theBindingResult.getFieldErrors()) {
            if(!fieldsToIgnore.contains(theFieldError.getField())) {
                return true;
            }
        }
        return false;
    }

    // Helper method to encode URL parameters
    private String encodeURIComponent(String value) {
        return URLEncoder.encode(value, StandardCharsets.UTF_8);
    }
}
