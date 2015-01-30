package com.metafour.cwbay.model;

import java.util.Date;

/**
 * Created by nadim on 1/21/15.
 */
public class User {
    public static final String JSON_TAG_NAME = "name";
    public static final String JSON_TAG_EMAIL = "email";
    public static final String JSON_TAG_PHONE = "phone";
    public static final String JSON_TAG_PASSWORD = "password";
    public static final String JSON_TAG_PLACE = "place";

    private String name;
    private String email;
    private String phone;
    private String password;
    private String place;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPlace() {
        return place;
    }

    public void setPlace(String place) {
        this.place = place;
    }

    @Override
    public String toString() {
        return "User{" +
                "name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", phone='" + phone + '\'' +
                ", password='" + password + '\'' +
                ", place='" + place + '\'' +
                '}';
    }
}
