package com.metafour.cwbay.model;

import android.util.Log;

import com.metafour.cwbay.util.Constants;

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
            category.setName(jsonCategory.getString(Category.JSON_TAG_NAME));
            List<Subcategory> subcats = new ArrayList<Subcategory>();
            JSONArray jsonSCats  = jsonCategory.getJSONArray(Category.JSON_TAG_SUBCATS);
            for (int i = 0; i < jsonSCats.length(); i++) {
                Subcategory scat = new Subcategory();
                JSONObject jsonSCat = jsonSCats.getJSONObject(i);
                scat.setId(jsonSCat.getString(Subcategory.JSON_TAG_ID));
                scat.setName(jsonSCat.getString("name"));
                subcats.add(scat);
            }
            category.setSubcats(subcats);
        } catch (Exception e) {
            Log.e(Constants.ACTIVITY_LOG_TAG, e.getMessage());
            category = null;
        }
        return category;
    }

}
