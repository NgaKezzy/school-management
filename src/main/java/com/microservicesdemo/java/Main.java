package com.microservicesdemo.java;

import com.microservicesdemo.java.data.MockDataLoader;
import com.microservicesdemo.java.models.Lecturer;
import com.microservicesdemo.java.models.Staff;
import com.microservicesdemo.java.models.Student;
import com.microservicesdemo.java.services.DashboardService;

import java.io.IOException;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws IOException {
        List<Student> studentList = MockDataLoader.getStudents();
        List<Lecturer> lecturerList = MockDataLoader.getLecturers();
        List<Staff> staffList = MockDataLoader.getStaff();

        Scanner scanner = new Scanner(System.in);
        DashboardService.run(
                studentList,
                lecturerList,
                staffList,
                scanner
        );
    }
}
