package com.webgalaxie.blischke.bachelortakesix.fragments.tabfragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.webgalaxie.blischke.bachelortakesix.R;


public class ContactTabFragment extends Fragment {

    private static final String TAG = "CONTACT_TAB";

// textviews

    private TextView contact_firm_name, contact_firm_website, contact_firm_person,
            contact_firm_street_housenumber, contact_firm_plz_city, contact_firm_state_country,
            contact_firm_phonenumber, contact_firm_email;

    public ContactTabFragment() {
        // Required empty public constructor

    }


    // Inflate the view for the fragment based on layout XML
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_contact_tab, container, false);


        // find view by id
        contact_firm_name = view.findViewById(R.id.contact_firm_name);
        contact_firm_website = view.findViewById(R.id.contact_firm_website);
        contact_firm_person = view.findViewById(R.id.contact_firm_person);
        contact_firm_street_housenumber = view.findViewById(R.id.contact_firm_street_housenumber);
        contact_firm_plz_city = view.findViewById(R.id.contact_firm_plz_city);
        contact_firm_state_country = view.findViewById(R.id.contact_firm_state_country);
        contact_firm_phonenumber = view.findViewById(R.id.contact_firm_phonenumber);
        contact_firm_email = view.findViewById(R.id.contact_firm_email);


        return view;
    }

}
