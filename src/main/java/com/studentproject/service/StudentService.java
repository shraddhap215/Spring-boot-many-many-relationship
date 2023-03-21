package com.studentproject.service;

import com.studentproject.entity.Course;
import com.studentproject.entity.Student;
import com.studentproject.entity.StudentCourse;
import com.studentproject.repo.CourseRepo;
import com.studentproject.repo.StudentCourseRepo;
import com.studentproject.repo.StudentRepo;
import com.studentproject.request.CourseRequest;
import com.studentproject.request.StudentRequest;
import com.studentproject.response.CourseResponse;
import com.studentproject.response.StudentResponse;
import com.studentproject.util.DataNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.DuplicateFormatFlagsException;
import java.util.List;
import java.util.Optional;
@ResponseBody
@Service
public class StudentService {
    @Autowired
    StudentRepo studentRepo;
    @Autowired
    CourseRepo courseRepo;
    @Autowired
    StudentCourseRepo studentCourseRepo;


    //Return list of all students
    public ResponseEntity<List<StudentResponse>> getAllStudents() {
        List<StudentResponse> studentResponseList=new ArrayList<>();
        List<Student> studentList=studentRepo.findAll();
        if(CollectionUtils.isEmpty(studentList)){
            throw new DataNotFoundException("Student List is Empty");
        }else{
            for(Student student:studentList) {
                StudentResponse studentResponse = new StudentResponse();
                studentResponse.setStudentID(student.getStudentId());
                studentResponse.setFirstName(student.getFirstName());
                studentResponse.setLastName(student.getLastName());
                studentResponse.setDob(student.getDob());
                studentResponse.setGender(student.getGender());
                studentResponseList.add(studentResponse);
            }
            return new ResponseEntity<>(studentResponseList,HttpStatus.OK);
        }
    }

    //Return details of specific student
    public ResponseEntity<StudentResponse> getStudentById(Integer studentId) {
        Optional<Student> student=studentRepo.findById(studentId);
        StudentResponse studentResponse= new StudentResponse();
        if(student.isPresent()){
            studentResponse.setStudentID(student.get().getStudentId());
            studentResponse.setFirstName(student.get().getFirstName());
            studentResponse.setLastName(student.get().getLastName());
            studentResponse.setDob(student.get().getDob());
            studentResponse.setGender(student.get().getGender());
            return new ResponseEntity<>(studentResponse,HttpStatus.OK);
        }else{
            return new ResponseEntity<>(studentResponse,HttpStatus.UNAUTHORIZED);
        }
    }

    //Add new student
    public ResponseEntity<Student> save(StudentRequest student) {
        if(studentRepo.findByFirstNameAndLastNameAndDobAndGender(student.getFirstName(),student.getLastName(),student.getDob(),student.getGender())==null) {
            Student student1 = new Student();
            student1.setFirstName(student.getFirstName());
            student1.setLastName(student.getLastName());
            student1.setDob(student.getDob());
            student1.setGender(student.getGender());
            return new ResponseEntity<>(studentRepo.save(student1), HttpStatus.CREATED);
        }else{
            throw new DuplicateFormatFlagsException("Duplicate Data");
        }
    }

    //Delete Student
    public ResponseEntity<Integer> deleteStudent(Integer studentId) {
        Optional<Student> student=studentRepo.findById(studentId);
        studentRepo.delete(student.get());
        return new ResponseEntity<>(studentId,HttpStatus.OK);
    }

    //Delete Course
    public ResponseEntity<Integer> deleteCourse(Integer courseId){
        Optional<Course> courseOptional=courseRepo.findById(courseId);
        if (studentCourseRepo.findByCourseId(courseId) != null) {
            Optional<StudentCourse> studentCourseOptional = Optional.ofNullable(studentCourseRepo.findByCourseId(courseId));
            studentCourseRepo.delete(studentCourseOptional.get());
        }
        courseRepo.delete(courseOptional.get());
        return new ResponseEntity<>(courseId,HttpStatus.OK);
    }
    //Enroll student in a course
    public ResponseEntity<Student> assignCourseToStudent(Integer studentId, Integer courseId) {
        List<Course> courseSet;
        if(studentCourseRepo.findByStudentIdAndCourseId(studentId,courseId)==null) {
            Student student = studentRepo.findById(studentId).get();
            Course course = courseRepo.findById(courseId).get();
            courseSet = student.getCourseSet();
            courseSet.add(course);
            student.setCourseSet(courseSet);
            return new ResponseEntity<>(studentRepo.save(student), HttpStatus.CREATED);
        }else{
            throw new DuplicateFormatFlagsException("Already assigned");
        }
    }
    //add new course
    public ResponseEntity<Course> saveCourse(CourseRequest course) {
        if(courseRepo.findByName(course.getName())==null) {
            Course course1 = new Course();
            course1.setName(course.getName());
            return new ResponseEntity<>(courseRepo.save(course1), HttpStatus.CREATED);
        }else{
            throw new DuplicateFormatFlagsException("Course already created");
        }
    }

