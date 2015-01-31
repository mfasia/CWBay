package com.metafour.cwbay.util;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.metafour.cwbay.R;
import com.metafour.cwbay.model.Category;
import com.metafour.cwbay.process.ImageDownloadTask;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Noor on 1/22/2015.
 */
public class Utility {

    public static void showShortLengthToast(Context context, String message) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
    }

    public static void showLongLengthToast(Context context, String message) {
        Toast.makeText(context, message, Toast.LENGTH_LONG).show();
    }

    /**
     * Shows progress bar and disables button
     *
     * @param progressBar Progress bar to hide
     * @param button Button to enable. Ignores if reference is null.
     */
    public static void showProgressBarAndDisableButton (ProgressBar progressBar, Button button) {
        progressBar.setVisibility(View.VISIBLE);
        if (button != null) button.setEnabled(false);
    }

    /**
     * Hides progress bar and enables button
     *
     * @param progressBar Progress bar to hide
     * @param button Button to enable. Ignores if reference is null.
     */
    public static void hideProgressBarAndEnableButton (ProgressBar progressBar, Button button) {
        progressBar.setVisibility(View.INVISIBLE);
        if (button != null) button.setEnabled(true);
    }

    /**
     * Go next page with app default animation.
     *
     * @param activity
     */
    public static void nextWithAnimation(Activity activity) {
        activity.overridePendingTransition(R.animator.slight_in_right, R.animator.slight_out_left);
    }

    /**
     * Go back page with app default animation.
     *
     * @param activity
     */
    public static void backWithAnimation(Activity activity) {
        activity.overridePendingTransition(R.animator.slight_in_left, R.animator.slight_out_right);
    }

    public static SharedPreferences getPref(Context context) {
        return context.getSharedPreferences(Constants.PREFERENCE_FILE_KEY, Context.MODE_PRIVATE);
    }

    public static Category getCategory(int id) {
        if (id == 0) {
            Category cat = new Category();
            cat.setName("Test");
            List<Category> children = new ArrayList<Category>();
            for (int i = 0; i < 24; i++) {
                Category child = new Category();
                child.setId(i + 1);
                child.setName("Test " + child.getId());
                child.setHasChildren(true);
                children.add(child);
            }
            cat.setChildren(children);
            return cat;
        }
        return  null;
    }

    public static boolean isLoggedIn() {
        return false;
    }

    /**
     *
     * @param activity
     * @param path
     * @param height
     * @param width
     * @return
     */
    public static View getLayoutForImage(Activity activity, String path, int height, int width){

        LinearLayout layout = new LinearLayout(activity.getApplicationContext());
        layout.setLayoutParams(new LinearLayout.LayoutParams(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT));
        layout.setGravity(Gravity.CENTER);

        ImageView imageView = new ImageView(activity.getApplicationContext());

        if (height == 0 || width == 0) {
            DisplayMetrics metrics = new DisplayMetrics();
            activity.getWindowManager().getDefaultDisplay().getMetrics(metrics);

            height = height == 0 ? metrics.heightPixels : height;
            width = width == 0 ? metrics.widthPixels : width;
        }
        //Use RelativeLayout.LayoutParams if your parent is a RelativeLayout
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                width, height);
        imageView.setLayoutParams(params);
        imageView.setScaleType(ImageView.ScaleType.FIT_XY);
        new ImageDownloadTask(activity, path, imageView).execute();

        layout.addView(imageView);
        return layout;
    }

    public static boolean isConnectingToInternet(Context context){
        ConnectivityManager connectivity = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivity != null){
            NetworkInfo[] info = connectivity.getAllNetworkInfo();
            if (info != null) {
                for (int i = 0; i < info.length; i++) {
                    if (info[i].getState() == NetworkInfo.State.CONNECTED){
                        return true;
                    }
                }
            }
        }
        return false;
    }
    /**
     * Function to display simple Alert Dialog for internet connection
     *
     * @param context - application context
     * @param title - alert dialog title
     * @param message - alert message
     * @param status - success/failure (used to set icon)
     * */
    public void showAlertInternet(Context context, String title, String message, Boolean status) {
        AlertDialog alertDialog = new AlertDialog.Builder(context).create();

        // Setting Dialog Title
        alertDialog.setTitle(title);

        // Setting Dialog Message
        alertDialog.setMessage(message);

        // Setting alert dialog icon
        alertDialog.setIcon((status) ? R.drawable.con_ok : R.drawable.con_fail);

        // Setting OK Button
        alertDialog.setButton("OK", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
            }
        });

        // Showing Alert Message
        alertDialog.show();
    }
}
