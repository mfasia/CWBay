package com.metafour.cwbay.ui;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.metafour.cwbay.AbstractCWBayActivity;
import com.metafour.cwbay.R;
import com.metafour.cwbay.adapter.SingleLineCategoryAdapter;
import com.metafour.cwbay.model.Ad;
import com.metafour.cwbay.model.Category;
import com.metafour.cwbay.model.Subcategory;
import com.metafour.cwbay.remote.WebAPI;
import com.metafour.cwbay.util.Constants;
import com.metafour.cwbay.util.Utility;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Noor on 1/27/2015.
 */
public class CategoryActivity extends AbstractCWBayActivity {
    public static int idToShow;
    private Context context;
    private ListView catLItems;
    private TextView catLPath;
    private Category category;
    public static String path = ">>";
    private String prevPath = ">>";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.category);

        initialiseToolbar();

        this.context = getApplicationContext();
        this.catLItems = (ListView) findViewById(R.id.catLItems);
        this.catLPath = (TextView) findViewById(R.id.catLPath);

        WebAPI.categoryView(this.context, new WebAPI.Callback<Category>(){
            @Override
            public void onFailed(String reason) {
                Log.i(Constants.ACTIVITY_LOG_TAG, "Reason = " + reason);
                Utility.showShortLengthToast(context, reason);
            }
            @Override
            public void onSuccess(Category category1) {
                category = category1;
                showCategories();
            }
        }, idToShow + "");

//        category = getDummyCategory();
        catLItems.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView parent, final View view,
                                    int position, long id) {
                final Subcategory category = (Subcategory) parent.getItemAtPosition(position);
                Log.i(Constants.ACTIVITY_LOG_TAG, "Going to open category page for id = " + idToShow);
                Intent i = new Intent(CategoryActivity.this, ProductListActivity.class);
                i.putExtra("cat_id", category.getId());
                startActivity(i);
                Utility.nextWithAnimation(CategoryActivity.this);
            }

        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Utility.backWithAnimation(this);
        path = prevPath;
    }

    private void showCategories() {
        catLItems.setAdapter(new SingleLineCategoryAdapter(this, android.R.layout.simple_list_item_1, android.R.id.text1, category.getSubcats()));

        if (idToShow > 0) {
            catLPath.setText(prevPath + category.getName());
        } else {
            catLPath.setText(prevPath);
        }
    }
}
