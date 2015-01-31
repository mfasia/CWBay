package com.metafour.cwbay;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import com.metafour.cwbay.adapter.DrawerItemAdapter;
import com.metafour.cwbay.model.DrawerItem;
import com.metafour.cwbay.remote.WebConnection;
import com.metafour.cwbay.ui.CategoryActivity;
import com.metafour.cwbay.ui.ProductDetailsActivity;
import com.metafour.cwbay.ui.ProductImagesActivity;
import com.metafour.cwbay.ui.ProfileUpdateActivity;
import com.metafour.cwbay.ui.SignInActivity;
import com.metafour.cwbay.ui.SignUpActivity;
import com.metafour.cwbay.util.Constants;
import com.metafour.cwbay.util.Utility;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends AbstractCWBayActivity implements WebConnection.Callback {

    private ListView mainCategories;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
//        WebConnection.getInstance().connect(this, "http://192.168.20.102:8000");
        initialiseToolbar();

        mainCategories = (ListView) findViewById(R.id.mainCategories);
        List<DrawerItem> items = new ArrayList<DrawerItem>();
        Utility.populateCategories(this, items);
        mainCategories.setAdapter(new DrawerItemAdapter(this, R.layout.items_row, 0, items));

        mainCategories.setOnItemClickListener(new ListView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView parent, final View view,
                                    int position, long id) {
                final DrawerItem item = (DrawerItem) parent.getItemAtPosition(position);
                if (item.getType() == DrawerItem.ItemType.CATEGORY) {
                    CategoryActivity.idToShow = item.getId();
                    Intent i = new Intent(MainActivity.this, CategoryActivity.class);
                    i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(i);
                    Utility.nextWithAnimation(MainActivity.this);
                }

            }
        });
    }

    @Override
    public void onResponse(WebConnection.Response response) {
        Log.i(Constants.ACTIVITY_LOG_TAG, response.getStatus() + " " + response.getContent());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    public void openSignInPage(View v) {
        Log.i(Constants.ACTIVITY_LOG_TAG, "Going to open sign in page");
        openNextActivity(SignInActivity.class);
    }


    public void openSignUpPage(View v) {
        Log.i(Constants.ACTIVITY_LOG_TAG, "Going to open sign up page");
        openNextActivity(ProductImagesActivity.class);
    }

    public void openCategoryPage(View v) {
        Log.i(Constants.ACTIVITY_LOG_TAG, "Going to open category page");
        CategoryActivity.idToShow = 0;
        openNextActivity(CategoryActivity.class);
    }

    public void openCategoryGridPage(View v) {
        Log.i(Constants.ACTIVITY_LOG_TAG, "Going to open category grid page");
        openNextActivity(ProductDetailsActivity.class);
    }

    public void openLogOutPage(View v) {
        Log.i(Constants.ACTIVITY_LOG_TAG, "Going to logout from the app");
        openNextActivity(MainActivity.class);
    }

    public void openProfileUpdate(View v){
        Log.i(Constants.ACTIVITY_LOG_TAG, "Going to profile update");
        openNextActivity(ProfileUpdateActivity.class);
    }
}
