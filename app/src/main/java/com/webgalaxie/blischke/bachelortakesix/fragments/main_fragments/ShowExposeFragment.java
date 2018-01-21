package com.webgalaxie.blischke.bachelortakesix.fragments.main_fragments;

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

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.webgalaxie.blischke.bachelortakesix.R;
import com.webgalaxie.blischke.bachelortakesix.adapters.TabPageAdapter;
import com.webgalaxie.blischke.bachelortakesix.fragments.tabfragments.ContactTabFragment;
import com.webgalaxie.blischke.bachelortakesix.fragments.tabfragments.InfoTabFragment;
import com.webgalaxie.blischke.bachelortakesix.fragments.tabfragments.KostenTabFragment;
import com.webgalaxie.blischke.bachelortakesix.fragments.tabfragments.PDFTabFragment;
import com.webgalaxie.blischke.bachelortakesix.fragments.tabfragments.PictureTabFragment;
import com.webgalaxie.blischke.bachelortakesix.other.Constants;

/**
 * Created by Bexx on 09.01.18.
 */

public class ShowExposeFragment extends Fragment {

    private static final String TAG = "MAIN_FRAGMENT";
    FragmentPagerAdapter adapterViewPager;
    ViewPager viewPager;
    TabLayout tablayout;
    int[] tabIcons = {
            R.drawable.ic_info_white_24dp,
            R.drawable.ic_euro_symbol_white_24dp,
            R.drawable.ic_person_white_24dp,
            R.drawable.ic_photo_white_24dp,
            R.drawable.ic_picture_as_pdf_white_24dp
    };
    private TabPageAdapter tabPageAdapter;
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


        // setup the view pager with the tabPageAdapter
        viewPager = view.findViewById(R.id.viewpager);
        setupViewPager(viewPager);

        // create tablayout and set the id to tabs
        tablayout = view.findViewById(R.id.tabs);
        tablayout.setupWithViewPager(viewPager);

        setupTabIcons();


        // return the view
        return view;
    }


    public void setupTabIcons() {
        tablayout.getTabAt(0).setIcon(tabIcons[0]);
        tablayout.getTabAt(1).setIcon(tabIcons[1]);
        tablayout.getTabAt(2).setIcon(tabIcons[2]);
        tablayout.getTabAt(3).setIcon(tabIcons[3]);
        tablayout.getTabAt(4).setIcon(tabIcons[4]);
    }


    public void setupViewPager(ViewPager viewPager) {

        // get the expose id from the fragment
        oldbundle = getArguments();
        String immoID = oldbundle.getString("exposeID");

        newbundle = new Bundle();
        newbundle.putString("exposeID", immoID);

        // get an adapter
        TabPageAdapter adapter = new TabPageAdapter(getFragmentManager());

        Fragment infoTabFragment = new InfoTabFragment();
        Fragment kostenTabFragment = new KostenTabFragment();
        Fragment contactTabFragment = new ContactTabFragment();
        Fragment pictureTabFragment = new PictureTabFragment();
        Fragment pdfTabFragment = new PDFTabFragment();


        // Add the immoID as an Argument
        infoTabFragment.setArguments(newbundle);
        kostenTabFragment.setArguments(newbundle);
        pictureTabFragment.setArguments(newbundle);
        pdfTabFragment.setArguments(newbundle);
        contactTabFragment.setArguments(newbundle);

        // add the fragements to the adapter
        adapter.addFragment(infoTabFragment, "");
        adapter.addFragment(kostenTabFragment, "");
        adapter.addFragment(contactTabFragment, "");
        adapter.addFragment(pictureTabFragment, "");
        adapter.addFragment(pdfTabFragment, "");

        // set the adapter to the view pager
        viewPager.setAdapter(adapter);

    }


}
