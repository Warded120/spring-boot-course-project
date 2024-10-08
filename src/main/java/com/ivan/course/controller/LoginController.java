package com.ivan.course.controller;

import com.ivan.course.dto.usersDto.UserDto;
import com.ivan.course.entity.Keys;
import com.ivan.course.entity.user.User;
import com.ivan.course.exceptionHandling.UserNotFoundException;
import com.ivan.course.service.user.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class LoginController {

    UserService userService;
    BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    public LoginController(UserService userService, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.userService = userService;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    @GetMapping("/login")
    public String login() {
        return "login/login-page";
    }

    @GetMapping("/access-denied")
    public String accessDenied() {
        return "access-denied";
    }

    @GetMapping("/error")
    public String error() {
        return "error/error-page";
    }

    @GetMapping("/change-password")
    public String changePassword(@ModelAttribute("user") User changePasswordUser,
                                 Model theModel) {

        UserDto userDto = new UserDto(changePasswordUser);

        theModel.addAttribute("user", userDto);

        return "user/change-password-form";
    }

    @PostMapping("/change-password")
    public String confirmChangePassword(@Valid @ModelAttribute("user") UserDto userDto,
                                        BindingResult theBindingResult,
                                        Model theModel) throws UserNotFoundException {
        if (theBindingResult.hasErrors()) {
            theModel.addAttribute("user", userDto);
            return "user/change-password-form";
        }

        User changePasswordUser = userService.findByUsername(userDto.getUsername());

        if (changePasswordUser == null) {
            throw new UserNotFoundException("user doesn't exist", userDto.getUsername());
        }

        changePasswordUser.setPassword(new Keys(bCryptPasswordEncoder.encode(userDto.getPassword())));

        userService.save(changePasswordUser);

        return "redirect:/change-password/success";
    }

    @GetMapping("/change-password/success")
    public String successfulPasswordChange() {
        return "user/change-password-confirm-page";
    }
}
