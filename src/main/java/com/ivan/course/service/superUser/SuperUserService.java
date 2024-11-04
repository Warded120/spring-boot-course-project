package com.ivan.course.service.superUser;

import com.ivan.course.entity.student.Student;
import com.ivan.course.entity.superuser.SuperUser;

public interface SuperUserService {
    SuperUser save(SuperUser superUser);
    SuperUser save(SuperUser superUser, boolean isEncodePassword);
    SuperUser save(SuperUser superUser, boolean isEncodePassword, boolean isSetSession);
    boolean existsByUsername(String username);
    SuperUser findById(int id);
    boolean existsByUserId(int id);
}
