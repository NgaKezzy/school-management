package com.microservicesdemo.java.models;

import java.util.Date;

 public abstract class Person {
     public Person(String id, String fullName, Date dateOfBirth, String gender, String phoneNumber, String email,
                   String address) {
         this.id = id;
         this.fullName = fullName;
         this.dateOfBirth = dateOfBirth;
         this.gender = gender;
         this.phoneNumber = phoneNumber;
         this.email = email;
         this.address = address;
     }

     private String id;
    private String fullName;
    private Date dateOfBirth;
    private String gender;
    private String phoneNumber;
    private String email;
    private String address;

     public String getId() {
         return id;
     }

     public void setId(String id) {
         this.id = id;
     }

     public String getFullName() {
         return fullName;
     }

     public void setFullName(String fullName) {
         this.fullName = fullName;
     }

     public Date getDateOfBirth() {
         return dateOfBirth;
     }

     public void setDateOfBirth(Date dateOfBirth) {
         this.dateOfBirth = dateOfBirth;
     }

     public String getGender() {
         return gender;
     }

     public void setGender(String gender) {
         this.gender = gender;
     }

     public String getPhoneNumber() {
         return phoneNumber;
     }

     public void setPhoneNumber(String phoneNumber) {
         this.phoneNumber = phoneNumber;
     }

     public String getEmail() {
         return email;
     }

     public void setEmail(String email) {
         this.email = email;
     }

     public String getAddress() {
         return address;
     }

     public void setAddress(String address) {
         this.address = address;
     }
 }
