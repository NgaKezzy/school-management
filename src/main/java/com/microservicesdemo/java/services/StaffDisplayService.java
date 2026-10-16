package com.microservicesdemo.java.services;

import com.microservicesdemo.java.models.Staff;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.text.Normalizer;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Scanner;
import java.util.stream.Collectors;

/**
 * Chứa các thao tác thêm, sửa, xoá, hiển thị, tìm kiếm và thống kê nhân viên trên console.
 * Các thay đổi hiện chỉ tác động đến List trong bộ nhớ, chưa ghi ngược vào file JSON.
 */
public final class StaffDisplayService {
    private StaffDisplayService() {
    }

    // ========== THÊM NHÂN VIÊN ==========

    public static Staff addStaffFromKeyboard(
            List<Staff> staffList,
            Scanner scanner
    ) {
        // Dùng exception nội bộ để có thể huỷ ngay cả khi đang nhập giữa chừng.
        try {
            return addStaff(staffList, scanner);
        } catch (InputCancelledException exception) {
            System.out.println("Đã huỷ thêm nhân viên.");
            return null;
        }
    }

    private static Staff addStaff(List<Staff> staffList, Scanner scanner) {
        System.out.println("Nhập 0 tại bất kỳ bước nào để huỷ.");
        String staffId = generateNextStaffId(staffList);
        System.out.println("ID nhân viên tự động: " + staffId);

        String fullName = readRequiredText(scanner, "Họ và tên: ");
        Date dateOfBirth = readDate(scanner, "Ngày sinh (dd-MM-yyyy): ");
        String gender = readRequiredText(scanner, "Giới tính: ");
        String phoneNumber = readRequiredText(scanner, "Số điện thoại: ");
        String email = readRequiredText(scanner, "Email: ");
        String address = readRequiredText(scanner, "Địa chỉ: ");
        String department = readRequiredText(scanner, "Phòng ban: ");
        double salary = readDouble(scanner, "Lương: ");

        Staff staff = new Staff(
                staffId,
                fullName,
                dateOfBirth,
                gender,
                phoneNumber,
                email,
                address,
                department,
                salary
        );

        staffList.add(staff);

        System.out.println("Đã thêm nhân viên " + staffId);
        return staff;
    }

    // ========== SỬA NHÂN VIÊN ==========

    public static Staff updateStaffFromKeyboard(List<Staff> staffList, Scanner scanner) {
        try {
            return updateStaff(staffList, scanner);
        } catch (InputCancelledException exception) {
            System.out.println("Đã huỷ sửa nhân viên. Dữ liệu cũ được giữ nguyên.");
            return null;
        }
    }

    private static Staff updateStaff(List<Staff> staffList, Scanner scanner) {
        System.out.println("Nhập 0 tại bất kỳ bước nào để huỷ.");
        Staff staff = selectStaff(staffList, scanner, "Chọn nhân viên cần sửa");
        if (staff == null) {
            return null;
        }

        System.out.println("Nhập lại thông tin cho " + staff.getId() + ":");

        // Đọc hết vào biến tạm trước; nếu người dùng huỷ thì object gốc chưa bị thay đổi.
        String fullName = readRequiredText(scanner, "Họ và tên: ");
        Date dateOfBirth = readDate(scanner, "Ngày sinh (dd-MM-yyyy): ");
        String gender = readRequiredText(scanner, "Giới tính: ");
        String phoneNumber = readRequiredText(scanner, "Số điện thoại: ");
        String email = readRequiredText(scanner, "Email: ");
        String address = readRequiredText(scanner, "Địa chỉ: ");
        String department = readRequiredText(scanner, "Phòng ban: ");
        double salary = readDouble(scanner, "Lương: ");

        // Chỉ cập nhật object sau khi toàn bộ dữ liệu đã được nhập hợp lệ.
        staff.setFullName(fullName);
        staff.setDateOfBirth(dateOfBirth);
        staff.setGender(gender);
        staff.setPhoneNumber(phoneNumber);
        staff.setEmail(email);
        staff.setAddress(address);
        staff.setDepartment(department);
        staff.setSalary(salary);

        System.out.println("Đã cập nhật nhân viên " + staff.getId());
        return staff;
    }

    // ========== XOÁ NHÂN VIÊN ==========

    public static Staff deleteStaffFromKeyboard(List<Staff> staffList, Scanner scanner) {
        try {
            System.out.println("Nhập 0 để huỷ xoá.");
            Staff staff = selectStaff(staffList, scanner, "Chọn nhân viên cần xoá");
            if (staff == null) {
                return null;
            }
            staffList.remove(staff);
            System.out.println("Đã xoá nhân viên " + staff.getId());
            return staff;
        } catch (InputCancelledException exception) {
            System.out.println("Đã huỷ xoá nhân viên.");
            return null;
        }
    }

