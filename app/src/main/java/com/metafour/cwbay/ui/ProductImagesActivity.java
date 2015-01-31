package com.metafour.cwbay.ui;

import android.app.Activity;
import android.os.Bundle;
import android.widget.LinearLayout;

import com.metafour.cwbay.R;
import com.metafour.cwbay.util.Utility;

/**
 * Created by Noor on 1/31/2015.
 */
public class ProductImagesActivity extends Activity {
    private LinearLayout myGallery;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.product_images);

        myGallery = (LinearLayout)findViewById(R.id.mygallery);

        if (getIntent().getExtras() != null) {
            Object images = getIntent().getExtras().get("images_array");
            /*if (images != null) {
                String[] arr = (String[]) images;
                for(String path : arr) {
                    myGallery.addView(Utility.getLayoutForImage(this, path, 0, 0));
                }
            }*/
        }
        for(String path : new String[]{"http://images03.olx-st.com/ui/26/04/78/1422570877_778619678_1.jpg","http://images03.olx-st.com/ui/26/68/11/1422593045_778826711_1.jpg","http://images03.olx-st.com/ui/26/04/78/1422570877_778619678_1.jpg","http://images03.olx-st.com/ui/26/68/11/1422593045_778826711_1.jpg","http://images03.olx-st.com/ui/26/04/78/1422570877_778619678_1.jpg","http://images03.olx-st.com/ui/26/68/11/1422593045_778826711_1.jpg"}) {
            myGallery.addView(Utility.getLayoutForImage(this, path, 0, 0));
        }
    }
}
