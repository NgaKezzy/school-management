package com.microservicesdemo.java;

import com.microservicesdemo.java.data.MockDataLoader;
import com.microservicesdemo.java.models.Student;
import com.microservicesdemo.java.services.StudentInputService;

import java.io.IOException;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws IOException {
        List<Student> studentList = MockDataLoader.getStudents();

        Scanner scanner = new Scanner(System.in);
        Student newStudent = StudentInputService.addStudentFromKeyboard(
                studentList,
                scanner
        );

        System.out.println("Sinh viên vừa thêm: "
                + newStudent.getId() + " - " + newStudent.getFullName());
        System.out.println("Tổng số sinh viên: " + studentList.size());
    }
}
