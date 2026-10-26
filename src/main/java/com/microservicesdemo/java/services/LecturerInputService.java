package com.microservicesdemo.java.services;

import com.microservicesdemo.java.models.Lecturer;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.text.Normalizer;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Scanner;

/**
 * Chứa các thao tác thêm, sửa, xoá, hiển thị và tìm kiếm giáo viên trên console.
 * Các thay đổi hiện chỉ tác động đến List trong bộ nhớ, chưa ghi ngược vào file JSON.
 */
public final class LecturerInputService {
    private LecturerInputService() {
    }

    // ========== THÊM GIÁO VIÊN ==========

    public static Lecturer addLecturerFromKeyboard(
            List<Lecturer> lecturerList,
            Scanner scanner
    ) {
        try {
            return addLecturer(lecturerList, scanner);
        } catch (InputCancelledException exception) {
            System.out.println("Đã huỷ thêm giáo viên.");
            return null;
        }
    }

    private static Lecturer addLecturer(List<Lecturer> lecturerList, Scanner scanner) {
        System.out.println("Nhập 0 tại bất kỳ bước nào để huỷ.");
        String id = generateNextLecturerId(lecturerList);
        System.out.println("ID giáo viên tự động: " + id);

        String fullName = readRequiredText(scanner, "Họ và tên: ");
        Date dateOfBirth = readDate(scanner, "Ngày sinh (dd-MM-yyyy hoặc dd/MM/yyyy): ");
        String gender = readRequiredText(scanner, "Giới tính: ");
        String phoneNumber = readRequiredText(scanner, "Số điện thoại: ");
        String email = readRequiredText(scanner, "Email: ");
        String address = readRequiredText(scanner, "Địa chỉ: ");
        String lecturerCode = readRequiredText(scanner, "Mã GV (ví dụ GV006): ");
        String faculty = readRequiredText(scanner, "Khoa: ");
        String department = readRequiredText(scanner, "Bộ môn: ");
        String academicDegree = readRequiredText(scanner, "Học vị (Tiến sĩ/Thạc sĩ...): ");
        String academicTitle = readRequiredText(scanner, "Học hàm: ");
        String specialization = readRequiredText(scanner, "Chuyên ngành: ");
        double salary = readDouble(scanner, "Lương: ");
        int yearsOfExperience = readInt(scanner, "Số năm kinh nghiệm: ");

        Lecturer lecturer = new Lecturer(
                id,
                fullName,
                dateOfBirth,
                gender,
                phoneNumber,
                email,
                address,
                lecturerCode,
                faculty,
                department,
                academicDegree,
                academicTitle,
                specialization,
                salary,
                yearsOfExperience
        );

        lecturerList.add(lecturer);
        System.out.println("Đã thêm giáo viên " + id);
        return lecturer;
    }

    // ========== SỬA GIÁO VIÊN ==========

    public static Lecturer updateLecturerFromKeyboard(List<Lecturer> lecturerList, Scanner scanner) {
        try {
            return updateLecturer(lecturerList, scanner);
        } catch (InputCancelledException exception) {
            System.out.println("Đã huỷ sửa giáo viên. Dữ liệu cũ được giữ nguyên.");
            return null;
        }
    }

