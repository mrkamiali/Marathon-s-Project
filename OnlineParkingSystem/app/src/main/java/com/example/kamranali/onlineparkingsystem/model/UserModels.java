package com.example.kamranali.onlineparkingsystem.model;

/**
 * Created by kamranali on 27/01/2017.
 */

public class UserModels {
    private String email;
    private String password;
    private String mobileNo;
    private String gender;
    private String name;
    private String dob;


    public UserModels() {
    }

    public UserModels(String email, String password, String mobileNo, String gender, String name, String dob) {
        this.email = email;
        this.password = password;
        this.mobileNo = mobileNo;
        this.gender = gender;
        this.name = name;
        this.dob = dob;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getMobileNo() {
        return mobileNo;
    }

    public void setMobileNo(String mobileNo) {
        this.mobileNo = mobileNo;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDob() {
        return dob;
    }

    public void setDob(String dob) {
        this.dob = dob;
    }
}
