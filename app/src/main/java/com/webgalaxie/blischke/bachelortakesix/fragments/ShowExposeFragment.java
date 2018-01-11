package com.webgalaxie.blischke.bachelortakesix.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
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
                String immo_Name = String.valueOf(dataSnapshot.child("immo_name").getValue()).toString();

                // set immo name as toolbar title
                toolbar.setTitle(immo_Name);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        sectionsPageAdapter = new SectionsPageAdapter(getFragmentManager());

        // setup the view pager with the sectionsPageAdapter
        viewPager = v.findViewById(R.id.viewpager);
        setupViewPager(viewPager);

        // create tablayout and set the id to tabs
        tablayout = v.findViewById(R.id.tabs);
        tablayout.setupWithViewPager(viewPager);

        // return the view
        return v;
    }


    private void setupViewPager(ViewPager viewPager) {

        // get the expose id from the fragment
        oldbundle = getArguments();
        String immoID = oldbundle.getString("exposeID");

        // get an adapter
        SectionsPageAdapter adapter = new SectionsPageAdapter(getFragmentManager());

        // add the fragements to the adapter
        adapter.addFragment(new InfoTabFragment(), "Infos", immoID);
        adapter.addFragment(new KostenTabFragment(), "Kosten", immoID);
        adapter.addFragment(new ContactTabFragment(), "Kontakt", immoID);
        adapter.addFragment(new AttachmentTabFragment(), "Dokumente", immoID);

        // set the adapter to the view pager
        viewPager.setAdapter(adapter);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.showexposemenu, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        final FragmentManager manager = getFragmentManager();

        switch (item.getItemId()) {
            case R.id.edit_expose:
                Toast.makeText(getContext(), "Expose bearbeiten geklickt.", Toast.LENGTH_SHORT).show();

                /*
                Fragment editExpose = new EditExposeFragment();
                manager.beginTransaction().replace(R.id.content_frame,editExpose).commit();
                '*/
                break;
            case R.id.delete_expose:
                Toast.makeText(getContext(), "Expose wurde gelöscht.", Toast.LENGTH_SHORT).show();
                /*
                final DatabaseReference immoDatabase = FirebaseDatabase.getInstance().getReference().child(Constants.DATABASE_PATH_IMMOBILIEN).child(userid);
                immoDatabase.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        //immoDatabase.child("immoID").removeValue();
                        Fragment showAllExpose = new ShowExposeFragment();
                        manager.beginTransaction().replace(R.id.content_frame, showAllExpose).commit();
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
                */
                break;

        }
        return super.onOptionsItemSelected(item);
    }
}
