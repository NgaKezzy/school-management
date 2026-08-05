package com.microservicesdemo.java.models;

import java.util.ArrayList;
import java.util.List;

public class ClassRoom {
    public ClassRoom(String id, String className, String homeroomLecturerId, List<String> studentIds) {
        setId(id);
        this.className = className;
        setHomeroomLecturerId(homeroomLecturerId);
        setStudentIds(studentIds);
    }

    private String id;
    private String className;
    private String homeroomLecturerId;
    private List<String> studentIds;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        if (id == null || id.isBlank()) {
            throw new IllegalArgumentException("Class room id is required");
        }
        this.id = id;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public String getHomeroomLecturerId() {
        return homeroomLecturerId;
    }

    public void setHomeroomLecturerId(String homeroomLecturerId) {
        if (homeroomLecturerId == null || homeroomLecturerId.isBlank()) {
            throw new IllegalArgumentException("Class room must have a homeroom lecturer");
        }
        this.homeroomLecturerId = homeroomLecturerId;
    }

    public List<String> getStudentIds() {
        return studentIds;
    }

    public void setStudentIds(List<String> studentIds) {
        this.studentIds = studentIds == null ? new ArrayList<>() : new ArrayList<>(studentIds);
    }

    public void addStudentId(String studentId) {
        if (studentId == null || studentId.isBlank()) {
            throw new IllegalArgumentException("Student id is required");
        }
        if (!studentIds.contains(studentId)) {
            studentIds.add(studentId);
        }
    }
}
