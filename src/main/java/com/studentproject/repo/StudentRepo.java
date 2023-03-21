package com.studentproject.repo;

import com.studentproject.entity.Student;
import com.studentproject.entity.StudentCourse;
import org.springframework.data.jpa.repository.JpaRepository;
import java.sql.Date;
import java.util.List;
import java.util.Set;

public interface StudentRepo extends JpaRepository <Student,Integer> {

    List<StudentCourse> findByStudentId(Integer studentId);
    Student findByFirstName(String firstName);

    Student findByFirstNameAndLastNameAndDobAndGender(String firstName, String lastName, Date dob,Character gender);
}