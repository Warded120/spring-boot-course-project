package com.ivan.course;

import com.ivan.course.repo.SuperUserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class SpringBootCourseProjectApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringBootCourseProjectApplication.class, args);
    }

    @Bean
    CommandLineRunner commandLineRunner(SuperUserRepository superUserRepository/* other repo's */) {
        return args -> {
            // TODO: maybe populate database like this
        };
    }
}

