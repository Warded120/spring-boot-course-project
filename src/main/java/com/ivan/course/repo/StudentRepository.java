package com.ivan.course.repo;

import com.ivan.course.entity.student.Student;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StudentRepository extends JpaRepository<Student, Integer> {
    Student findByUsername(String username);
}
