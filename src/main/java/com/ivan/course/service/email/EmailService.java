package com.ivan.course.service.email;

import jakarta.mail.MessagingException;

public interface EmailService {
    public void sendHtmlEmail(String toEmail, String subject, String htmlBody) throws MessagingException;
}
