package com.webgalaxie.blischke.bachelortakesix.fragments;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.webgalaxie.blischke.bachelortakesix.R;


/**
 * A simple {@link Fragment} subclass.
 */
public class ShowExposeFragment extends Fragment {


    Toolbar toolbar;

    DatabaseReference databaseReference;
    FirebaseAuth auth;

    Bundle bundle;

    String userid;


    TextView immo_price,
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

    public ShowExposeFragment() {
        // Required empty public constructor
    }

    // setup options menu for the edit and delete buttons
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);


    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // get an firebaseauth instance
        auth = FirebaseAuth.getInstance();

        // get the userid of the current user
        userid = auth.getCurrentUser().getUid();

        // get the arguments
        bundle = getArguments();
        if (bundle != null) {
            String immoID = bundle.getString("exposeID");
        } else {
            Toast.makeText(getContext(), "Keine Argumente gesendet", Toast.LENGTH_SHORT).show();
        }

        //setup the view
        View v = inflater.inflate(R.layout.fragment_show_expose, container, false);

        // get the view elements
        toolbar = v.findViewById(R.id.toolbar);

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

        // get the expose id
        final String immoID = bundle.getString("exposeID");

        // get an database reference of the viewed immo object
        databaseReference = FirebaseDatabase.getInstance().getReference().child("Immobilien").child(userid).child(immoID);

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // get the relevant values form the database
                toolbar.setTitle(String.valueOf(dataSnapshot.child("immo_name").getValue()));
                immo_price.setText(String.valueOf(dataSnapshot.child("immo_price_brutto").getValue() + " €"));
                immo_type.setText(String.valueOf(dataSnapshot.child("immo_type").getValue()));
                immo_size.setText(String.valueOf(dataSnapshot.child("immo_size").getValue()));
                immo_rooms.setText(String.valueOf(dataSnapshot.child("immo_number_of_rooms").getValue()));
                immo_beds.setText(String.valueOf(dataSnapshot.child("immo_number_of_sleeping_rooms").getValue()));
                immo_bathrooms.setText(String.valueOf(dataSnapshot.child("immo_number_of_bathrooms").getValue()));
                display_street_of_immo.setText(String.valueOf(dataSnapshot.child("immo_street").getValue()));
                display_housenumber_of_immo.setText(String.valueOf(dataSnapshot.child("immo_housenumber:").getValue()));
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


        //return the view element
        return v;

    }

    private void editExpose() {
        // get the expose id  and change the fragment
        final String immoID = bundle.getString("exposeID");

        Fragment editExposeFragment = new EditExposeFragment();
        Bundle newbundle= new Bundle();
        newbundle.putString("exposeID", immoID );
        editExposeFragment.setArguments(newbundle);
        FragmentManager fragmentManager = getFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.content_frame, editExposeFragment).commit();
    }

    private void deleteExpose() {

        // get the expose id
        final String immoID = bundle.getString("exposeID");

        // get an database reference of the viewed immo object
        databaseReference = FirebaseDatabase.getInstance().getReference().child("Immobilien").child(userid).child(immoID);

        // remove the value from the database
        databaseReference.removeValue();

        // show toast
        Toast.makeText(getContext(), "Immobilie wurde entfernt", Toast.LENGTH_LONG).show();

        // change the Fragment
        Fragment deleteImmoSuccessFragment = new DeleteImmoSuccessFragment();
        FragmentManager fragmentManager = getFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.content_frame, deleteImmoSuccessFragment).commit();


    }


    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        // inflate the optionsmenu
        inflater.inflate(R.menu.showexposemenu, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.delete_expose:
                //Clicking on Save button will save Expose to database
                deleteExpose();

                return true;
            case R.id.edit_expose:
                //Cancel the Creation of Expose here. All Data will be lost.
                editExpose();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }


}
