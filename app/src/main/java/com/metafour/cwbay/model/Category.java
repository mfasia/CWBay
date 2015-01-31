package com.metafour.cwbay.model;

import java.util.List;

/**
 * Created by nadim on 1/26/15.
 */
public class Category {
    public static final String JSON_TAG_NAME = "category";
    public static final String JSON_TAG_SUBCATS = "subcats";

    private String name;
    private List<Subcategory> subcats;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Subcategory> getSubcats() {
        return subcats;
    }

    public void setSubcats(List<Subcategory> subcats) {
        this.subcats = subcats;
    }

    @Override
    public String toString() {
        return "Category{" +
                "name='" + name + '\'' +
                ", subcats=" + subcats +
                '}';
    }
}
