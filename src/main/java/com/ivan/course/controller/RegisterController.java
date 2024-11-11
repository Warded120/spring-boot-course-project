package com.ivan.course.controller;

import com.ivan.course.constants.ConstantsCollection;
import com.ivan.course.dto.usersDto.StudentDto;
import com.ivan.course.dto.usersDto.TeacherDto;
import com.ivan.course.entity.student.Student;
import com.ivan.course.entity.teacher.Teacher;
import com.ivan.course.entity.user.User;
import com.ivan.course.service.role.RoleService;
import com.ivan.course.service.student.StudentService;
import com.ivan.course.service.teacher.TeacherService;
import com.ivan.course.service.user.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import java.util.List;

@Controller
public class RegisterController {

    StudentService studentService;
    TeacherService teacherService;
    UserService userService;
    RoleService roleService;

    List<String> studentRoles = ConstantsCollection.getStudentRoles();

    List<String> teacherRoles = ConstantsCollection.getTeacherRoles();

    @Autowired
    public RegisterController(StudentService studentService, TeacherService teacherService, UserService userService,
                              RoleService roleService) {
        this.studentService = studentService;
        this.teacherService = teacherService;
        this.userService = userService;
        this.roleService = roleService;
    }

    @InitBinder
    public void initBinder(WebDataBinder dataBinder) {
        StringTrimmerEditor trimmerEditor = new StringTrimmerEditor(true);
        dataBinder.registerCustomEditor(String.class, trimmerEditor);
    }

    @GetMapping("/register")
    public String register() {
        return "register/register-form";
    }

    @GetMapping("/register-student")
    public String registerStudent(Model theModel) {

        theModel.addAttribute("student", new StudentDto());
        theModel.addAttribute("roles", studentRoles);

        return "register/student-form";
    }

    @PostMapping("/register-student")
    public String registerStudent(@Valid @ModelAttribute("student") StudentDto studentDto,
                                  BindingResult theBindingResult,
                                  Model theModel) {

        if (theBindingResult.hasErrors()) {
            return "register/student-form";
        }

        Student newStudent = new Student(studentDto);

        studentService.save(newStudent);

        return "redirect:/register-student/success";
    }

    @GetMapping("/register-student/success")
    public String registerStudentSuccess(Model theModel) {
        return "register/register-confirm-page";
    }

    @GetMapping("/register-teacher")
    public String registerTeacher(Model theModel) {

        theModel.addAttribute("teacher", new TeacherDto());
        theModel.addAttribute("roles", teacherRoles);

        return "register/teacher-form";
    }

    @PostMapping("/register-teacher")
    public String registerTeacher(@Valid @ModelAttribute("teacher") TeacherDto teacherDto,
                                  BindingResult theBindingResult,
                                  Model theModel) {

        if (theBindingResult.hasErrors()) {
            return "register/teacher-form";
        }

        String username = teacherDto.getUsername();
        User existing = userService.findByUsername(username);

        if(existing != null) {

            theModel.addAttribute("registerError", "Ім'я користувача вже зайняте");

            return "register/teacher-form";
        }

        Teacher newTeacher = new Teacher(teacherDto);

        teacherService.save(newTeacher);

        return "redirect:/register-teacher/success";
    }

    @GetMapping("/register-teacher/success")
    public String registerTeacherSuccess(Model theModel) {
        return "register/register-confirm-page";
    }
}