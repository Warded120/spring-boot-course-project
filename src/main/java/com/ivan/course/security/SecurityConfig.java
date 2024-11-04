package com.ivan.course.security;

import com.ivan.course.service.user.UserService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

import javax.sql.DataSource;

@Configuration
public class SecurityConfig {

    //bcrypt bean definition
    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    //authenticationProvider bean definition
    @Bean
    public DaoAuthenticationProvider authenticationProvider(UserService userService) {
        DaoAuthenticationProvider auth = new DaoAuthenticationProvider();
        auth.setUserDetailsService(userService); //set the custom user details service
        auth.setPasswordEncoder(passwordEncoder()); //set the password encoder - bcrypt
        return auth;
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http, CustomAuthenticationSuccessHandler customAuthenticationSuccessHandler) throws Exception {
        http
                .authorizeHttpRequests(configurer -> configurer
                    .requestMatchers("/login", "/access-denied", "/verify", "/change-password/**").permitAll()
                    .requestMatchers("/", "/discover", "/about").permitAll()
                    .requestMatchers("/home").authenticated()
                    .requestMatchers("/register", "/register-student/**", "/register-teacher/**").permitAll()
                    .requestMatchers("/teacher/**", "/course/add/**", "/course/start/**", "/course/examination/**", "/course/exam-result/**").hasRole("ROLE_TEACHER")
                    .requestMatchers("/student/**", "/course/enroll/**", "/course/confirm/enroll/**").hasRole("ROLE_STUDENT")
                    .requestMatchers("/operator/profile/**").hasRole("ROLE_OPERATOR")
                    .requestMatchers("/operator/create-operator/**").hasRole("ROLE_ADMIN")
                    .anyRequest().authenticated()
                )

                .formLogin(form -> form
                        .loginPage("/login")
                        .loginProcessingUrl("/authenticateTheUser")
                        .successHandler(customAuthenticationSuccessHandler)
                        .permitAll()
                )

                .logout(logout -> logout.permitAll())

                .exceptionHandling(configurer -> configurer
                        .accessDeniedPage("/access-denied")
        );

        return http.build();
    }

    @Bean
    public UserDetailsManager userDetailsManager(DataSource dataSource) {
        return new JdbcUserDetailsManager(dataSource);
    }
}
