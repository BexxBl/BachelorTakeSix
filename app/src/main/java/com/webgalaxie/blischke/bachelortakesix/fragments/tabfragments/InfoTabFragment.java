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
public class InfoTabFragment extends Fragment {

    // Store instance variables
    private String title;
    private int page;

    // newInstance constructor for creating fragment with arguments
    public static InfoTabFragment newInstance(int page, String title) {
        InfoTabFragment fragmentFirst = new InfoTabFragment();
        Bundle args = new Bundle();
        args.putInt("someInt", page);
        args.putString("someTitle", title);
        fragmentFirst.setArguments(args);
        return fragmentFirst;
    }

    // Store instance variables based on arguments passed
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        page = getArguments().getInt("someInt", 0);
        title = getArguments().getString("someTitle");
    }

    // Inflate the view for the fragment based on layout XML
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_info_tab, container, false);
        //TextView tvLabel = (TextView) view.findViewById(R.id.tvLabel);
        //tvLabel.setText(page + " -- " + title);
        return view;
    }

    /*

    private DatabaseReference immoDataRef;
    private Bundle bundle;
    private FirebaseAuth auth;
    private FirebaseUser user;
    private String userid;


    private TextView immo_price,
            immo_type,
            immo_size,
            immo_rooms,
            immo_beds,
            immo_bathrooms,
            display_street_of_immo,
            display_housenumber_of_immo,
            display_postcode_of_immo,
            display_city_of_immo,
            display_country_of_immo,
            display_description_of_immo,
            display_location_info_of_immo,
            display_aussattung_info_of_immo;

    private ImageView immo_first_pic;


    public InfoTabFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_info_tab, container, false);


        // get references to the view elements
        immo_price = v.findViewById(R.id.immo_price);
        immo_type = v.findViewById(R.id.immo_type);
        immo_size = v.findViewById(R.id.immo_size);
        immo_rooms = v.findViewById(R.id.immo_rooms);
        immo_beds = v.findViewById(R.id.immo_beds);
        immo_bathrooms = v.findViewById(R.id.immo_bathrooms);
        display_street_of_immo = v.findViewById(R.id.display_street_of_immo);
        display_housenumber_of_immo = v.findViewById(R.id.display_housenumber_of_immo);
        display_postcode_of_immo = v.findViewById(R.id.display_postcode_of_immo);
        display_city_of_immo = v.findViewById(R.id.display_city_of_immo);
        display_country_of_immo = v.findViewById(R.id.display_country_of_immo);
        display_description_of_immo = v.findViewById(R.id.display_description_of_immo);
        display_location_info_of_immo = v.findViewById(R.id.display_location_info_of_immo);
        display_aussattung_info_of_immo = v.findViewById(R.id.display_aussattung_info_of_immo);


        // get an instance of the FirebaseAuth
        auth = FirebaseAuth.getInstance();

        // get the current user
        user = auth.getCurrentUser();

        // get the id of the current user
        userid = user.getUid();

        // get the expose id from the fragment
        bundle = getArguments();
        String immoID = bundle.getString("exposeID");

        // get an reference to the database
        immoDataRef = FirebaseDatabase.getInstance().getReference().child(Constants.DATABASE_PATH_IMMOBILIEN);
        immoDataRef = immoDataRef.child(userid).child(immoID);

        // get title of Immo from database

        immoDataRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                immo_price.setText(String.valueOf(dataSnapshot.child("immo_price").getValue()));
                immo_type.setText(String.valueOf(dataSnapshot.child("immo_type").getValue()));
                immo_size.setText(String.valueOf(dataSnapshot.child("immo_size").getValue()));
                immo_rooms.setText(String.valueOf(dataSnapshot.child("immo_rooms").getValue()));
                immo_beds.setText(String.valueOf(dataSnapshot.child("immo_beds").getValue()));
                immo_bathrooms.setText(String.valueOf(dataSnapshot.child("immo_bathrooms").getValue()));
                display_street_of_immo.setText(String.valueOf(dataSnapshot.child("immo_street").getValue()));
                display_housenumber_of_immo.setText(String.valueOf(dataSnapshot.child("immo_housenumber").getValue()));
                display_postcode_of_immo.setText(String.valueOf(dataSnapshot.child("immo_postcode").getValue()));
                display_city_of_immo.setText(String.valueOf(dataSnapshot.child("immo_city").getValue()));
                display_country_of_immo.setText(String.valueOf(dataSnapshot.child("immo_country").getValue()));
                display_description_of_immo.setText(String.valueOf(dataSnapshot.child("immo_desc_text").getValue()));
                display_location_info_of_immo.setText(String.valueOf(dataSnapshot.child("immo_location_text").getValue()));
                display_aussattung_info_of_immo.setText(String.valueOf(dataSnapshot.child("immo_ausstattung_text").getValue()));

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


        return v;
    }
    */
}
