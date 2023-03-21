package com.studentproject.repo;

import com.studentproject.entity.Course;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CourseRepo extends JpaRepository<Course, Integer> {
    Course findByName(String name);
}