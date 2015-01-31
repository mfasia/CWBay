package com.metafour.cwbay.process;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.ImageView;

import com.metafour.cwbay.util.Constants;
import com.metafour.cwbay.util.Utility;

import java.io.InputStream;
import java.net.URL;

/**
 * Created by Noor on 1/30/2015.
 */
public class ImageDownloadTask extends AsyncTask<String, Void, Bitmap> {

    private final String imageURL;
    private final ImageView imageView;
    private final Context context;

    /**
     * Constructor
     * @param context
     * @param imageURL
     * @param imageView
     */
    public ImageDownloadTask(final Context context, final String imageURL, final ImageView imageView) {
        this.imageURL = imageURL;
        this.imageView = imageView;
        this.context = context;
    }

    @Override
    protected Bitmap doInBackground(final String... args) {
        Bitmap bitmap = null;

        try {

            final InputStream in = new URL(imageURL).openStream();
            bitmap = BitmapFactory.decodeStream(in);
            in.close();

        } catch (final Exception e) {
            Log.e("Error", e.getMessage());
        }
        return bitmap;
    }


    @Override
    protected void onPostExecute(final Bitmap result) {
        if(result != null) {
            int iw = result.getWidth();
            int ih = result.getHeight();

            int vw = imageView.getWidth();
            int vh = imageView.getHeight();

            if (iw == vw && ih == vh) { // no need to scale
                imageView.setImageBitmap(result);
            } else if (vh > 220) {
                if (iw < vw && ih < vh) {
                    double r = (double) vh / ih;
                    iw = (int) (r * iw);
                    ih = vh;
                }

                if (ih > vh) {
                    double r = (double) vh / ih;
                    iw = (int) (r * iw);
                    ih = vh;
                }

                if (iw > vw) {
                    double r = (double) vw / iw;
                    ih = (int) (r * ih);
                    iw = vw;
                }

                Bitmap scaledResult = Bitmap.createScaledBitmap(result, iw, ih, false);
                Bitmap bg = Bitmap.createBitmap(vw, vh, Bitmap.Config.ARGB_8888);
                Canvas canvas = new Canvas(bg);
                canvas.drawBitmap(scaledResult, (vw - iw) / 2, (vh - ih) / 2, null);
                imageView.setImageBitmap(bg);
            } else {
                imageView.setImageBitmap(result);
            }



        }
    }
}
