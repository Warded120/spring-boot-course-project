package com.ivan.course.repo;

import com.ivan.course.entity.StudentGroup;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StudentGroupRepository extends JpaRepository<StudentGroup, Integer> {
}
