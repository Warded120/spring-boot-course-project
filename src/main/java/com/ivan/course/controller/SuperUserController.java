package com.ivan.course.controller;

import com.ivan.course.dto.usersDto.SuperUserDto;
import com.ivan.course.entity.superuser.SuperUser;
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

    private final SuperUserService superUserService;
    private final SuperUserDataService superUserDataService;
    private SuperUserDto currentSuperUserDto;

    @Autowired
    public SuperUserController(SuperUserService superUserService, SuperUserDataService superUserDataService) {
        this.superUserService = superUserService;
        this.superUserDataService = superUserDataService;
    }

    @GetMapping("/profile")
    public String profile(HttpSession session, Model theModel) {

        SuperUser superUser = (SuperUser) session.getAttribute("superUser");

        theModel.addAttribute("superUser", superUser);

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

        SuperUser dbUser = superUserService.findById(superUserDto.getId());

        dbUser.getSuperUserData().update(superUserDto.getFirstName(), superUserDto.getLastName(), superUserDto.getBirthDate());

        superUserDataService.save(dbUser.getSuperUserData());
        currentSuperUserDto = superUserDto;

        session.setAttribute("superUser", dbUser);

        return "redirect:/operator/profile/success";
    }

    @GetMapping("/profile/success")
    public String success(Model theModel) {

        theModel.addAttribute("superUser", currentSuperUserDto);

        return "user/update-confirm-page";
    }

    @GetMapping("/create-operator")
    public String createOperator(Model theModel) {
        theModel.addAttribute("operator", new SuperUserDto());

        return "register/operator-form";
    }

    @PostMapping("/create-operator")
    public String createOperator(@Valid @ModelAttribute("operator") SuperUserDto superUserDto, BindingResult theBindingResult) {

        if(theBindingResult.hasErrors()) {
            return "register/operator-form";
        }

        SuperUser superUser = new SuperUser(superUserDto);
        superUserService.save(superUser, true, false);

        return "redirect:/operator/create-operator/success";
    }

    @GetMapping("/create-operator/success")
    public String success() {
        return "register/create-operator-confirm-page";
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
