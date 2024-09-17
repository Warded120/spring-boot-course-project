package com.ivan.course.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.ArrayList;

@Controller
public class MainController {

    @GetMapping("/")
    public String index(Model model) {
        return "redirect:/discover";
    }

    @GetMapping("/discover")
    public String discover(Model theModel) {

        theModel.addAttribute("courses", new ArrayList<>());

        return "main/discover-page";
    }

    @GetMapping("/about")
    public String about(Model theModel) {
        return "about-us-page";
    }

    @GetMapping("/home")
    public String home() {
        return "main/home-page";
    }
}
