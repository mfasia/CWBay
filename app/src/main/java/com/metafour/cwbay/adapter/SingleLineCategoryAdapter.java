package com.metafour.cwbay.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.metafour.cwbay.model.Category;
import com.metafour.cwbay.model.Subcategory;

import java.util.List;

/**
 * Created by Noor on 1/29/2015.
 */
public class SingleLineCategoryAdapter extends ArrayAdapter<Subcategory> {
    public SingleLineCategoryAdapter(Context context, int resource, int textViewResourceId, List<Subcategory> categories) {
        super(context, resource, textViewResourceId, categories);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = super.getView(position, convertView, parent);
        TextView text1 = (TextView) view.findViewById(android.R.id.text1);
        text1.setText(getItem(position).getName());
        return view;
    }
}
