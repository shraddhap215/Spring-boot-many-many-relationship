package com.studentproject.request;

import lombok.Data;

import java.sql.Date;

@Data
public class StudentRequest {
    private Integer studentId;
    private String firstName;
    private String lastName;
    private Date dob;
    private Character gender;
}
