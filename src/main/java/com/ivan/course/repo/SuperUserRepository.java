package com.ivan.course.repo;

import com.ivan.course.entity.superuser.SuperUser;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SuperUserRepository extends JpaRepository<SuperUser, Integer> {
    SuperUser findByUsername(String username);
}