    // ========== XEM TOÀN BỘ ==========

    /**
     * In toàn bộ danh sách nhân viên ra console dưới dạng bảng có header và separator.
     * Độ rộng cột tự tính theo nội dung dài nhất để các dòng luôn thẳng hàng.
     */
    public static void showAllStaff(List<Staff> staffList) {
        if (staffList.isEmpty()) {
            System.out.println("Danh sách nhân viên đang trống.");
            return;
        }

        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
        // Tính độ rộng theo nội dung dài nhất để các cột luôn thẳng hàng.
        int nameWidth = Math.max(
                "HỌ VÀ TÊN".length(),
                staffList.stream().mapToInt(staff -> staff.getFullName().length()).max().orElse(0)
        );
        int departmentWidth = Math.max(
                "PHÒNG BAN".length(),
                staffList.stream().mapToInt(staff -> staff.getDepartment().length()).max().orElse(0)
        );
        // Lương hiển thị dạng "25,000,000 đ" — tính độ rộng tối thiểu cho cột này.
        int salaryWidth = Math.max(
                "LƯƠNG".length(),
                staffList.stream()
                        .mapToInt(staff -> String.format("%,.0f đ", staff.getSalary()).length())
                        .max().orElse(0)
        );

        // Format chuỗi cho mỗi dòng: STT | MÃ NV | HỌ VÀ TÊN | NGÀY SINH | PHÒNG BAN | LƯƠNG
        String rowFormat = "%-4s | %-8s | %-" + nameWidth + "s | %-10s | %-"
                + departmentWidth + "s | %" + salaryWidth + "s%n";
        int tableWidth = 4 + 3 + 8 + 3 + nameWidth + 3 + 10 + 3
                + departmentWidth + 3 + salaryWidth;
        String separator = "-".repeat(tableWidth);

        System.out.println("\nDANH SÁCH NHÂN VIÊN");
        System.out.println(separator);
        System.out.printf(
                rowFormat,
                "STT", "MÃ NV", "HỌ VÀ TÊN", "NGÀY SINH", "PHÒNG BAN", "LƯƠNG"
        );
        System.out.println(separator);

        for (int index = 0; index < staffList.size(); index++) {
            Staff staff = staffList.get(index);
            System.out.printf(
                    rowFormat,
                    index + 1,
                    staff.getId(),
                    staff.getFullName(),
                    dateFormat.format(staff.getDateOfBirth()),
                    staff.getDepartment(),
                    String.format("%,.0f đ", staff.getSalary())
            );
        }

        System.out.println(separator);
        System.out.println("Tổng số nhân viên: " + staffList.size());
    }

    // ========== TÌM KIẾM THEO TÊN ==========

    public static List<Staff> searchStaffByName(List<Staff> staffList, Scanner scanner) {
        try {
            System.out.println("Nhập 0 để huỷ tìm kiếm.");
            String keyword = readRequiredText(scanner, "Nhập tên cần tìm: ");
            String normalizedKeyword = normalizeText(keyword);

            List<Staff> result = staffList.stream()
                    .filter(staff -> normalizeText(staff.getFullName()).contains(normalizedKeyword))
                    .toList();

            if (result.isEmpty()) {
                System.out.println("Không tìm thấy nhân viên có tên chứa: " + keyword);
            } else {
                System.out.println("Tìm thấy " + result.size() + " nhân viên:");
                showAllStaff(result);
            }
            return result;
        } catch (InputCancelledException exception) {
            System.out.println("Đã huỷ tìm kiếm nhân viên.");
            return List.of();
        }
    }

    // ========== THỐNG KÊ LƯƠNG THEO PHÒNG BAN ==========

