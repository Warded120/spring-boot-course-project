package com.ivan.course.controller;

import com.ivan.course.dto.usersDto.SuperUserDto;
import com.ivan.course.dto.usersDto.TeacherDto;
import com.ivan.course.entity.Course;
import com.ivan.course.entity.superuser.SuperUser;
import com.ivan.course.entity.teacher.Teacher;
import com.ivan.course.service.superUser.SuperUserService;
import com.ivan.course.service.superUserData.SuperUserDataService;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/operator")
public class SuperUserController {

    private SuperUserDataService superUserDataService;
    private SuperUserDto currentSuperUserDto;

    @Autowired
    public SuperUserController(SuperUserDataService superUserDataService) {
        this.superUserDataService = superUserDataService;
    }

    // TODO: create a default operator and admin if they don't exist (hardcoded users)
    // TODO: admin has an operator registration form
    // TODO: implement db queries
    @GetMapping("/profile")
    public String profile() {
        return "/user/profile-page";
    }

    @GetMapping("/profile/update")
    public String update(Model theModel, HttpSession session) {

        SuperUser superUser = (SuperUser) session.getAttribute("superUser");

        SuperUserDto superUserDto = new SuperUserDto(superUser);

        theModel.addAttribute("superUser", superUserDto);

        return "user/update-superUser-form";
    }

    @PostMapping("/profile/update")
    public String update(@Valid @ModelAttribute("superUser") SuperUserDto superUserDto,
                         BindingResult theBindingResult,
                         HttpSession session) {

        if(thereAreErrorsIn(theBindingResult, List.of("username", "password", "confirmPassword"))) {
            System.out.println("errors exist");
            return "user/update-superUser-form";
        }
        System.out.println("no errors exist");

        SuperUser updatedUser = new SuperUser(superUserDto);

        superUserDataService.save(updatedUser.getSuperUserData());
        currentSuperUserDto = superUserDto;

        session.setAttribute("superUser", updatedUser);

        return "redirect:/operator/profile/success";
    }

    // TODO: set currentSuperUserDto
    @GetMapping("/profile/success")
    public String success(Model theModel) {

        theModel.addAttribute("superUser", currentSuperUserDto);

        return "user/update-confirm-page";
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
