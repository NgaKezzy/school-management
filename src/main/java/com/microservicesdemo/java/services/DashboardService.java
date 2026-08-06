package com.microservicesdemo.java.services;

import com.microservicesdemo.java.models.Lecturer;
import com.microservicesdemo.java.models.Staff;
import com.microservicesdemo.java.models.Student;

import java.util.List;
import java.util.Scanner;

/**
 * Điều khiển menu console chính và chuyển người dùng đến từng nhóm chức năng.
 */
public final class DashboardService {
    private DashboardService() {
    }

    public static void run(
            List<Student> studentList,
            List<Lecturer> lecturerList,
            List<Staff> staffList,
            Scanner scanner
    ) {
        // Vòng lặp chỉ kết thúc khi người dùng chọn 0 ở dashboard chính.
        boolean running = true;
        while (running) {
            printMainMenu();
            switch (readMenuChoice(scanner, "Chọn chức năng: ")) {
                case 1 -> showStudentMenu(studentList, scanner);
                case 2 -> showLecturers(lecturerList);
                case 3 -> showStaff(staffList);
                case 0 -> running = false;
                default -> System.out.println("Chức năng không hợp lệ.");
            }
        }
        System.out.println("Đã thoát chương trình.");
    }

    private static void printMainMenu() {
        System.out.println("\n========== SCHOOL DASHBOARD ==========");
        System.out.println("1. Quản lý sinh viên");
        System.out.println("2. Xem giáo viên");
        System.out.println("3. Xem nhân viên");
        System.out.println("0. Thoát");
    }

    private static void showStudentMenu(List<Student> studentList, Scanner scanner) {
        // Menu con có vòng lặp riêng để thao tác nhiều lần mà không quay về dashboard.
        boolean inStudentMenu = true;
        while (inStudentMenu) {
            System.out.println("\n---------- QUẢN LÝ SINH VIÊN ----------");
            System.out.println("1. Thêm sinh viên");
            System.out.println("2. Sửa sinh viên");
            System.out.println("3. Xoá sinh viên");
            System.out.println("4. Xem toàn bộ sinh viên");
            System.out.println("5. Tìm sinh viên theo tên");
            System.out.println("6. Thống kê xếp loại học lực");
            System.out.println("7. Quay lại");

            switch (readMenuChoice(scanner, "Chọn chức năng: ")) {
                case 1 -> StudentInputService.addStudentFromKeyboard(studentList, scanner);
                case 2 -> StudentInputService.updateStudentFromKeyboard(studentList, scanner);
                case 3 -> StudentInputService.deleteStudentFromKeyboard(studentList, scanner);
                case 4 -> StudentInputService.showAllStudents(studentList);
                case 5 -> StudentInputService.searchStudentsByName(studentList, scanner);
                case 6 -> {
                    StudentInputService.showAcademicPerformanceStatistics(studentList);
                    System.out.print("Nhấn Enter để quay lại menu...");
                    scanner.nextLine();
                }
                case 7 -> inStudentMenu = false;
                default -> System.out.println("Chức năng không hợp lệ.");
            }
        }
    }

    private static void showLecturers(List<Lecturer> lecturerList) {
        System.out.println("\n--------------- GIÁO VIÊN ---------------");
        for (int index = 0; index < lecturerList.size(); index++) {
            Lecturer lecturer = lecturerList.get(index);
            System.out.printf(
                    "%d. %s | ID: %s | Mã GV: %s | Khoa: %s | Staff ID: %s%n",
                    index + 1,
                    lecturer.getFullName(),
                    lecturer.getId(),
                    lecturer.getLecturerCode(),
                    lecturer.getFaculty(),
                    lecturer.getStaffId()
            );
        }
    }

    private static void showStaff(List<Staff> staffList) {
        System.out.println("\n--------------- NHÂN VIÊN ---------------");
        for (int index = 0; index < staffList.size(); index++) {
            Staff staff = staffList.get(index);
            System.out.printf(
                    "%d. ID: %s | Phòng ban: %s | Lương: %,.0f%n",
                    index + 1,
                    staff.getId(),
                    staff.getDepartment(),
                    staff.getSalary()
            );
        }
    }

    private static int readMenuChoice(Scanner scanner, String message) {
        // Đọc dưới dạng String rồi tự chuyển kiểu để tránh lỗi nextInt() bỏ sót ký tự xuống dòng.
        while (true) {
            System.out.print(message);
            String value = scanner.nextLine().trim();
            try {
                return Integer.parseInt(value);
            } catch (NumberFormatException exception) {
                System.out.println("Vui lòng nhập một số nguyên.");
            }
        }
    }
}
