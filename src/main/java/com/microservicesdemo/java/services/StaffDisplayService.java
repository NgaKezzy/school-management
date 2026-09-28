package com.microservicesdemo.java.services;

import com.microservicesdemo.java.models.Staff;

import java.util.List;

/**
 * Hiển thị danh sách nhân viên trên console dưới dạng bảng thẳng hàng.
 * Tách riêng khỏi DashboardService để mỗi service chỉ phụ trách một nhóm chức năng (Single Responsibility).
 */
public final class StaffDisplayService {
    private StaffDisplayService() {
    }

    /**
     * In toàn bộ danh sách nhân viên ra console dưới dạng bảng có header và separator.
     * Độ rộng cột tự tính theo nội dung dài nhất để các dòng luôn thẳng hàng.
     */
    public static void showAllStaff(List<Staff> staffList) {
        if (staffList.isEmpty()) {
            System.out.println("Danh sách nhân viên đang trống.");
            return;
        }

        // Tính độ rộng cột theo nội dung dài nhất, đảm bảo không nhỏ hơn header.
        int idWidth = Math.max(
                "MÃ NV".length(),
                staffList.stream().mapToInt(staff -> staff.getId().length()).max().orElse(0)
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

        // Format chuỗi cho mỗi dòng: STT | MÃ NV | PHÒNG BAN | LƯƠNG
        String rowFormat = "%-4s | %-" + idWidth + "s | %-" + departmentWidth + "s | %" + salaryWidth + "s%n";
        int tableWidth = 4 + 3 + idWidth + 3 + departmentWidth + 3 + salaryWidth;
        String separator = "-".repeat(tableWidth);

        System.out.println("\nDANH SÁCH NHÂN VIÊN");
        System.out.println(separator);
        System.out.printf(rowFormat, "STT", "MÃ NV", "PHÒNG BAN", "LƯƠNG");
        System.out.println(separator);

        for (int index = 0; index < staffList.size(); index++) {
            Staff staff = staffList.get(index);
            System.out.printf(
                    rowFormat,
                    index + 1,
                    staff.getId(),
                    staff.getDepartment(),
                    String.format("%,.0f đ", staff.getSalary())
            );
        }

        System.out.println(separator);
        System.out.println("Tổng số nhân viên: " + staffList.size());
    }
}
