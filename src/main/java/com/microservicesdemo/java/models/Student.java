package com.microservicesdemo.java.models;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Date;

public class Student extends Person{
    // Jackson gọi constructor này và ánh xạ từng key JSON qua @JsonProperty.
    @JsonCreator
    public Student(@JsonProperty("id") String id,
                   @JsonProperty("fullName") String fullName,
                   @JsonProperty("dateOfBirth") Date dateOfBirth,
                   @JsonProperty("gender") String gender,
                   @JsonProperty("phoneNumber") String phoneNumber,
                   @JsonProperty("email") String email,
                   @JsonProperty("address") String address,
                   @JsonProperty("major") String major,
                   @JsonProperty("faculty") String faculty,
                   @JsonProperty("enrollmentYear") int enrollmentYear,
                   @JsonProperty("gpa") double gpa,
                   @JsonProperty("academicStatus") String academicStatus) {
        super(id, fullName, dateOfBirth, gender, phoneNumber, email, address);
        this.major = major;
        this.faculty = faculty;
        this.enrollmentYear = enrollmentYear;
        this.gpa = gpa;
        this.academicStatus = academicStatus;
    }

    private String major;
    private String faculty;
    private int enrollmentYear;
    private double gpa;
    private String academicStatus;

    public String getMajor() {
        return major;
    }

    public void setMajor(String major) {
        this.major = major;
    }

    public String getFaculty() {
        return faculty;
    }

    public void setFaculty(String faculty) {
        this.faculty = faculty;
    }

    public int getEnrollmentYear() {
        return enrollmentYear;
    }

    public void setEnrollmentYear(int enrollmentYear) {
        this.enrollmentYear = enrollmentYear;
    }

    public double getGpa() {
        return gpa;
    }

    public void setGpa(double gpa) {
        this.gpa = gpa;
    }

    public String getAcademicStatus() {
        return academicStatus;
    }

    public void setAcademicStatus(String academicStatus) {
        this.academicStatus = academicStatus;
    }
}
