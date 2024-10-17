package com.ivan.course.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class UserController {
    @GetMapping("/profile")
    public String profile() {
        return "user/profile-page";
    }

    @GetMapping("/profile/update")
    public String update() {



        return "update-teacher-page";
    }
}
