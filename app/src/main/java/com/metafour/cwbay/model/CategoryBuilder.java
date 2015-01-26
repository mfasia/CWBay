package com.metafour.cwbay.model;

import org.json.JSONArray;
import org.json.JSONObject;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by nadim on 1/26/15.
 */
public class CategoryBuilder {
    public static Category build(String json) {
        Category category = null;
        try {
            category = new Category();
            JSONObject jsonCategory = new JSONObject(json);
            category.setId(jsonCategory.getInt(Category.JSON_TAG_ID));
            category.setName(jsonCategory.getString(Category.JSON_TAG_NAME));
            List<Category> children = new ArrayList<Category>();
            JSONArray childrenJson = jsonCategory.getJSONArray(Category.JSON_TAG_CHILDREN);
            for (int i = 0; i < childrenJson.length(); i++) {
                JSONObject childJson = childrenJson.getJSONObject(i);
                Category child = new Category();
                child.setId(childJson.getInt(Category.JSON_TAG_ID));
                child.setName(childJson.getString(Category.JSON_TAG_NAME));
                children.add(child);
            }
            category.setChildren(children);
        } catch (Exception e) {
            category = null;
        }
        return category;
    }

}
