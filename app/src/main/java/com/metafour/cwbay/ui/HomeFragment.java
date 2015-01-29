package com.metafour.cwbay.ui;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.metafour.cwbay.R;

/**
 * Created by MURAD on 1/29/2015.
 */
public class HomeFragment extends Fragment {
    public HomeFragment(){}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.main, container, false);

        return rootView;
    }
}
