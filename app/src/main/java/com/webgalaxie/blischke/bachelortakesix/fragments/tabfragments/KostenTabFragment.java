package com.webgalaxie.blischke.bachelortakesix.fragments.tabfragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.webgalaxie.blischke.bachelortakesix.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class KostenTabFragment extends Fragment {
    private static final String TAG = "KOSTEN_TAB";


    public KostenTabFragment() {
        // Required empty public constructor
    }


    // Inflate the view for the fragment based on layout XML
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_kosten_tab, container, false);

        return view;
    }
}
