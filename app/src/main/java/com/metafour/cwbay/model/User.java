package com.metafour.cwbay.model;

import java.util.Date;

/**
 * Created by nadim on 1/21/15.
 */
public class User {
    public static final String JSON_TAG_NAME = "name";
    public static final String JSON_TAG_EMAIL = "email";
    public static final String JSON_TAG_IMAGE = "image";
    public static final String JSON_TAG_PASSWORD = "password";
    public static final String JSON_TAG_MEMBER_SINCE = "memberSince";

    private String name;
    private String email;
    private String password;
    private String image;
    private Date memberSince;

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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public Date getMemberSince() {
        return memberSince;
    }

    public void setMemberSince(Date memberSince) {
        this.memberSince = memberSince;
    }

    @Override
    public String toString() {
        return "User{" +
                "name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", image='" + image + '\'' +
                ", memberSince=" + memberSince +
                '}';
    }
}
