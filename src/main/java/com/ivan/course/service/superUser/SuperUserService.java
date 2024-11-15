package com.ivan.course.service.superUser;

import com.ivan.course.entity.superuser.SuperUser;

import java.util.List;

public interface SuperUserService {
    SuperUser save(SuperUser superUser);
    SuperUser save(SuperUser superUser, boolean isEncodePassword);
    SuperUser save(SuperUser superUser, boolean isEncodePassword, boolean isSetSession);
    void saveAll(List<SuperUser> superUserList);
    boolean existsByUsername(String username);
    SuperUser findById(int id);
    boolean existsByUserId(int id);
}
