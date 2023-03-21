package com.studentproject.response;

import lombok.Data;

import java.sql.Date;

@Data
public class StudentResponse {
    private Integer studentID;
    private String firstName;
    private String lastName;
    private Date dob;
    private Character gender;
}