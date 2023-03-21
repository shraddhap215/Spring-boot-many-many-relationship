package com.studentproject.controller;

import com.studentproject.entity.Student;
import com.studentproject.request.StudentRequest;
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
public class StudentController {
    @Autowired
    StudentService studentServices;

    @GetMapping("/students")
    public ResponseEntity<List<StudentResponse>> getData(){
        log.info("Fetch List of all students");
        return studentServices.getAllStudents();
    }
    @GetMapping("/students/{studentId}")
    public ResponseEntity<StudentResponse> getStudent(@PathVariable Integer studentId){
        log.info("Fetch record student with id"+studentId);
        return studentServices.getStudentById(studentId);
    }
    @PostMapping("/students")
    public String addData(@RequestBody StudentRequest student){
        log.info("add student to database");
        this.studentServices.save(student);
        return "Data is inserted successfully...";
    }

    @PostMapping ("/students/{studentId}/courses/{courseId}")
    public ResponseEntity<Student> assignCourse(@PathVariable Integer studentId, @PathVariable Integer courseId){
        log.info("Assign course to studentId:"+studentId);
        return this.studentServices.assignCourseToStudent(studentId,courseId);
    }
    @DeleteMapping("/students/{studentId}")
    public ResponseEntity<Integer> delete(@PathVariable Integer studentId){
        log.info("Delete student with Id:-"+studentId);
        return studentServices.deleteStudent(studentId);
    }

    @GetMapping("/students/{studentId}/courses")
    public ResponseEntity<List<CourseResponse>> getAllRegisteredCourse(@PathVariable Integer studentId){
        log.info("Fetch List of all courses student has registered");
        return studentServices.getAllRegisteredCourse(studentId);
    }

    @DeleteMapping("/students/{studentId}/course/{courseId}")
    public void deleteCourseOfTheStudent(@PathVariable Integer studentId,@PathVariable Integer courseId){
        log.info("Delete course of the student");
        this.studentServices.deleteCourseOfTheStudent(studentId,courseId);
    }
}