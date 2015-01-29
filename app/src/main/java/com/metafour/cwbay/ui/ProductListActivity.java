package com.metafour.cwbay.ui;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.TextView;

import com.metafour.cwbay.R;
import com.metafour.cwbay.adapter.ProdcutListAdapter;
import com.metafour.cwbay.model.Category;
import com.metafour.cwbay.remote.WebAPI;
import com.metafour.cwbay.util.Constants;
import com.metafour.cwbay.util.Utility;

/**
 * Created by Noor on 1/29/2015.
 */
public class ProductListActivity extends ActionBarActivity {
    private Context context;
    private GridView prodGItems;
    public static Category category;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.product_list);

        this.context = getApplicationContext();
        this.prodGItems = (GridView) findViewById(R.id.prodGItems);

        this.prodGItems.setAdapter(new ProdcutListAdapter(this, category.getAds()));

//        category = getDummyCategory();
        prodGItems.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView parent, final View view, int position, long id) {
                showToast();
            }

        });
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
