package com.ivan.course.repo;

import com.ivan.course.entity.superuser.SuperUserData;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SuperUserDataRepository extends JpaRepository<SuperUserData, Integer> {
}
