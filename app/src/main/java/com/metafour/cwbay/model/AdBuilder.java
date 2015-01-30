package com.metafour.cwbay.model;

import com.metafour.cwbay.util.Constants;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by nadim on 1/27/15.
 */
public class AdBuilder {
    public static Ad build(String json) {
        Ad ad = null;
        try {
            ad = new Ad();
            JSONObject jsonAd = new JSONObject(json);
            ad.setTitle(jsonAd.getString(Ad.JSON_TAG_TITLE));
            ad.setDescription(jsonAd.getString(Ad.JSON_TAG_DESC));
            ad.setPrice(jsonAd.getDouble(Ad.JSON_TAG_PRICE));
            ad.setTime(Constants.DATE_FORMAT.parse(jsonAd.getString(Ad.JSON_TAG_TIME)));
            ad.setPlace(jsonAd.getString(Ad.JSON_TAG_PLACE));
            JSONArray jsonProps = jsonAd.getJSONArray(Ad.JSON_TAG_PROPS);
            Map<String, String> props = new HashMap<String, String>();
            for (int i = 0; i < jsonProps.length(); i++) {
                JSONObject jsonProp = jsonProps.getJSONObject(i);
                props.put(jsonProp.getString(Ad.JSON_TAG_KEY), jsonProp.getString(Ad.JSON_TAG_VALUE));
            }
            ad.setProperties(props);
            ad.setViews(jsonAd.getInt(Ad.JSON_TAG_VIEWS));
            ad.setFavorite(jsonAd.getBoolean(Ad.JSON_TAG_FAV));

            JSONObject jsonContact = jsonAd.getJSONObject(Ad.JSON_TAG_CONTACT);
            ad.setContactEmail(jsonContact.getString(Ad.JSON_TAG_EMAIL));
            ad.setContactName(jsonContact.getString(Ad.JSON_TAG_NAME));
            ad.setContactPhone(jsonContact.getString(Ad.JSON_TAG_PHONE));

            JSONArray jsonImages = jsonAd.getJSONArray(Ad.JSON_TAG_IMAGES);
            List<String> images = new ArrayList<String>();
            for (int i = 0; i < jsonImages.length(); i++) {
                images.add(jsonImages.getString(i));
            }
            ad.setImages(images);
        } catch (Exception e) {
            ad = null;
        }
        return ad;
    }
}
