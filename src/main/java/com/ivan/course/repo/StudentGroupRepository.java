package com.ivan.course.repo;

import com.ivan.course.entity.StudentGroup;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface StudentGroupRepository extends JpaRepository<StudentGroup, Integer> {
    @Query(value = """ 
        SELECT sg.* FROM student_group sg 
        JOIN course c ON sg.id = c.student_group_id 
        WHERE c.language = :language AND c.teacher_data_id = :teacherId 
        """, nativeQuery = true)
    List<StudentGroup> findGroupsByLanguageAndTeacher(@Param("language") String language,
                                                      @Param("teacherId") int teacherId);
}