    //Get list of all courses
    public ResponseEntity<List<CourseResponse>> getAllDataOfCourse() {
        List<CourseResponse> courseResponseList=new ArrayList<>();
        List<Course> courseList=courseRepo.findAll();
        if(CollectionUtils.isEmpty(courseList)){
            throw  new DataNotFoundException("Course List is Empty");
        }else{
            for(Course course:courseList) {
                CourseResponse courseResponse = new CourseResponse();
                courseResponse.setCourseId(course.getCourseId());
                courseResponse.setName(course.getName());
                courseResponseList.add(courseResponse);
            }
            return new ResponseEntity<>(courseResponseList,HttpStatus.OK);
        }
    }

    //get list of courses student has registered
    public ResponseEntity<List<CourseResponse>> getAllRegisteredCourse(Integer studentId) {
        Student student=studentRepo.findById(studentId).get();
        List<CourseResponse> courseResponseList=new ArrayList<>();
        List<Course> courseList=student.getCourseSet();
        if(CollectionUtils.isEmpty(courseList)){
            throw new DataNotFoundException("not yet Registered to any course");
        }else{
            for(Course course:courseList) {
                CourseResponse courseResponse = new CourseResponse();
                courseResponse.setCourseId(course.getCourseId());
                courseResponse.setName(course.getName());
                courseResponseList.add(courseResponse);
            }
            return new ResponseEntity<>(courseResponseList,HttpStatus.OK);
        }
    }
    public ResponseEntity<List<StudentResponse>> getAllRegisteredStudents(Integer courseId){
        Course course=courseRepo.findById(courseId).get();
        List<StudentResponse> studentResponseList=new ArrayList<>();
        List<Student> studentList=course.getStudentSet();
        if(CollectionUtils.isEmpty(studentList)){
            throw new DataNotFoundException("doesn't have students");
        }else {
            for(Student student:studentList){
                StudentResponse studentResponse=new StudentResponse();
                studentResponse.setStudentID(student.getStudentId());
                studentResponse.setFirstName(student.getFirstName());
                studentResponse.setLastName(student.getLastName());
                studentResponse.setDob(student.getDob());
                studentResponse.setGender(student.getGender());
                studentResponseList.add(studentResponse);
            }
            return new ResponseEntity<>(studentResponseList,HttpStatus.OK);
        }
    }

    public ResponseEntity<StudentCourse> changeCourseStudent(Integer studentId,Integer courseId,StudentCourse studentCourse){
        Optional<Student> studentOptional=studentRepo.findById((studentCourse.getStudentId()));
        Optional<Course> courseOptional=courseRepo.findById(studentCourse.getCourseId());
        if(studentOptional.isPresent()&&courseOptional.isPresent()){
            Optional<StudentCourse> studentCourseOptional= Optional.ofNullable(studentCourseRepo.findByStudentIdAndCourseId(studentId, courseId));
            if(studentCourseOptional.isPresent()){
                if(courseId==studentCourse.getCourseId()){
                    throw new DuplicateFormatFlagsException("Same course is present");
                }else{
                    StudentCourse studentCourse1 = studentCourseOptional.get();
                    studentCourse1.setCourseId(studentCourse.getCourseId());
                    return new ResponseEntity<>(studentCourseRepo.save(studentCourse1), HttpStatus.CREATED);
                }
            }else {
                throw new DataNotFoundException("Enroll course is not found");
            }
        }else {
            throw new DataNotFoundException("Student id or course id doesn't exist");
        }
    }
    public ResponseEntity<Integer> deleteCourseOfTheStudent(Integer studentId,Integer courseId) {
        StudentCourse studentCourse = studentCourseRepo.findByStudentIdAndCourseId(studentId, courseId);
        studentCourseRepo.delete(studentCourse);
        return new ResponseEntity<>(studentId,HttpStatus.OK);
    }

}