package com.microservicesdemo.java.models;

import java.util.Date;

public class Student extends Person{
    public Student(String id, String fullName, Date dateOfBirth, String gender, String phoneNumber, String email,
                   String address, String major, String faculty, String className, int enrollmentYear, double gpa,
                   String academicStatus) {
        super(id, fullName, dateOfBirth, gender, phoneNumber, email, address);
        this.major = major;
        this.faculty = faculty;
        this.className = className;
        this.enrollmentYear = enrollmentYear;
        this.gpa = gpa;
        this.academicStatus = academicStatus;
    }

    private String major;
    private String faculty;
    private String className;
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

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
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
