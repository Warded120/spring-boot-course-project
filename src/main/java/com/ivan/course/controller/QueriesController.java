package com.ivan.course.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/queries")
public class QueriesController {
    // TODO: implement all queries
    // TODO: create new branch
    @GetMapping("/test")
    public String find() {
        return "test";
    }
}
