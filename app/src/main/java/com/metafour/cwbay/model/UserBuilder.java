package com.metafour.cwbay.model;

import com.metafour.cwbay.util.Constants;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

/**
 * Created by nadim on 1/21/15.
 */
public class UserBuilder {
    public static User build(String json) {
        User user = null;
        try {
            user = new User();
            JSONObject jsonUser = new JSONObject(json);
            user.setEmail(jsonUser.getString(User.JSON_TAG_EMAIL));
            user.setName(jsonUser.getString(User.JSON_TAG_NAME));
            user.setImage(jsonUser.getString(User.JSON_TAG_IMAGE));
            user.setMemberSince(Constants.DATE_FORMAT.parse(jsonUser.getString(User.JSON_TAG_MEMBER_SINCE)));
        } catch (Exception e) {
            user = null;
        }
        return user;
    }

    public static String serialize(User user) {
        String jsonStr = "";
        JSONObject json = new JSONObject();
        try {
            json.put(User.JSON_TAG_EMAIL, user.getEmail());
            json.put(User.JSON_TAG_PASSWORD, user.getPassword());
            json.put(User.JSON_TAG_NAME, user.getName());
            jsonStr = json.toString();
        } catch (Exception e) {
            jsonStr = "";
        }
        return jsonStr;
    }
}
