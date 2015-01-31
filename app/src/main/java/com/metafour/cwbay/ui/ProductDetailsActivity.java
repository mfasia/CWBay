package com.metafour.cwbay.ui;

import android.app.ActionBar;
import android.content.Intent;
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
import android.widget.TextView;

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
    private String prodId = "test";
    private LinearLayout myGallery;
    private TextView detLoc;
    private TextView detPrice;
    private TextView detTitle;
    private TextView detDesc;
    private Ad ad;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.product_details);

        initialiseToolbar();

        myGallery = (LinearLayout)findViewById(R.id.mygallery);
        detLoc = (TextView) findViewById(R.id.detLoc);
        detPrice = (TextView) findViewById(R.id.detPrice);
        detTitle = (TextView) findViewById(R.id.detTitle);
        detDesc = (TextView) findViewById(R.id.detDesc);
        if (getIntent().getExtras() != null) {
            prodId = getIntent().getExtras().getString("prod_id");

        }

        WebAPI.adDetails(this, new WebAPI.Callback<Ad>() {
            @Override
            public void onFailed(String reason) {
                Log.i(Constants.ACTIVITY_LOG_TAG, "Reason = " + reason);
                Utility.showShortLengthToast(ProductDetailsActivity.this, reason);
            }

            @Override
            public void onSuccess(Ad ad1) {
                Log.i(Constants.ACTIVITY_LOG_TAG, "Category list received successfully. " + ad1.toString());
                showAdDetails(ad1);
                ad = ad1;
            }
        }, prodId);
        myGallery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(ProductDetailsActivity.this, ProductImagesActivity.class);
                String[] images = new String[ad.getImages().size()];
                ad.getImages().toArray(images);
                i.putExtra("images_array", images);
                ProductDetailsActivity.this.startActivity(i);
                Utility.nextWithAnimation(ProductDetailsActivity.this);
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Utility.backWithAnimation(this);
    }

    void showAdDetails(Ad ad) {
        for(String path : ad.getImages()) {
            myGallery.addView(Utility.getLayoutForImage(this, path, 220, 0));
        }
        detLoc.setText(ad.getPlace());
        detPrice.setText(String.format("%.2f", ad.getPrice()));
        detTitle.setText(ad.getTitle());
        detDesc.setText(ad.getDescription());
    }
}