    private static Lecturer updateLecturer(List<Lecturer> lecturerList, Scanner scanner) {
        System.out.println("Nhập 0 tại bất kỳ bước nào để huỷ.");
        Lecturer lecturer = selectLecturer(lecturerList, scanner, "Chọn giáo viên cần sửa");
        if (lecturer == null) {
            return null;
        }

        System.out.println("Nhập lại thông tin cho " + lecturer.getId() + ":");

        String fullName = readRequiredText(scanner, "Họ và tên: ");
        Date dateOfBirth = readDate(scanner, "Ngày sinh (dd-MM-yyyy hoặc dd/MM/yyyy): ");
        String gender = readRequiredText(scanner, "Giới tính: ");
        String phoneNumber = readRequiredText(scanner, "Số điện thoại: ");
        String email = readRequiredText(scanner, "Email: ");
        String address = readRequiredText(scanner, "Địa chỉ: ");
        String lecturerCode = readRequiredText(scanner, "Mã GV: ");
        String faculty = readRequiredText(scanner, "Khoa: ");
        String department = readRequiredText(scanner, "Bộ môn: ");
        String academicDegree = readRequiredText(scanner, "Học vị: ");
        String academicTitle = readRequiredText(scanner, "Học hàm: ");
        String specialization = readRequiredText(scanner, "Chuyên ngành: ");
        double salary = readDouble(scanner, "Lương: ");
        int yearsOfExperience = readInt(scanner, "Số năm kinh nghiệm: ");

        lecturer.setFullName(fullName);
        lecturer.setDateOfBirth(dateOfBirth);
        lecturer.setGender(gender);
        lecturer.setPhoneNumber(phoneNumber);
        lecturer.setEmail(email);
        lecturer.setAddress(address);
        lecturer.setLecturerCode(lecturerCode);
        lecturer.setFaculty(faculty);
        lecturer.setDepartment(department);
        lecturer.setAcademicDegree(academicDegree);
        lecturer.setAcademicTitle(academicTitle);
        lecturer.setSpecialization(specialization);
        lecturer.setSalary(salary);
        lecturer.setYearsOfExperience(yearsOfExperience);

        System.out.println("Đã cập nhật giáo viên " + lecturer.getId());
        return lecturer;
    }

    // ========== XOÁ GIÁO VIÊN ==========

    public static Lecturer deleteLecturerFromKeyboard(List<Lecturer> lecturerList, Scanner scanner) {
        try {
            System.out.println("Nhập 0 để huỷ xoá.");
            Lecturer lecturer = selectLecturer(lecturerList, scanner, "Chọn giáo viên cần xoá");
            if (lecturer == null) {
                return null;
            }
            lecturerList.remove(lecturer);
            System.out.println("Đã xoá giáo viên " + lecturer.getId());
            return lecturer;
        } catch (InputCancelledException exception) {
            System.out.println("Đã huỷ xoá giáo viên.");
            return null;
        }
    }

    // ========== XEM TOÀN BỘ GIÁO VIÊN ==========

    public static void showAllLecturers(List<Lecturer> lecturerList) {
        if (lecturerList.isEmpty()) {
            System.out.println("Danh sách giáo viên đang trống.");
            return;
        }

        int nameWidth = Math.max(
                "HỌ VÀ TÊN".length(),
                lecturerList.stream().mapToInt(l -> l.getFullName().length()).max().orElse(0)
        );
        int facultyWidth = Math.max(
                "KHOA".length(),
                lecturerList.stream().mapToInt(l -> l.getFaculty().length()).max().orElse(0)
        );
        int degreeWidth = Math.max(
                "HỌC VỊ".length(),
                lecturerList.stream().mapToInt(l -> l.getAcademicDegree().length()).max().orElse(0)
        );

        String rowFormat = "%-4s | %-8s | %-6s | %-" + nameWidth + "s | %-" 
                + facultyWidth + "s | %-" + degreeWidth + "s%n";
        
        int tableWidth = 4 + 3 + 8 + 3 + 6 + 3 + nameWidth + 3 + facultyWidth + 3 + degreeWidth;
        String separator = "-".repeat(tableWidth);

        System.out.println("\nDANH SÁCH GIÁO VIÊN");
        System.out.println(separator);
        System.out.printf(rowFormat, "STT", "ID", "MÃ GV", "HỌ VÀ TÊN", "KHOA", "HỌC VỊ");
        System.out.println(separator);

        for (int index = 0; index < lecturerList.size(); index++) {
            Lecturer lecturer = lecturerList.get(index);
            System.out.printf(
                    rowFormat,
                    index + 1,
                    lecturer.getId(),
                    lecturer.getLecturerCode(),
                    lecturer.getFullName(),
                    lecturer.getFaculty(),
                    lecturer.getAcademicDegree()
            );
        }
        System.out.println(separator);
        System.out.println("Tổng số giáo viên: " + lecturerList.size());
    }

