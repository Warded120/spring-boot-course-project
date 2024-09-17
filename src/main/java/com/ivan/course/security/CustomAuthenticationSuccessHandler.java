package com.ivan.course.security;

import com.ivan.course.entity.student.Student;
import com.ivan.course.entity.teacher.Teacher;
import com.ivan.course.entity.user.User;
import com.ivan.course.service.student.StudentService;
import com.ivan.course.service.teacher.TeacherService;
import com.ivan.course.service.user.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class CustomAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

    private final UserService userService;
    private final TeacherService teacherService;
    private final StudentService studentService;

    @Autowired
    public CustomAuthenticationSuccessHandler(UserService theUserService, TeacherService theTeacherService, StudentService theStudentService) {
        userService = theUserService;
        teacherService = theTeacherService;
        studentService = theStudentService;
    }

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication)
            throws IOException {

        String userName = authentication.getName();

        User theUser = userService.findByUsername(userName);

        // now place in the session
        HttpSession session = request.getSession();
        session.setAttribute("user", theUser);

        if (teacherService.existsByUserId(theUser.getId())) {
            Teacher theTeacher = teacherService.findByUserId(theUser.getId());
            session.setAttribute("teacher", theTeacher);
        } else if (studentService.existsByUserId(theUser.getId())) {
            Student theStudent = studentService.findByUserId(theUser.getId());
            session.setAttribute("student", theStudent);
        }

        // forward to home page
        response.sendRedirect(request.getContextPath() + "/home");
    }

}
