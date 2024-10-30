package com.ivan.course.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class SuperUserController {

    // TODO: finish profile page for operator
    // TODO: create a default operator and admin if they don't exist (hardcoded users)
    // TODO: admin has an operator registration form
    // TODO: implement db queries
    @GetMapping("/operator/profile")
    public String profile() {
        return "/user/profile-page";
    }
}