    // ========== TÌM KIẾM GIÁO VIÊN THEO TÊN ==========

    public static List<Lecturer> searchLecturersByName(List<Lecturer> lecturerList, Scanner scanner) {
        try {
            System.out.println("Nhập 0 để huỷ tìm kiếm.");
            String keyword = readRequiredText(scanner, "Nhập tên giáo viên cần tìm: ");
            String normalizedKeyword = normalizeText(keyword);

            List<Lecturer> result = lecturerList.stream()
                    .filter(l -> normalizeText(l.getFullName()).contains(normalizedKeyword))
                    .toList();

            if (result.isEmpty()) {
                System.out.println("Không tìm thấy giáo viên có tên chứa: " + keyword);
            } else {
                System.out.println("Tìm thấy " + result.size() + " giáo viên:");
                showAllLecturers(result);
            }
            return result;
        } catch (InputCancelledException exception) {
            System.out.println("Đã huỷ tìm kiếm giáo viên.");
            return List.of();
        }
    }

    // ========== CÁC HÀM HỖ TRỢ (HELPER) ==========

    private static String normalizeText(String value) {
        return Normalizer.normalize(value, Normalizer.Form.NFD)
                .replaceAll("\\p{M}+", "")
                .replace('đ', 'd')
                .replace('Đ', 'D')
                .toLowerCase(Locale.ROOT);
    }

    private static Lecturer selectLecturer(List<Lecturer> lecturerList, Scanner scanner, String message) {
        if (lecturerList.isEmpty()) {
            System.out.println("Danh sách giáo viên đang trống.");
            return null;
        }
        showAllLecturers(lecturerList);
        while (true) {
            int selectedIndex = readInt(scanner, message + " (1-" + lecturerList.size() + "): ");
            if (selectedIndex >= 1 && selectedIndex <= lecturerList.size()) {
                return lecturerList.get(selectedIndex - 1);
            }
            System.out.println("Số thứ tự giáo viên không hợp lệ.");
        }
    }

    private static String generateNextLecturerId(List<Lecturer> lecturerList) {
        int maxId = lecturerList.stream()
                .map(Lecturer::getId)
                .filter(id -> id != null && id.matches("LEC\\d+"))
                .mapToInt(id -> Integer.parseInt(id.substring(3)))
                .max()
                .orElse(0);
        return String.format("LEC%03d", maxId + 1);
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
                return dateFormat.parse(value.replace('/', '-'));
            } catch (ParseException exception) {
                System.out.println("Ngày không hợp lệ, vui lòng nhập theo dd-MM-yyyy.");
            }
        }
    }

    private static int readInt(Scanner scanner, String message) {
        while (true) {
            String value = readRequiredText(scanner, message);
            try {
                int number = Integer.parseInt(value);
                if (number >= 0) {
                    return number;
                }
                System.out.println("Số phải lớn hơn hoặc bằng 0.");
            } catch (NumberFormatException exception) {
                System.out.println("Vui lòng nhập một số nguyên hợp lệ.");
            }
        }
    }

    private static double readDouble(Scanner scanner, String message) {
        while (true) {
            String value = readRequiredText(scanner, message);
            try {
                double number = Double.parseDouble(value);
                if (number >= 0) {
                    return number;
                }
                System.out.println("Lương phải lớn hơn hoặc bằng 0.");
            } catch (NumberFormatException exception) {
                System.out.println("Vui lòng nhập một số hợp lệ.");
            }
        }
    }

    private static final class InputCancelledException extends RuntimeException {
    }
}
