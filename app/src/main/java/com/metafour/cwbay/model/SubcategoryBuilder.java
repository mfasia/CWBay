package com.metafour.cwbay.model;

import com.metafour.cwbay.util.Constants;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Nadim on 31-Jan-15.
 */
public class SubcategoryBuilder {
    public static Subcategory build(String json) {
        Subcategory scat = null;
        try {
            scat = new Subcategory();
            JSONObject jsonSCat = new JSONObject(json);
            scat.setName(jsonSCat.getString(Subcategory.JSON_TAG_NAME));
            scat.setCategoryName(jsonSCat.getString(Subcategory.JSON_TAG_CATEGORY_NAME));
            List<Ad> ads = new ArrayList<Ad>();
            JSONArray jsonAds = jsonSCat.getJSONArray(Subcategory.JSON_TAG_ADS);
            for (int i = 0; i < jsonAds.length(); i++) {
                Ad ad = new Ad();
                JSONObject jsonAd = jsonAds.getJSONObject(i);
                ad.setId(jsonAd.getString(Ad.JSON_TAG_ID));
                ad.setTitle(jsonAd.getString(Ad.JSON_TAG_TITLE));
                ad.setPrice(jsonAd.getDouble(Ad.JSON_TAG_PRICE));
                ad.setTime(Constants.DATE_FORMAT.parse(jsonAd.getString(Ad.JSON_TAG_TIME)));
                ad.setPlace(jsonAd.getString(Ad.JSON_TAG_PLACE));
                List<String> images = new ArrayList<String>();
                JSONArray jsonImages = jsonAd.getJSONArray(Ad.JSON_TAG_IMAGES);
                if (jsonImages.length() > 0) images.add(jsonImages.getString(0));
                ad.setImages(images);
                ads.add(ad);
            }
            scat.setAds(ads);
        } catch (Exception e) {
            scat = null;
        }
        return scat;
    }

}