    public static void showSalaryStatistics(List<Staff> staffList) {
        if (staffList.isEmpty()) {
            System.out.println("Danh sách nhân viên đang trống, không có dữ liệu thống kê.");
            return;
        }

        // Nhóm nhân viên theo phòng ban.
        Map<String, List<Staff>> groupedByDepartment = staffList.stream()
                .collect(Collectors.groupingBy(Staff::getDepartment));

        // Tính tổng lương toàn trường.
        double totalSalary = staffList.stream().mapToDouble(Staff::getSalary).sum();
        double averageSalary = totalSalary / staffList.size();
        double maxSalary = staffList.stream().mapToDouble(Staff::getSalary).max().orElse(0);
        double minSalary = staffList.stream().mapToDouble(Staff::getSalary).min().orElse(0);

        // Tính độ rộng cột phòng ban.
        int deptWidth = Math.max(
                "PHÒNG BAN".length(),
                groupedByDepartment.keySet().stream()
                        .mapToInt(String::length).max().orElse(0)
        );

        String rowFormat = "%-" + deptWidth + "s | %-10s | %15s | %15s | %15s%n";
        int tableWidth = deptWidth + 3 + 10 + 3 + 15 + 3 + 15 + 3 + 15;
        String separator = "-".repeat(tableWidth);

        System.out.println("\n========== THỐNG KÊ LƯƠNG THEO PHÒNG BAN ==========");
        System.out.println(separator);
        System.out.printf(rowFormat, "PHÒNG BAN", "SỐ LƯỢNG", "LƯƠNG TB", "LƯƠNG CAO NHẤT", "LƯƠNG THẤP NHẤT");
        System.out.println(separator);

        for (Map.Entry<String, List<Staff>> entry : groupedByDepartment.entrySet()) {
            String dept = entry.getKey();
            List<Staff> members = entry.getValue();
            double deptAvg = members.stream().mapToDouble(Staff::getSalary).average().orElse(0);
            double deptMax = members.stream().mapToDouble(Staff::getSalary).max().orElse(0);
            double deptMin = members.stream().mapToDouble(Staff::getSalary).min().orElse(0);

            System.out.printf(
                    rowFormat,
                    dept,
                    members.size(),
                    String.format("%,.0f đ", deptAvg),
                    String.format("%,.0f đ", deptMax),
                    String.format("%,.0f đ", deptMin)
            );
        }

        System.out.println(separator);
        System.out.println("\n--- TỔNG QUAN ---");
        System.out.printf("Tổng số nhân viên : %d%n", staffList.size());
        System.out.printf("Tổng quỹ lương    : %,.0f đ%n", totalSalary);
        System.out.printf("Lương trung bình  : %,.0f đ%n", averageSalary);
        System.out.printf("Lương cao nhất    : %,.0f đ%n", maxSalary);
        System.out.printf("Lương thấp nhất   : %,.0f đ%n", minSalary);
    }

    // ========== HÀM HỖ TRỢ ==========

    private static String normalizeText(String value) {
        // Bỏ dấu và chuyển về chữ thường để "nguyen" có thể khớp với "Nguyễn".
        return Normalizer.normalize(value, Normalizer.Form.NFD)
                .replaceAll("\\p{M}+", "")
                .replace('đ', 'd')
                .replace('Đ', 'D')
                .toLowerCase(Locale.ROOT);
    }

    private static Staff selectStaff(List<Staff> staffList, Scanner scanner, String message) {
        if (staffList.isEmpty()) {
            System.out.println("Danh sách nhân viên đang trống.");
            return null;
        }

        showAllStaff(staffList);
        // Người dùng nhìn thấy STT từ 1, còn List của Java sử dụng index bắt đầu từ 0.
        while (true) {
            int selectedIndex = readInt(scanner, message + " (1-" + staffList.size() + "): ");
            if (selectedIndex >= 1 && selectedIndex <= staffList.size()) {
                return staffList.get(selectedIndex - 1);
            }
            System.out.println("Số thứ tự nhân viên không hợp lệ.");
        }
    }

    private static String generateNextStaffId(List<Staff> staffList) {
        // Lấy phần số lớn nhất của các ID dạng STAFFxxx rồi tăng thêm 1.
        int maxId = staffList.stream()
                .map(Staff::getId)
                .filter(id -> id != null && id.matches("STAFF\\d+"))
                .mapToInt(id -> Integer.parseInt(id.substring(5)))
                .max()
                .orElse(0);

        return String.format("STAFF%03d", maxId + 1);
    }

    private static String readRequiredText(Scanner scanner, String message) {
        while (true) {
            System.out.print(message);
            String value = scanner.nextLine().trim();
            // Quy ước chung: nhập 0 tại bất kỳ trường nào sẽ huỷ thao tác hiện tại.
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
        // Không cho phép Java tự sửa ngày sai, ví dụ 31-02 thành một ngày trong tháng 3.
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
                int number = Integer.parseInt(value);

                if (number > 0) {
                    return number;
                }

                System.out.println("Số phải lớn hơn 0.");
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

    /** Exception chỉ dùng nội bộ để thoát nhanh khỏi chuỗi bước nhập liệu. */
    private static final class InputCancelledException extends RuntimeException {
    }
}
