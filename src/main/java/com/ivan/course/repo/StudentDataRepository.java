package com.ivan.course.repo;

import com.ivan.course.entity.student.StudentData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StudentDataRepository extends JpaRepository<StudentData, Integer> {

    @Query(value = """
        SELECT sd.*
        FROM student_data sd
        JOIN certificate c ON sd.id = c.student_id
        WHERE c.mark < 50
    """, nativeQuery = true)
    List<StudentData> findFailedStudents();

    @Query(value = """
        SELECT s.*
        FROM student_data s
        LEFT JOIN students_payments sp ON s.id = sp.student_data_id
        WHERE sp.student_data_id IS NULL
    """, nativeQuery = true)
    List<StudentData> findStudentsWithoutCoursePayments();

    @Query(value = """
            SELECT s.*
            FROM student_data s
            JOIN students_payments sp ON s.id = sp.student_data_id
            JOIN payment p ON sp.debt_id = p.id
            JOIN course c ON p.course_id = c.id
            WHERE p.payment <= (0.5 * c.price)
    """, nativeQuery = true)
    List<StudentData> findStudentsWithCoursePaymentsOf50Percent();

    @Query(value = """
        SELECT s.*
        FROM student_data s
        JOIN certificate c ON s.id = c.student_id
        JOIN course co ON c.course_id = co.id
        GROUP BY s.id
        HAVING COUNT(DISTINCT co.language_level) >= 3
    """, nativeQuery = true)
    List<StudentData> findStudentsWithThreeOrMoreCourseLevels();

    @Query(value = """
        SELECT sd.*
        FROM student_data sd
        JOIN groups_students gs ON sd.id = gs.student_id
        JOIN student_group sg ON gs.group_id = sg.id
        JOIN course c ON sg.id = c.student_group_id
        GROUP BY sd.id
        HAVING COUNT(DISTINCT CASE WHEN c.language = 'German' THEN 1 END) > 0
           OR COUNT(DISTINCT c.language) >= 2;
    """, nativeQuery = true)
    List<StudentData> findStudentsWhoLearnGermanOrTwoLanguages();
}
