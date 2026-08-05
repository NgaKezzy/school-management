package com.microservicesdemo.java.data;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.microservicesdemo.java.models.Lecturer;
import com.microservicesdemo.java.models.Staff;
import com.microservicesdemo.java.models.Student;

import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.List;

/**
 * Đọc dữ liệu mẫu trong thư mục src/main/resources/mock và chuyển JSON thành model Java.
 * Đây là utility class nên chỉ cung cấp các phương thức static và không cho phép tạo object.
 */
public final class MockDataLoader {
    // Dùng chung một ObjectMapper để không phải cấu hình lại mỗi lần đọc file.
    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    static {
        // Ngày trong tất cả file mock có định dạng ngày-tháng-năm, ví dụ 15-03-2006.
        OBJECT_MAPPER.setDateFormat(new SimpleDateFormat("dd-MM-yyyy"));
    }

    private MockDataLoader() {
    }

    public static List<Student> getStudents() throws IOException {
        return readData(
                "/mock/students.json",
                new TypeReference<List<Student>>() {}
        );
    }

    public static List<Lecturer> getLecturers() throws IOException {
        return readData(
                "/mock/lecturers.json",
                new TypeReference<List<Lecturer>>() {}
        );
    }

    public static List<Staff> getStaff() throws IOException {
        return readData(
                "/mock/staff.json",
                new TypeReference<List<Staff>>() {}
        );
    }

    private static <T> T readData(String resourcePath, TypeReference<T> typeReference) throws IOException {
        // try-with-resources tự đóng InputStream sau khi đọc xong hoặc khi có lỗi.
        try (InputStream inputStream = MockDataLoader.class.getResourceAsStream(resourcePath)) {
            if (inputStream == null) {
                throw new IllegalStateException("Không tìm thấy " + resourcePath);
            }

            return OBJECT_MAPPER.readValue(inputStream, typeReference);
        }
    }
}
