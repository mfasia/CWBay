package com.metafour.cwbay.model;

import java.util.List;

/**
 * Created by nadim on 1/26/15.
 */
public class Category {
    public static final String JSON_TAG_ID = "id";
    public static final String JSON_TAG_NAME = "name";
    public static final String JSON_TAG_CHILDREN = "children";
    public static final String JSON_TAG_ADS = "ads";
    public static final String JSON_TAG_HAS_CHILDREN = "hasChildren";

    private int id;
    private String name;
    private List<Category> children;
    private List<Ad> ads;
    private boolean hasChildren;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Category> getChildren() {
        return children;
    }

    public void setChildren(List<Category> children) {
        this.children = children;
    }

    public boolean isHasChildren() {
        return hasChildren;
    }

    public void setHasChildren(boolean hasChildren) {
        this.hasChildren = hasChildren;
    }

    public List<Ad> getAds() {
        return ads;
    }

    public void setAds(List<Ad> ads) {
        this.ads = ads;
    }

    @Override
    public String toString() {
        return "Category{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", children=" + children +
                ", ads=" + ads +
                ", hasChildren=" + hasChildren +
                '}';
    }
}
