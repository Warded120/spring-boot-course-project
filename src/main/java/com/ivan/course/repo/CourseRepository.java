package com.ivan.course.repo;

import com.ivan.course.constants.CourseState;
import com.ivan.course.entity.Course;
import com.ivan.course.entity.StudentGroup;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

public interface CourseRepository extends JpaRepository<Course, Integer> {
    List<Course> findAllBy();
    List<Course> findAllByStateNot(CourseState state);

    @Query(value = """
                   SELECT c.*
                   FROM course c
                   JOIN student_group sg ON c.student_group_id = sg.id
                   JOIN groups_students gs ON sg.id = gs.group_id
                   GROUP BY c.id
                   HAVING COUNT(gs.student_id) < :minStudents
                   """, nativeQuery = true)
    List<Course> findCoursesWithStudentCountLessThan(@Param("minStudents") int minStudents);

    @Query(value = """
                   SELECT c.*
                   FROM course c
                   JOIN student_group sg ON c.student_group_id = sg.id
                   JOIN groups_students gs ON sg.id = gs.group_id
                   GROUP BY c.id
                   HAVING COUNT(gs.student_id) = :count
                   """, nativeQuery = true)
    List<Course> findCoursesWithStudentCountEquals(@Param("count")int count);
}

