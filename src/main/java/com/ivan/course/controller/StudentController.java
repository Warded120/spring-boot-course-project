package com.ivan.course.controller;

import com.ivan.course.dto.StudentDto;
import com.ivan.course.entity.student.Student;
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

import java.util.List;

@Controller
@RequestMapping("/student")
public class StudentController {

    StudentService studentService;
    StudentDataService studentDataService;

    // custom properties
    StudentDto currentStudentDto;

    @Autowired
    public StudentController(StudentService studentService, StudentDataService studentDataService) {
        this.studentService = studentService;
        this.studentDataService = studentDataService;
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

        return "user/update-student-page";
    }

    @PostMapping("/profile/update")
    public String update(@Valid @ModelAttribute("student") StudentDto studentDto,
                         BindingResult theBindingResult,
                         HttpSession session,
                         Model theModel) {

/*        if (theBindingResult.hasErrors()) {
            System.out.println("errors exist");
            return "user/update-student-page";
        }*/
        if(thereAreErrorsIn(theBindingResult, List.of("username", "password", "confirmPassword"))) {
            System.out.println("errors exist");
            return "user/update-student-page";
        }
        System.out.println("no errors exist");

        System.out.println("studentDto = " + studentDto);
        Student updatedStudent = new Student(studentDto);
        System.out.println("updated student: " + updatedStudent);


        //student.save(updatedStudent, false);
        studentDataService.save(updatedStudent.getStudentData());

        session.setAttribute("student", updatedStudent);
        currentStudentDto = studentDto;

        return "redirect:/student/profile/success";
    }

    @GetMapping("/profile/success")
    public String success(Model theModel) {

        theModel.addAttribute("student", currentStudentDto);

        return "user/update-confirm-page";
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
}
