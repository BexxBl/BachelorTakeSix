package com.webgalaxie.blischke.bachelortakesix.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.webgalaxie.blischke.bachelortakesix.R;
import com.webgalaxie.blischke.bachelortakesix.other.Constants;

/**
 * Created by Bexx on 09.01.18.
 */

public class ShowExposeFragment extends Fragment {

    FragmentPagerAdapter adapterViewPager;
    private DatabaseReference immoDataRef;
    private Bundle oldbundle, newbundle;
    private FirebaseAuth auth;
    private FirebaseUser user;
    private String userid;
    private Toolbar toolbar;

    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // inflating the view
        View v = inflater.inflate(R.layout.fragment_show_expose, container, false);

        // setup the Toolbar
        toolbar = v.findViewById(R.id.toolbar);


        // get the expose id from the fragment
        oldbundle = getArguments();
        String immoID = oldbundle.getString("exposeID");

        // get an instance of the FirebaseAuth
        auth = FirebaseAuth.getInstance();

        // get the current user
        user = auth.getCurrentUser();

        // get the id of the current user
        userid = user.getUid();

        // get an reference to the database
        immoDataRef = FirebaseDatabase.getInstance().getReference().child(Constants.DATABASE_PATH_IMMOBILIEN);
        immoDataRef = immoDataRef.child(userid).child(immoID);

        // get title of Immo from database

        immoDataRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // get the string from the database
                String immo_Name = String.valueOf(dataSnapshot.child("string_immo_name").getValue()).toString();

                // set immo name as toolbar title
                toolbar.setTitle(immo_Name);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


        // return the view
        return v;
    }



}
