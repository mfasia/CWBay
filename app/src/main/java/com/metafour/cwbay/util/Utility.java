package com.metafour.cwbay.util;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.metafour.cwbay.R;

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
}
