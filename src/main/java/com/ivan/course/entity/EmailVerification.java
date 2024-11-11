package com.ivan.course.entity;

import lombok.*;

@NoArgsConstructor
@Getter
@Setter
@ToString
public class EmailVerification {
    private String email;
    private String verificatoinCode;
    private String actualVerificatoinCode;

    public EmailVerification(String email, String verificatoinCode, String actualVerificatoinCode) {
        this.email = email;
        this.verificatoinCode = verificatoinCode;
        this.actualVerificatoinCode = actualVerificatoinCode;
    }
}
