package com.ivan.course.controller;

import com.ivan.course.dto.usersDto.TeacherDto;
import com.ivan.course.entity.Course;
import com.ivan.course.entity.teacher.Teacher;
import com.ivan.course.service.teacher.TeacherService;
import com.ivan.course.service.teacherData.TeacherDataService;
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
@RequestMapping("/teacher")
public class TeacherController {

    TeacherService teacherService;
    TeacherDataService teacherDataService;

    // custom properties
    TeacherDto currentTeacherDto;

    @Autowired
    public TeacherController(TeacherService teacherService, TeacherDataService teacherDataService) {
        this.teacherService = teacherService;
        this.teacherDataService = teacherDataService;
    }

    @GetMapping("/profile")
    public String profile() {
        return "user/profile-page";
    }

    @GetMapping("/profile/update")
    public String update(Model theModel, HttpSession session) {

        Teacher teacher = (Teacher) session.getAttribute("teacher");

        TeacherDto teacherDto = new TeacherDto(teacher);

        theModel.addAttribute("teacher", teacherDto);

        return "user/update-teacher-form";
    }

    @PostMapping("/profile/update")
    public String update(@Valid @ModelAttribute("teacher") TeacherDto teacherDto,
                         BindingResult theBindingResult,
                         HttpSession session) {

        if(thereAreErrorsIn(theBindingResult, List.of("username", "password", "confirmPassword"))) {
            System.out.println("errors exist");
            return "user/update-teacher-form";
        }
        System.out.println("no errors exist");

        Teacher updatedTeacher = new Teacher(teacherDto);

        //set courses to updated teacher
        Teacher teacherWithCourses = teacherService.findByUserId(updatedTeacher.getId());
        List<Course> coursesOfTeacher = teacherWithCourses.getTeacherData().getCourses();
        updatedTeacher.getTeacherData().setCourses(coursesOfTeacher);

        //teacherService.save(updatedTeacher, false);
        teacherDataService.save(updatedTeacher.getTeacherData());

        session.setAttribute("teacher", updatedTeacher);
        currentTeacherDto = teacherDto;

        return "redirect:/teacher/profile/success";
    }

    @GetMapping("/profile/success")
    public String success(Model theModel) {

        theModel.addAttribute("teacher", currentTeacherDto);

        return "user/update-confirm-page";
    }

    @GetMapping("/courses")
    public String myCourses(Model theModel) {

        Teacher teacher = teacherService.getSessionTeacher();

        theModel.addAttribute("courses", teacher.getTeacherData().getCourses());

        return "teacher/courses-list-page";
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
