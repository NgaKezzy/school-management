package com.microservicesdemo.java.services;

import com.microservicesdemo.java.models.Student;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.text.Normalizer;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Scanner;

public final class StudentInputService {
    private StudentInputService() {
    }

    public static Student addStudentFromKeyboard(
            List<Student> studentList,
            Scanner scanner
    ) {
        try {
            return addStudent(studentList, scanner);
        } catch (InputCancelledException exception) {
            System.out.println("Đã huỷ thêm sinh viên.");
            return null;
        }
    }

    private static Student addStudent(List<Student> studentList, Scanner scanner) {
        System.out.println("Nhập 0 tại bất kỳ bước nào để huỷ.");
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

    public static Student updateStudentFromKeyboard(List<Student> studentList, Scanner scanner) {
        try {
            return updateStudent(studentList, scanner);
        } catch (InputCancelledException exception) {
            System.out.println("Đã huỷ sửa sinh viên. Dữ liệu cũ được giữ nguyên.");
            return null;
        }
    }

    private static Student updateStudent(List<Student> studentList, Scanner scanner) {
        System.out.println("Nhập 0 tại bất kỳ bước nào để huỷ.");
        Student student = selectStudent(studentList, scanner, "Chọn sinh viên cần sửa");
        if (student == null) {
            return null;
        }

        System.out.println("Nhập lại thông tin cho " + student.getId() + ":");
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

        student.setFullName(fullName);
        student.setDateOfBirth(dateOfBirth);
        student.setGender(gender);
        student.setPhoneNumber(phoneNumber);
        student.setEmail(email);
        student.setAddress(address);
        student.setMajor(major);
        student.setFaculty(faculty);
        student.setEnrollmentYear(enrollmentYear);
        student.setGpa(gpa);
        student.setAcademicStatus(academicStatus);

        System.out.println("Đã cập nhật sinh viên " + student.getId());
        return student;
    }

    public static Student deleteStudentFromKeyboard(List<Student> studentList, Scanner scanner) {
        try {
            System.out.println("Nhập 0 để huỷ xoá.");
            Student student = selectStudent(studentList, scanner, "Chọn sinh viên cần xoá");
            if (student == null) {
                return null;
            }
            studentList.remove(student);
            System.out.println("Đã xoá sinh viên " + student.getId());
            return student;
        } catch (InputCancelledException exception) {
            System.out.println("Đã huỷ xoá sinh viên.");
            return null;
        }
    }

    public static void showAllStudents(List<Student> studentList) {
        if (studentList.isEmpty()) {
            System.out.println("Danh sách sinh viên đang trống.");
            return;
        }

        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
        int nameWidth = Math.max(
                "HỌ VÀ TÊN".length(),
                studentList.stream().mapToInt(student -> student.getFullName().length()).max().orElse(0)
        );
        int facultyWidth = Math.max(
                "KHOA".length(),
                studentList.stream().mapToInt(student -> student.getFaculty().length()).max().orElse(0)
        );
        int statusWidth = Math.max(
                "TRẠNG THÁI".length(),
                studentList.stream().mapToInt(student -> student.getAcademicStatus().length()).max().orElse(0)
        );

        String rowFormat = "%-4s | %-8s | %-" + nameWidth + "s | %-10s | %-"
                + facultyWidth + "s | %6s | %-" + statusWidth + "s%n";
        int tableWidth = 4 + 3 + 8 + 3 + nameWidth + 3 + 10 + 3
                + facultyWidth + 3 + 6 + 3 + statusWidth;
        String separator = "-".repeat(tableWidth);

        System.out.println("\nDANH SÁCH SINH VIÊN");
        System.out.println(separator);
        System.out.printf(
                rowFormat,
                "STT", "ID", "HỌ VÀ TÊN", "NGÀY SINH", "KHOA", "GPA", "TRẠNG THÁI"
        );
        System.out.println(separator);
        for (int index = 0; index < studentList.size(); index++) {
            Student student = studentList.get(index);
            System.out.printf(
                    rowFormat,
                    index + 1,
                    student.getId(),
                    student.getFullName(),
                    dateFormat.format(student.getDateOfBirth()),
                    student.getFaculty(),
                    String.format("%.2f", student.getGpa()),
                    student.getAcademicStatus()
            );
        }
        System.out.println(separator);
    }

    public static List<Student> searchStudentsByName(List<Student> studentList, Scanner scanner) {
        try {
            System.out.println("Nhập 0 để huỷ tìm kiếm.");
            String keyword = readRequiredText(scanner, "Nhập tên cần tìm: ");
            String normalizedKeyword = normalizeText(keyword);

            List<Student> result = studentList.stream()
                    .filter(student -> normalizeText(student.getFullName()).contains(normalizedKeyword))
                    .toList();

            if (result.isEmpty()) {
                System.out.println("Không tìm thấy sinh viên có tên chứa: " + keyword);
            } else {
                System.out.println("Tìm thấy " + result.size() + " sinh viên:");
                showAllStudents(result);
            }
            return result;
        } catch (InputCancelledException exception) {
            System.out.println("Đã huỷ tìm kiếm sinh viên.");
            return List.of();
        }
    }

    private static String normalizeText(String value) {
        return Normalizer.normalize(value, Normalizer.Form.NFD)
                .replaceAll("\\p{M}+", "")
                .replace('đ', 'd')
                .replace('Đ', 'D')
                .toLowerCase(Locale.ROOT);
    }

    private static Student selectStudent(List<Student> studentList, Scanner scanner, String message) {
        if (studentList.isEmpty()) {
            System.out.println("Danh sách sinh viên đang trống.");
            return null;
        }

        showAllStudents(studentList);
        while (true) {
            int selectedIndex = readInt(scanner, message + " (1-" + studentList.size() + "): ");
            if (selectedIndex >= 1 && selectedIndex <= studentList.size()) {
                return studentList.get(selectedIndex - 1);
            }
            System.out.println("Số thứ tự sinh viên không hợp lệ.");
        }
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
            if (value.equals("0")) {
                throw new InputCancelledException();
            }
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

    private static final class InputCancelledException extends RuntimeException {
    }
}
