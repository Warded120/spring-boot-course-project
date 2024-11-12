package com.ivan.course.service.user;

import com.ivan.course.entity.user.User;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;

public interface UserService extends UserDetailsService {
    User save(User user);
    User findByUsername(String username);
    List<User> findAll();
}
