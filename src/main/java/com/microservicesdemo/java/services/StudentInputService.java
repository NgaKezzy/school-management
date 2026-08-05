package com.microservicesdemo.java.services;

import com.microservicesdemo.java.models.Student;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

public final class StudentInputService {
    private StudentInputService() {
    }

    public static Student addStudentFromKeyboard(
            List<Student> studentList,
            Scanner scanner
    ) {
        String studentId = generateNextStudentId(studentList);
        System.out.println("ID sinh viên tự động: " + studentId);

        String fullName = readRequiredText(scanner, "Họ và tên: ");
        Date dateOfBirth = readDate(scanner, "Ngày sinh (dd-MM-yyyy): ");
        String gender = readRequiredText(scanner, "Giới tính: ");
        String phoneNumber = readRequiredText(scanner, "Số điện thoại: ");
        String email = readRequiredText(scanner, "Email: ");
        String address = readRequiredText(scanner, "Địa chỉ: ");
        String major = readRequiredText(scanner, "Chuyên ngành: ");
        String faculty = readRequiredText(scanner, "Khoa: ");

        int enrollmentYear = readInt(scanner, "Năm nhập học: ");
        double gpa = readDouble(scanner, "GPA: ");
        String academicStatus = readRequiredText(scanner, "Trạng thái học tập: ");

        Student student = new Student(
                studentId,
                fullName,
                dateOfBirth,
                gender,
                phoneNumber,
                email,
                address,
                major,
                faculty,
                enrollmentYear,
                gpa,
                academicStatus
        );

        studentList.add(student);

        System.out.println("Đã thêm sinh viên " + studentId);
        return student;
    }

    private static String generateNextStudentId(List<Student> studentList) {
        int maxId = studentList.stream()
                .map(Student::getId)
                .filter(id -> id != null && id.matches("STU\\d+"))
                .mapToInt(id -> Integer.parseInt(id.substring(3)))
                .max()
                .orElse(0);

        return String.format("STU%03d", maxId + 1);
    }

    private static String readRequiredText(Scanner scanner, String message) {
        while (true) {
            System.out.print(message);
            String value = scanner.nextLine().trim();
            if (!value.isEmpty()) {
                return value;
            }
            System.out.println("Thông tin không được để trống.");
        }
    }

    private static Date readDate(Scanner scanner, String message) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
        dateFormat.setLenient(false);

        while (true) {
            String value = readRequiredText(scanner, message);
            try {
                return dateFormat.parse(value);
            } catch (ParseException exception) {
                System.out.println("Ngày không hợp lệ, vui lòng nhập theo dd-MM-yyyy.");
            }
        }
    }

    private static int readInt(Scanner scanner, String message) {
        while (true) {
            String value = readRequiredText(scanner, message);
            try {
                return Integer.parseInt(value);
            } catch (NumberFormatException exception) {
                System.out.println("Vui lòng nhập một số nguyên hợp lệ.");
            }
        }
    }

    private static double readDouble(Scanner scanner, String message) {
        while (true) {
            String value = readRequiredText(scanner, message);
            try {
                return Double.parseDouble(value);
            } catch (NumberFormatException exception) {
                System.out.println("Vui lòng nhập một số hợp lệ.");
            }
        }
    }
}
