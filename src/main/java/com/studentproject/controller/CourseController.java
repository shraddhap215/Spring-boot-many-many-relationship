package com.studentproject.controller;

import com.studentproject.entity.Course;
import com.studentproject.entity.StudentCourse;
import com.studentproject.request.CourseRequest;
import com.studentproject.response.CourseResponse;
import com.studentproject.response.StudentResponse;
import com.studentproject.service.StudentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Set;

@Slf4j
@RestController
public class CourseController {
    @Autowired
    StudentService studentServices;
    @PostMapping("/courses")
    public ResponseEntity<Course> addCourse(@RequestBody CourseRequest course){
        log.info("Add course in database");
        return studentServices.saveCourse(course);
    }
    @DeleteMapping("/courses/{courseId}")
    public void deleteCourse(@PathVariable Integer courseId){
        log.info("Delete course from database");
        this.studentServices.deleteCourse(courseId);
    }

    @GetMapping("/courses")
    public ResponseEntity<List<CourseResponse>> getAllCourses(){
        log.info("Fetch List of all courses");
        return studentServices.getAllDataOfCourse();
    }
    @GetMapping("/courses/{courseId}/students")
    public ResponseEntity<List<StudentResponse>> getAllRegisteredStudents(@PathVariable Integer courseId){
        log.info("Fetch List of all students registered to courseId:"+courseId);
        return studentServices.getAllRegisteredStudents(courseId);
    }
    @PutMapping("/students/{studentId}/course/{courseId}")
    public ResponseEntity<StudentCourse> changeCourseStudent(@PathVariable Integer studentId,@PathVariable Integer courseId,@RequestBody StudentCourse studentCourse){
        log.info("Change course of the student");
        return studentServices.changeCourseStudent(studentId,courseId,studentCourse);
    }

}