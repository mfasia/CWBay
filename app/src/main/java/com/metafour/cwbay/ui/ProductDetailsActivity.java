package com.metafour.cwbay.ui;

import android.app.ActionBar;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Gallery;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.metafour.cwbay.AbstractCWBayActivity;
import com.metafour.cwbay.R;
import com.metafour.cwbay.model.Ad;
import com.metafour.cwbay.model.Category;
import com.metafour.cwbay.process.ImageDownloadTask;
import com.metafour.cwbay.remote.WebAPI;
import com.metafour.cwbay.util.Constants;
import com.metafour.cwbay.util.Utility;

import java.io.File;

/**
 * Created by Noor on 1/31/2015.
 */
public class ProductDetailsActivity extends AbstractCWBayActivity {
    public static String prodId;
    private LinearLayout myGallery;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.product_details);

        initialiseToolbar();

        myGallery = (LinearLayout)findViewById(R.id.mygallery);

        WebAPI.adDetails(this, new WebAPI.Callback<Ad>() {
            @Override
            public void onFailed(String reason) {
                Log.i(Constants.ACTIVITY_LOG_TAG, "Reason = " + reason);
                Utility.showShortLengthToast(context, reason);
            }

            @Override
            public void onSuccess(Ad ad) {
                Log.i(Constants.ACTIVITY_LOG_TAG, "Category list received successfully. " + ad.toString());
                showAdDetails(ad);
            }
        }, prodId);
    }

    void showAdDetails(Ad ad) {
        for(String path : ad.getImages()) {
            myGallery.addView(Utility.getLayoutForImage(this, path, 220, 0));
        }
    }
}
