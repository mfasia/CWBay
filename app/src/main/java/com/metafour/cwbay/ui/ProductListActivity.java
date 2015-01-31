package com.metafour.cwbay.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.LinearLayout;

import com.metafour.cwbay.AbstractCWBayActivity;
import com.metafour.cwbay.R;
import com.metafour.cwbay.adapter.ProdcutListAdapter;
import com.metafour.cwbay.model.Ad;
import com.metafour.cwbay.model.Category;
import com.metafour.cwbay.remote.WebAPI;
import com.metafour.cwbay.util.Constants;
import com.metafour.cwbay.util.Utility;

import java.util.List;

/**
 * Created by Noor on 1/29/2015.
 */
public class ProductListActivity extends AbstractCWBayActivity {
    public static int catId;
    private Context context;
    private GridView prodGItems;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.product_list);

        initialiseToolbar();

        this.context = getApplicationContext();
        this.prodGItems = (GridView) findViewById(R.id.prodGItems);

        WebAPI.categoryView(this.context, new WebAPI.Callback<Category>(){
            @Override
            public void onFailed(String reason) {
                Log.i(Constants.ACTIVITY_LOG_TAG, "Reason = " + reason);
                Utility.showShortLengthToast(context, reason);
            }
            @Override
            public void onSuccess(Category category) {
                Log.i(Constants.ACTIVITY_LOG_TAG, "Category list received successfully. " + category.toString());
                showAds(category);
            }
        }, catId);

        prodGItems.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView parent, final View view, int position, long id) {
                final Ad ad = (Ad) parent.getItemAtPosition(position);
                Intent i = new Intent(ProductListActivity.this, ProductDetailsActivity.class);
                i.putExtra("prod_id", ad.getId());
                Log.i(Constants.ACTIVITY_LOG_TAG, ad.getId());
                ProductListActivity.this.startActivity(i);
                Utility.nextWithAnimation(ProductListActivity.this);
            }

        });
    }

    private void showAds(Category category) {
        List<Ad> ads = category.getAds();
        while (ads.size() < 5) {
            ads.add(ads.get(0));
        }
        prodGItems.setAdapter(new ProdcutListAdapter(this, ads));
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Utility.backWithAnimation(this);
    }

    private void showToast() {
        Utility.showShortLengthToast(this, "Product list item has been clicked. Next activity not implemented yet!!!!");
    }
}
