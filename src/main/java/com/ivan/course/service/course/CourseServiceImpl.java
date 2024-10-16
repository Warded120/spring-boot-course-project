package com.ivan.course.service.course;

import com.ivan.course.entity.Course;
import com.ivan.course.repo.CourseRepository;
import jakarta.persistence.EntityManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CourseServiceImpl implements CourseService {

    private final CourseRepository courseRepository;
    private final EntityManager entityManager;

    @Autowired
    public CourseServiceImpl(CourseRepository courseRepository, EntityManager entityManager) {
        this.courseRepository = courseRepository;
        this.entityManager = entityManager;
    }

    @Override
    public Course save(Course course) {
        //return entityManager.merge(course);

        // to solve problem with detached studentData when enrolling a student
        return courseRepository.save(course);
    }

    @Override
    public List<Course> findAll() {
        return courseRepository.findAllBy();
    }

    @Override
    public Course findById(int id) {
        return courseRepository.findById(id).orElse(null);
    }
}
