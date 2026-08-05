package com.microservicesdemo.java.models;

public class Staff {
    public Staff(String id, String department, double salary) {
        setId(id);
        this.department = department;
        this.salary = salary;
    }

    private String id;
    private String department;
    private double salary;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        if (id == null || id.isBlank()) {
            throw new IllegalArgumentException("Staff id is required");
        }
        this.id = id;
    }

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
