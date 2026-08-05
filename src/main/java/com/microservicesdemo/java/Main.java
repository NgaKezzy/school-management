package com.microservicesdemo.java;//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or

import com.microservicesdemo.java.models.Student;

import java.util.Date;

public class Main {
    public static void main(String[] args) {
        Student student = new Student(
                "SV001",
                "Nguyễn Văn An",
                new Date(),
                "Nam",
                "0987654321",
                "an@gmail.com",
                "Hà Nội",
                "Công nghệ thông tin",
                "Khoa CNTT",
                "CNTT01",
                2024,
                8.5,
                "Đang học"
        );

        System.out.println(student.getId());
        System.out.println(student.getFullName());
        System.out.println(student.getMajor());
        System.out.println(student.getGpa());
    }
}