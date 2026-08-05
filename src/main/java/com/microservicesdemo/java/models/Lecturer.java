package com.microservicesdemo.java.models;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Date;
import java.util.ArrayList;
import java.util.List;

public class Lecturer extends Person {
    @JsonCreator
    public Lecturer(@JsonProperty("id") String id,
                    @JsonProperty("fullName") String fullName,
                    @JsonProperty("dateOfBirth") Date dateOfBirth,
                    @JsonProperty("gender") String gender,
                    @JsonProperty("phoneNumber") String phoneNumber,
                    @JsonProperty("email") String email,
                    @JsonProperty("address") String address,
                    @JsonProperty("lecturerCode") String lecturerCode,
                    @JsonProperty("faculty") String faculty,
                    @JsonProperty("department") String department,
                    @JsonProperty("academicDegree") String academicDegree,
                    @JsonProperty("academicTitle") String academicTitle,
                    @JsonProperty("specialization") String specialization,
                    @JsonProperty("salary") double salary,
                    @JsonProperty("yearsOfExperience") int yearsOfExperience,
                    @JsonProperty("staffId") String staffId,
                    @JsonProperty("classRoomIds") List<String> classRoomIds) {
        super(id, fullName, dateOfBirth, gender, phoneNumber, email, address);
        this.lecturerCode = lecturerCode;
        this.faculty = faculty;
        this.department = department;
        this.academicDegree = academicDegree;
        this.academicTitle = academicTitle;
        this.specialization = specialization;
        this.salary = salary;
        this.yearsOfExperience = yearsOfExperience;
        setStaffId(staffId);
        setClassRoomIds(classRoomIds);
    }

    private String lecturerCode;
    private String faculty;
    private String department;
    private String academicDegree;
    private String academicTitle;
    private String specialization;
    private double salary;
    private int yearsOfExperience;
    private String staffId;
    private List<String> classRoomIds;

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

    public String getStaffId() {
        return staffId;
    }

    public void setStaffId(String staffId) {
        if (staffId == null || staffId.isBlank()) {
            throw new IllegalArgumentException("Lecturer must reference a staff record");
        }
        this.staffId = staffId;
    }

    public List<String> getClassRoomIds() {
        return classRoomIds;
    }

    public void setClassRoomIds(List<String> classRoomIds) {
        this.classRoomIds = classRoomIds == null ? new ArrayList<>() : new ArrayList<>(classRoomIds);
    }

    public void addClassRoomId(String classRoomId) {
        if (classRoomId == null || classRoomId.isBlank()) {
            throw new IllegalArgumentException("Class room id is required");
        }
        if (!classRoomIds.contains(classRoomId)) {
            classRoomIds.add(classRoomId);
        }
    }
}
