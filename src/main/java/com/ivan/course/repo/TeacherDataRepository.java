package com.ivan.course.repo;

import com.ivan.course.entity.teacher.TeacherData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface TeacherDataRepository extends JpaRepository<TeacherData, Integer> {
    @Query(value = """
        SELECT t.*
        FROM teacher_data t
        JOIN course c ON t.id = c.teacher_data_id
        GROUP BY t.id
        HAVING COUNT(DISTINCT c.language) IN (1, 2, 3)
    """, nativeQuery = true)
    List<TeacherData> findOneTwoThreeLanguageTeachers();
}
