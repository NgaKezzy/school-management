package com.microservicesdemo.java.models;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Date;

/**
 * Nhân viên trong hệ thống quản lý trường học.
 * Kế thừa Person để có đầy đủ thông tin cá nhân, bổ sung thêm phòng ban và lương.
 */
public class Staff extends Person {
    // Jackson gọi constructor này và ánh xạ từng key JSON qua @JsonProperty.
    @JsonCreator
    public Staff(@JsonProperty("id") String id,
                 @JsonProperty("fullName") String fullName,
                 @JsonProperty("dateOfBirth") Date dateOfBirth,
                 @JsonProperty("gender") String gender,
                 @JsonProperty("phoneNumber") String phoneNumber,
                 @JsonProperty("email") String email,
                 @JsonProperty("address") String address,
                 @JsonProperty("department") String department,
                 @JsonProperty("salary") double salary) {
        super(id, fullName, dateOfBirth, gender, phoneNumber, email, address);
        this.department = department;
        this.salary = salary;
    }

    private String department;
    private double salary;

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public double getSalary() {
        return salary;
    }

    public void setSalary(double salary) {
        this.salary = salary;
    }
}
