package com.studentproject.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.Data;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Data
@Entity
@Table(name = "course")
public class Course {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    Integer courseId;
    @Column(name = "name")
    String name;

    @ManyToMany(mappedBy = "courseSet",fetch = FetchType.LAZY)
    private List<Student> studentSet;
    // @JsonBackReference


}