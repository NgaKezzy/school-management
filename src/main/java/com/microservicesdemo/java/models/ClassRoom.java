package com.microservicesdemo.java.models;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.List;

public class ClassRoom {
    @JsonCreator
    public ClassRoom(@JsonProperty("id") String id,
                     @JsonProperty("className") String className,
                     @JsonProperty("homeroomLecturerId") String homeroomLecturerId,
                     @JsonProperty("studentIds") List<String> studentIds) {
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
