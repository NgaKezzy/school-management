package com.microservicesdemo.java;

import com.microservicesdemo.java.data.MockDataLoader;
import com.microservicesdemo.java.models.ClassRoom;
import com.microservicesdemo.java.models.Lecturer;
import com.microservicesdemo.java.models.Staff;
import com.microservicesdemo.java.models.Student;

import java.io.IOException;
import java.util.List;

public class Main {
    public static void main(String[] args) throws IOException {
        List<Student> studentList = MockDataLoader.getStudents();
        List<ClassRoom> classRoomList = MockDataLoader.getClassRooms();
        List<Lecturer> lecturerList = MockDataLoader.getLecturers();
        List<Staff> staffList = MockDataLoader.getStaff();

        System.out.println("Students: " + studentList.size());
        System.out.println("Class rooms: " + classRoomList.size());
        System.out.println("Lecturers: " + lecturerList.size());
        System.out.println("Staff: " + staffList.size());
    }
}
