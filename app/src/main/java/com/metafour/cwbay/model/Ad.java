package com.metafour.cwbay.model;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by nadim on 1/27/15.
 */
public class Ad {
    public static final String JSON_TAG_CATEGORY = "category";
    public static final String JSON_TAG_SUBCATEGORY = "subcategory";
    public static final String JSON_TAG_TITLE = "title";
    public static final String JSON_TAG_DESC = "desc";
    public static final String JSON_TAG_PRICE = "price";
    public static final String JSON_TAG_TIME = "time";
    public static final String JSON_TAG_PLACE = "place";
    public static final String JSON_TAG_PROPS = "props";
    public static final String JSON_TAG_KEY = "key";
    public static final String JSON_TAG_VALUE = "value";
    public static final String JSON_TAG_VIEWS = "views";
    public static final String JSON_TAG_FAV = "fav";
    public static final String JSON_TAG_CONTACT = "contact";
    public static final String JSON_TAG_NAME = "name";
    public static final String JSON_TAG_EMAIL = "email";
    public static final String JSON_TAG_PHONE = "phone";
    public static final String JSON_TAG_IMAGES = "images";

    private String id;
    private String categoryName;
    private String subcategoryName;
    private String title;
    private String description;
    private double price;
    private Date time;
    private String place;
    private Map<String, String> properties;
    private int views;
    private boolean favorite;
    private String contactName;
    private String contactPhone;
    private String contactEmail;
    private List<String> images;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public String getSubcategoryName() {
        return subcategoryName;
    }

    public void setSubcategoryName(String subcategoryName) {
        this.subcategoryName = subcategoryName;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    public String getPlace() {
        return place;
    }

    public void setPlace(String place) {
        this.place = place;
    }

    public Map<String, String> getProperties() {
        return properties;
    }

    public void setProperties(Map<String, String> properties) {
        this.properties = properties;
    }

    public int getViews() {
        return views;
    }

    public void setViews(int views) {
        this.views = views;
    }

    public boolean isFavorite() {
        return favorite;
    }

    public void setFavorite(boolean favorite) {
        this.favorite = favorite;
    }

    public String getContactName() {
        return contactName;
    }

    public void setContactName(String contactName) {
        this.contactName = contactName;
    }

    public String getContactPhone() {
        return contactPhone;
    }

    public void setContactPhone(String contactPhone) {
        this.contactPhone = contactPhone;
    }

    public String getContactEmail() {
        return contactEmail;
    }

    public void setContactEmail(String contactEmail) {
        this.contactEmail = contactEmail;
    }

    /**
     * List of links to images.
     * @param images
     */
    public List<String> getImages() {
        return images;
    }

    public void setImages(List<String> images) {
        this.images = images;
    }

    /**
     * Access value of customized field particular for this Ad/Subcategory.
     * @param key
     * @param defaultValue
     * @return
     */
    public String getProperty(String key, String defaultValue) {
        String value = properties.get(key);
        return value == null ? defaultValue : value;
    }


    @Override
    public String toString() {
        return "Ad{" +
                "id='" + id + '\'' +
                ", categoryName='" + categoryName + '\'' +
                ", subcategoryName='" + subcategoryName + '\'' +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", price=" + price +
                ", time=" + time +
                ", place='" + place + '\'' +
                ", properties=" + properties +
                ", views=" + views +
                ", favorite=" + favorite +
                ", contactName='" + contactName + '\'' +
                ", contactPhone='" + contactPhone + '\'' +
                ", contactEmail='" + contactEmail + '\'' +
                ", images=" + images +
                '}';
    }
}
