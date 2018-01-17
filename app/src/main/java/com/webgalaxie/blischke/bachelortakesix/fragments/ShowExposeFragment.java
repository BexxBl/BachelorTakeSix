package com.webgalaxie.blischke.bachelortakesix.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.webgalaxie.blischke.bachelortakesix.R;
import com.webgalaxie.blischke.bachelortakesix.adapters.SectionsPageAdapter;
import com.webgalaxie.blischke.bachelortakesix.fragments.tabfragments.AttachmentTabFragment;
import com.webgalaxie.blischke.bachelortakesix.fragments.tabfragments.ContactTabFragment;
import com.webgalaxie.blischke.bachelortakesix.fragments.tabfragments.InfoTabFragment;
import com.webgalaxie.blischke.bachelortakesix.fragments.tabfragments.KostenTabFragment;
import com.webgalaxie.blischke.bachelortakesix.other.Constants;

/**
 * Created by Bexx on 09.01.18.
 */

public class ShowExposeFragment extends Fragment {

    private static final String TAG = "MAIN_FRAGMENT";
    FragmentPagerAdapter adapterViewPager;
    ViewPager viewPager;
    TabLayout tablayout;
    private SectionsPageAdapter sectionsPageAdapter;
    private DatabaseReference immoDataRef, contactDataRef;
    private Bundle oldbundle, newbundle;
    private FirebaseAuth auth;
    private FirebaseUser user;
    private String userid;
    private Toolbar toolbar;


    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        // inflating the view
        View view = inflater.inflate(R.layout.fragment_show_expose, container, false);

        // setup the Toolbar
        toolbar = view.findViewById(R.id.toolbar);


        // get the expose id from the fragment
        oldbundle = getArguments();
        String immoID = oldbundle.getString("exposeID");
        Toast.makeText(getContext(), immoID, Toast.LENGTH_SHORT).show();

        // get an instance of the FirebaseAuth
        auth = FirebaseAuth.getInstance();

        // get the current user
        user = auth.getCurrentUser();

        // get the id of the current user
        userid = user.getUid();

        // get an reference to the database
        immoDataRef = FirebaseDatabase.getInstance().getReference().child(Constants.DATABASE_PATH_IMMOBILIEN);
        immoDataRef = immoDataRef.child(userid).child(immoID);

        contactDataRef = FirebaseDatabase.getInstance().getReference().child(Constants.DATABASE_PATH_CONTACTS);
        contactDataRef = contactDataRef.child(userid).child(immoID);



        // get title of Immo from database

        immoDataRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // get the string from the database
                String immo_Name = String.valueOf(dataSnapshot.child("immo_name").getValue()).toString();
                String immo_Type = String.valueOf(dataSnapshot.child("immo_art").getValue()).toString();
                String immo_vermarktung = String.valueOf(dataSnapshot.child("immo_vermarktung").getValue()).toString();

                // set immo name as toolbar title
                toolbar.setTitle(immo_Name);
                toolbar.setSubtitle("(" + immo_Type + " zur " + immo_vermarktung + ")");
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });



        // setup the view pager with the sectionsPageAdapter
        viewPager = view.findViewById(R.id.viewpager);
        setupViewPager(viewPager);

        // create tablayout and set the id to tabs
        tablayout = view.findViewById(R.id.tabs);
        tablayout.setupWithViewPager(viewPager);

        // return the view
        return view;
    }


    private void setupViewPager(ViewPager viewPager) {

        // get the expose id from the fragment
        oldbundle = getArguments();
        String immoID = oldbundle.getString("exposeID");

        newbundle = new Bundle();
        newbundle.putString("exposeID", immoID);

        // get an adapter
        SectionsPageAdapter adapter = new SectionsPageAdapter(getFragmentManager());

        Fragment infoTabFragment = new InfoTabFragment();
        Fragment kostenTabFragment = new KostenTabFragment();
        Fragment contactTabFragment = new ContactTabFragment();
        Fragment attachmentTabFragment = new AttachmentTabFragment();

        // Add the immoID as an Argument
        infoTabFragment.setArguments(newbundle);
        kostenTabFragment.setArguments(newbundle);
        attachmentTabFragment.setArguments(newbundle);
        contactTabFragment.setArguments(newbundle);

        // add the fragements to the adapter
        adapter.addFragment(infoTabFragment, "Infos");
        adapter.addFragment(kostenTabFragment, "Kosten");
        adapter.addFragment(contactTabFragment, "Kontakt");
        adapter.addFragment(attachmentTabFragment, "Dokumente");

        // set the adapter to the view pager
        viewPager.setAdapter(adapter);
    }


}
