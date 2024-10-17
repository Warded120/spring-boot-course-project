package com.ivan.course.controller;

import com.ivan.course.entity.Course;
import com.ivan.course.entity.student.Student;
import com.ivan.course.service.course.CourseService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class MainController {

    CourseService courseService;

    @Autowired
    public MainController(CourseService courseService) {
        this.courseService = courseService;
    }

    @GetMapping("/")
    public String index(Model model) {
        return "redirect:/discover";
    }

    @GetMapping("/discover")
    public String discover(Model theModel) {

        List<Course> courses = courseService.findAll();

        theModel.addAttribute("courses", courses);

        return "main/discover-page";
    }

    @GetMapping("/about")
    public String about(Model theModel) {
        return "main/about-us-page";
    }

    @GetMapping("/home")
    public String home(Model theModel, HttpSession session) {
        Student student = (Student) session.getAttribute("student");

        List<Course> courses;
        if(student == null) {
            courses = courseService.findAll();
        } else {
            courses = courseService.findNotEnrolledCoursesByStudent(student);
        }

        theModel.addAttribute("courses", courses);

        return "main/home-page";
    }
}