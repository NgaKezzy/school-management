package com.microservicesdemo.java.models;

import java.util.Date;

public class Lecturer extends Person {
    public Lecturer(String id, String fullName, Date dateOfBirth, String gender, String phoneNumber, String email,
                    String address, String lecturerCode, String faculty, String department, String academicDegree,
                    String academicTitle, String specialization, double salary, int yearsOfExperience) {
        super(id, fullName, dateOfBirth, gender, phoneNumber, email, address);
        this.lecturerCode = lecturerCode;
        this.faculty = faculty;
        this.department = department;
        this.academicDegree = academicDegree;
        this.academicTitle = academicTitle;
        this.specialization = specialization;
        this.salary = salary;
        this.yearsOfExperience = yearsOfExperience;
    }

    private String lecturerCode;
    private String faculty;
    private String department;
    private String academicDegree;
    private String academicTitle;
    private String specialization;
    private double salary;
    private int yearsOfExperience;

    public String getLecturerCode() {
        return lecturerCode;
    }

    public void setLecturerCode(String lecturerCode) {
        this.lecturerCode = lecturerCode;
    }

    public String getFaculty() {
        return faculty;
    }

    public void setFaculty(String faculty) {
        this.faculty = faculty;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public String getAcademicDegree() {
        return academicDegree;
    }

    public void setAcademicDegree(String academicDegree) {
        this.academicDegree = academicDegree;
    }

    public String getAcademicTitle() {
        return academicTitle;
    }

    public void setAcademicTitle(String academicTitle) {
        this.academicTitle = academicTitle;
    }

    public String getSpecialization() {
        return specialization;
    }

    public void setSpecialization(String specialization) {
        this.specialization = specialization;
    }

    public double getSalary() {
        return salary;
    }

    public void setSalary(double salary) {
        this.salary = salary;
    }

    public int getYearsOfExperience() {
        return yearsOfExperience;
    }

    public void setYearsOfExperience(int yearsOfExperience) {
        this.yearsOfExperience = yearsOfExperience;
    }
}
