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
            category.setId(jsonCategory.getInt(Category.JSON_TAG_ID));
            category.setName(jsonCategory.getString(Category.JSON_TAG_NAME));
            List<Category> children = new ArrayList<Category>();
            JSONArray childrenJson = jsonCategory.getJSONArray(Category.JSON_TAG_CHILDREN);
            for (int i = 0; i < childrenJson.length(); i++) {
                JSONObject childJson = childrenJson.getJSONObject(i);
                Category child = new Category();
                child.setId(childJson.getInt(Category.JSON_TAG_ID));
                child.setName(childJson.getString(Category.JSON_TAG_NAME));
                child.setHasChildren(childJson.getBoolean(Category.JSON_TAG_HAS_CHILDREN));
                children.add(child);
            }
            category.setChildren(children);
            JSONArray adsJson = jsonCategory.getJSONArray(Category.JSON_TAG_ADS);
            List<Ad> ads = new ArrayList<Ad>();
            for (int i = 0; i < adsJson.length(); i++) {
                JSONObject adJson = adsJson.getJSONObject(i);
                Ad ad = new Ad();
                ad.setId(adJson.getString(Ad.JSON_TAG_DESC));
                ad.setTitle(adJson.getString(Ad.JSON_TAG_TITLE));
                ad.setPrice(adJson.getDouble(Ad.JSON_TAG_PRICE));
                ad.setTime(Constants.DATE_FORMAT.parse(adJson.getString(Ad.JSON_TAG_TIME)));
                ad.setPlace(adJson.getString(Ad.JSON_TAG_PLACE));
                ads.add(ad);
            }
            category.setAds(ads);
        } catch (Exception e) {
            category = null;
        }
        return category;
    }

}
