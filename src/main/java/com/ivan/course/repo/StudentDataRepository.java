package com.ivan.course.repo;

import com.ivan.course.entity.student.StudentData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StudentDataRepository extends JpaRepository<StudentData, Integer> {
}
