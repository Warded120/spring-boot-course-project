package com.ivan.course.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

// TODO: this class may be useless
@Controller
public class UserController {
    @GetMapping("/profile")
    public String profile() {
        return "user/profile-page";
    }
}
