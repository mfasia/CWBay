package com.metafour.cwbay;

import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBarDrawerToggle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.metafour.cwbay.adapter.DrawerItemAdapter;
import com.metafour.cwbay.adapter.SingleLineCategoryAdapter;
import com.metafour.cwbay.model.Category;
import com.metafour.cwbay.model.DrawerItem;
import com.metafour.cwbay.ui.CategoryActivity;
import com.metafour.cwbay.ui.SignInActivity;
import com.metafour.cwbay.ui.SignUpActivity;
import com.metafour.cwbay.util.Constants;
import com.metafour.cwbay.util.Utility;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Noor on 1/30/2015.
 */
public abstract class AbstractCWBayActivity extends ActionBarActivity {

    private DrawerLayout mDrawerLayout;
    private ListView mDrawerList;
    private ActionBarDrawerToggle mDrawerToggle;

    // used to store app title
    private CharSequence mTitle;

    public void initialiseToolbar() {
        mTitle = getTitle();
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mDrawerList = (ListView) findViewById(R.id.list_slider_menu);

        /*String[] categories = getResources().getStringArray(R.array.nav_drawer_items_category);
        TypedArray navMenuIcons = getResources().obtainTypedArray(R.array.nav_drawer_icons_category);*/
        List<DrawerItem> items = new ArrayList<DrawerItem>();
        DrawerItem item = new DrawerItem("Welcome", R.drawable.cwbay_logo);
        items.add(item);
        if (Utility.isLoggedIn()) {
            // TODO
        } else {
            items.add(new DrawerItem("Sign in", 0, 1001, DrawerItem.ItemType.PROFILE));
            items.add(new DrawerItem("Sign up", 0, 1002, DrawerItem.ItemType.PROFILE));
        }
        item = new DrawerItem("CATEGORY", 0);
        items.add(item);
        /*int i = 0;
        for (String cat : categories) {
            item = new DrawerItem(cat, navMenuIcons.getResourceId(i, -1), (101 + i++), DrawerItem.ItemType.CATEGORY);
            items.add(item);
        }
        // Recycle the typed array
        navMenuIcons.recycle();*/
        Utility.populateCategories(this, items);

        mDrawerList.setAdapter(new DrawerItemAdapter(this, R.layout.items_row, 0, items));

        mDrawerList.setOnItemClickListener(new ListView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView parent, final View view,
                                    int position, long id) {
                final DrawerItem item = (DrawerItem) parent.getItemAtPosition(position);
                if (item.getType() == DrawerItem.ItemType.CATEGORY) {
                    CategoryActivity.idToShow = item.getId();
                    Intent i = new Intent(AbstractCWBayActivity.this, CategoryActivity.class);
                    i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(i);
                    Utility.nextWithAnimation(AbstractCWBayActivity.this);
                } else if (item.getType() == DrawerItem.ItemType.PROFILE) {
                    if (item.getId() == 1001) {
                        openNextActivity(SignInActivity.class);
                    } else if (item.getId() == 1002) {
                        openNextActivity(SignUpActivity.class);
                    }
                }
                mDrawerLayout.closeDrawer(mDrawerList);
            }

        });

        // enabling action bar app icon and behaving it as toggle button
        android.support.v7.app.ActionBar ab = getSupportActionBar();
        ab.setDisplayHomeAsUpEnabled(true);
        ab.setHomeButtonEnabled(true);

        mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout, R.string.app_name, R.string.app_name){
            @Override
            public void onDrawerClosed(View view) {
                getSupportActionBar().setTitle(mTitle);
                // calling onPrepareOptionsMenu() to show action bar icons
                invalidateOptionsMenu();
            }

            @Override
            public void onDrawerOpened(View drawerView) {
                getSupportActionBar().setTitle(mTitle);
                // calling onPrepareOptionsMenu() to hide action bar icons
                invalidateOptionsMenu();
            }
        };
        mDrawerLayout.setDrawerListener(mDrawerToggle);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_root, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Pass the event to ActionBarDrawerToggle, if it returns
        // true, then it has handled the app icon touch event
        if (mDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }
        // Handle your other action bar items...

        return super.onOptionsItemSelected(item);

    }

    /* *
     * Called when invalidateOptionsMenu() is triggered
     */
    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        // if nav drawer is opened, hide the action items
        menu.findItem(R.id.action_settings).setVisible(mDrawerLayout.isDrawerOpen(mDrawerList) == Boolean.FALSE);
        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    public void setTitle(CharSequence title) {
        mTitle = title;
        getSupportActionBar().setTitle(mTitle);
    }

    /**
     * When using the ActionBarDrawerToggle, you must call it during
     * onPostCreate() and onConfigurationChanged()...
     */

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        // Sync the toggle state after onRestoreInstanceState has occurred.
        mDrawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        // Pass any configuration change to the drawer toggls
        mDrawerToggle.onConfigurationChanged(newConfig);
    }

    void openNextActivity(Class clz) {
        startActivity(new Intent(this, clz));
        Utility.nextWithAnimation(this);
    }
}
