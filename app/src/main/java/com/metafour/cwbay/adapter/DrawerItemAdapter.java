package com.metafour.cwbay.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.metafour.cwbay.R;
import com.metafour.cwbay.model.Category;
import com.metafour.cwbay.model.DrawerItem;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by MURAD on 1/29/2015.
 */
public class DrawerItemAdapter extends ArrayAdapter<DrawerItem> {

    public DrawerItemAdapter(Context context, int resource, int textViewResourceId, List<DrawerItem> items) {
        super(context, resource, textViewResourceId, items);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        /*View view = super.getView(position, convertView, parent);
        TextView text1 = (TextView) view.findViewById(android.R.id.text1);
        text1.setText(getItem(position).getName());
        return view;*/
        if (convertView == null) {
            LayoutInflater mInflater = (LayoutInflater)
                    getContext().getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
            convertView = mInflater.inflate(R.layout.items_row, null);
        }

        ImageView imgIcon = (ImageView) convertView.findViewById(R.id.icon);
        TextView txtTitle = (TextView) convertView.findViewById(R.id.title);
        TextView txtCount = (TextView) convertView.findViewById(R.id.counter);

        DrawerItem item = getItem(position);
        if (item.getType() == null && item.getIcon() == 0) {
            imgIcon.setVisibility(View.INVISIBLE);
            txtTitle.setSelected(true);
        } else {
            imgIcon.setImageResource(item.getIcon());
        }
        txtTitle.setText(item.getTitle());

        // displaying Number
        // check whether it set visible or not
        if(item.getNumberVisibility()){
            txtCount.setText(item.getNumber());
        }else{
            txtCount.setVisibility(View.GONE);
        }

        return convertView;
    }

}
