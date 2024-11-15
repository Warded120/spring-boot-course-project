package com.ivan.course.exceptionHandling.controllerAdvice;

import com.ivan.course.exceptionHandling.exception.NoStudentFoundException;
import com.ivan.course.exceptionHandling.exception.NoTeacherFoundException;
import com.ivan.course.exceptionHandling.exception.NoUserFoundException;
import org.eclipse.angus.mail.util.MailConnectException;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;

//TODO: test whether the methods return an html pages
@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(value = NoUserFoundException.class)
    @GetMapping("/error/UserNotFoundException")
    public String userNotFoundExceptionHandler(NoUserFoundException ex, Model theModel) {
        System.out.println("in userNotFoundExceptionHandler");

        theModel.addAttribute("errorTitle", "Користувач не знайдений");
        theModel.addAttribute("message", ex.what());

        return "redirect:/error?UserNotFound";
    }

    @ExceptionHandler(value = MailConnectException.class)
    @GetMapping("/error/MailConnectionException")
    public String mailConnectExceptionHandler(MailConnectException ex, Model theModel) {
        System.out.println("in mailConnectExceptionHandler");

        theModel.addAttribute("errorTitle", "Помилка відправки листа");
        theModel.addAttribute("message", ex.getMessage());

        // FIXME: fix redirect. pass cannotMail param
        return "redirect:/error?UserNotFound";
    }

    @ExceptionHandler(value = NoTeacherFoundException.class)
    public String noTeacherFoundExceptionHandler(NoTeacherFoundException ex) {
        System.out.println("in noTeacherFoundExceptionHandler");

        System.out.println(ex.what());

        return "redirect:/login?noTeacherError";
    }

    @ExceptionHandler(value = NoStudentFoundException.class)
    public String noStudentFoundExceptionHandler(NoTeacherFoundException ex) {
        System.out.println("in noStudentFoundExceptionHandler");

        System.out.println(ex.what());

        return "redirect:/login?noStudentError";
    }
}
