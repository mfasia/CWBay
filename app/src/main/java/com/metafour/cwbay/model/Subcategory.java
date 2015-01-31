package com.metafour.cwbay.model;

import java.util.List;

/**
 * Created by Nadim on 31-Jan-15.
 */
public class Subcategory {
    public static final String JSON_TAG_NAME = "subcategory";
    public static final String JSON_TAG_CATEGORY_NAME  = "category";
    public static final String JSON_TAG_ADS = "ads";

    private String id;
    private String categoryName;
    private String name;
    private List<Ad> ads;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Ad> getAds() {
        return ads;
    }

    public void setAds(List<Ad> ads) {
        this.ads = ads;
    }

    @Override
    public String toString() {
        return "Subcategory{" +
                "id='" + id + '\'' +
                ", categoryName='" + categoryName + '\'' +
                ", name='" + name + '\'' +
                ", ads=" + ads +
                '}';
    }
}
