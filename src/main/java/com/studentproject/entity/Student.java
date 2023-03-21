package com.studentproject.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Data;

import java.sql.Date;
import java.util.*;

@Data
@Entity
@Table(name = "student")
public class Student {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer studentId;
    @Column(name = "first_name")
    private String firstName;
    @Column(name = "last_name")
    private String lastName;
    @Column(name = "DOB")
    private Date dob;
    @Column(name = "gender")
    private Character gender;

    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinTable(name = "student_course",
            joinColumns = {
                    @JoinColumn(name = "student_id", referencedColumnName = "studentId")
            },
            inverseJoinColumns = {
                    @JoinColumn(name = "course_id", referencedColumnName = "courseId")
            }
    )
    private List<Course> courseSet;
   /* @ManyToMany()
    @JoinTable(
            name = "student_course",
            joinColumns = @JoinColumn(name = "studentId"),
            inverseJoinColumns = @JoinColumn(name = "courseId")
    )
    //@JsonIgnore
    @JsonManagedReference

    private List<Course> courseSet;*/

}