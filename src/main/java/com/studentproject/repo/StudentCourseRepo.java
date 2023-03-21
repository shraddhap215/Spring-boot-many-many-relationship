package com.studentproject.repo;



import com.studentproject.entity.StudentCourse;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;


public interface StudentCourseRepo extends JpaRepository<StudentCourse, Integer> {

    StudentCourse findByStudentIdAndCourseId(Integer studentId, Integer courseId);

    StudentCourse findByCourseId(Integer courseId);
}