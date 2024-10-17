package com.ivan.course.repo;

import com.ivan.course.entity.teacher.TeacherData;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TeacherDataRepository extends JpaRepository<TeacherData, Integer> {
}
