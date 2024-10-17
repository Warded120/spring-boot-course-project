package com.ivan.course.controller;

import com.ivan.course.entity.EmailVerification;
import com.ivan.course.entity.user.User;
import com.ivan.course.exceptionHandling.exception.UserNotFoundException;
import com.ivan.course.service.email.EmailService;
import com.ivan.course.service.user.UserService;
import jakarta.mail.MessagingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class EmailController {

    private final EmailService emailService;
    private final UserService userService;

    @Autowired
    public EmailController(EmailService emailService, UserService userService) {
        this.emailService = emailService;
        this.userService = userService;
    }

    @GetMapping("/forgot-password")
    public String forgotPassword() {
        return "login/forgot-password-page";
    }

    @PostMapping("/forgot-password")
    public String requestEmail(@RequestParam("email") String email, RedirectAttributes redirectAttributes) throws MessagingException {
        String verificationCode = generateVerificationCode();

        EmailVerification emailVerification = new EmailVerification();

        emailVerification.setEmail(email);
        emailVerification.setActualVerificatoinCode(verificationCode);

        redirectAttributes.addFlashAttribute("emailVerification", emailVerification);

        emailService.sendHtmlEmail(email, "Verification", htmlTemplate.replace("${verificationCode}", verificationCode));

        return "redirect:/verify";
    }

    @GetMapping("/verify")
    public String sendEmail(@ModelAttribute("emailVerification") EmailVerification emailVerification,
                              Model theModel) {

        System.out.println("in GetMapping /verify: " + emailVerification);
        theModel.addAttribute("emailVerification", emailVerification);

        return "login/verify-email-page";
    }

    @PostMapping("/verify")
    public String verifyEmail(@ModelAttribute("emailVerification") EmailVerification emailVerification,
                              Model theModel,
                              RedirectAttributes redirectAttributes) throws UserNotFoundException {
        System.out.println("in PostMapping /verify: " + emailVerification);

        if (emailVerification.getActualVerificatoinCode().equals(emailVerification.getVerificatoinCode())) {
            User changePasswordUser = userService.findByUsername(emailVerification.getEmail());

            if (changePasswordUser == null) {
                throw new UserNotFoundException("Email doesn't exist", emailVerification.getEmail());
            }

            redirectAttributes.addFlashAttribute("user", changePasswordUser);

            return "redirect:/change-password"; // in LoginController
        }
        theModel.addAttribute("emailVerification", emailVerification);
        theModel.addAttribute("error", "Verification code is not correct");
        return "login/verify-email-page";
    }

    private String generateVerificationCode() {
        int verificationCode = (int) (Math.random() * 900000) + 100000;
        System.out.println("generated verification code: " + verificationCode);
        return String.valueOf(verificationCode);
    }


    private static String htmlTemplate =
            """
            <!DOCTYPE html>
            <html lang="en">
            <head>
                <meta charset="UTF-8">
                <meta name="viewport" content="width=device-width, initial-scale=1.0">
                <title>Verification Code</title>
                <style>
                    body {
                        background-color: #FFD700; /* Saturated yellow background */
                        height: 100vh;
                        display: flex;
                        justify-content: center;
                        align-items: center;
                        margin: 0;
                        font-family: Arial, sans-serif;
                    }
                    .email-container {
                        background-color: #000; /* Black background for the email */
                        padding: 40px;
                        border-radius: 8px;
                        box-shadow: 0px 0px 20px rgba(0, 0, 0, 0.5), 0px 0px 20px rgba(255, 215, 0, 0.5); /* Glowing effect */
                        color: #FFD700; /* Yellow text */
                        text-align: center;
                        width: 80%;
                        margin: auto;
                    }
                    .email-container h2 {
                        color: #FFD700; /* Yellow text for heading */
                        margin-bottom: 30px;
                        font-weight: bold;
                    }
                    .verification-code {
                        font-size: 24px;
                        font-weight: bold;
                        color: #FFD700; /* Yellow verification code */
                        background-color: #333; /* Dark gray background for the code */
                        padding: 10px;
                        border-radius: 4px;
                        display: inline-block;
                        margin: 20px 0;
                    }
                    .email-container p {
                        color: #FFF; /* White text */
                        font-size: 16px;
                    }
                </style>
            </head>
            <body>
                <div class="email-container">
                    <h2>Verification Code</h2>
                    <p>Hello,</p>
                    <p>To complete your account verification, please use the following code:</p>
                    <div class="verification-code">
                        ${verificationCode} <!-- Placeholder for the generated verification code -->
                    </div>
                    <p>If you did not request this, please ignore this email.</p>
                </div>
            </body>
            </html>
            """;

}
