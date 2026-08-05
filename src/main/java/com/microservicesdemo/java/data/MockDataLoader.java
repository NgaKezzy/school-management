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

public final class MockDataLoader {
    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    static {
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
        try (InputStream inputStream = MockDataLoader.class.getResourceAsStream(resourcePath)) {
            if (inputStream == null) {
                throw new IllegalStateException("Không tìm thấy " + resourcePath);
            }

            return OBJECT_MAPPER.readValue(inputStream, typeReference);
        }
    }
}
