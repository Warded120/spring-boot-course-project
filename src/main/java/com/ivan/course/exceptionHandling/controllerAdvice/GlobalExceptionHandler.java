package com.ivan.course.exceptionHandling.controllerAdvice;

import com.ivan.course.exceptionHandling.UserNotFoundException;
import org.eclipse.angus.mail.util.MailConnectException;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(value = UserNotFoundException.class)
    @GetMapping("/error/UserNotFound")
    public String userNotFoundExceptionHandler(UserNotFoundException ex, Model theModel) {
        System.out.println("in userNotFoundExceptionHandler");

        theModel.addAttribute("errorTitle", "User Not Found");
        theModel.addAttribute("message", ex.what());

        return "error/error-page";
    }

    @ExceptionHandler(value = MailConnectException.class)
    @GetMapping("/error/MailConnectionException")
    public String mailConnectExceptionHandler(MailConnectException ex, Model theModel) {
        System.out.println("in mailConnectExceptionHandler");

        theModel.addAttribute("errorTitle", "Mail Connect Error");
        theModel.addAttribute("message", ex.getMessage());

        return "error/error-page";
    }
}
