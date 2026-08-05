package com.microservicesdemo.java.models;

import java.util.Date;

public class Student extends Person{
    public Student(String id, String fullName, Date dateOfBirth, String gender, String phoneNumber, String email,
                   String address, String major, String faculty, String classRoomId, int enrollmentYear, double gpa,
                   String academicStatus) {
        super(id, fullName, dateOfBirth, gender, phoneNumber, email, address);
        this.major = major;
        this.faculty = faculty;
        setClassRoomId(classRoomId);
        this.enrollmentYear = enrollmentYear;
        this.gpa = gpa;
        this.academicStatus = academicStatus;
    }

    private String major;
    private String faculty;
    private String classRoomId;
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

    public String getClassRoomId() {
        return classRoomId;
    }

    public void setClassRoomId(String classRoomId) {
        if (classRoomId == null || classRoomId.isBlank()) {
            throw new IllegalArgumentException("Student must belong to a class room");
        }
        this.classRoomId = classRoomId;
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
