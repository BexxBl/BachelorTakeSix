package com.webgalaxie.blischke.bachelortakesix.fragments.main_fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.daimajia.swipe.util.Attributes;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.webgalaxie.blischke.bachelortakesix.R;
import com.webgalaxie.blischke.bachelortakesix.adapters.ImmobilienSwipeRecyclerViewAdapter;
import com.webgalaxie.blischke.bachelortakesix.models.Immobilie;
import com.webgalaxie.blischke.bachelortakesix.other.Constants;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class ShowAllFragment extends Fragment {

    FirebaseUser user;
    String user_id;

    Bundle bundle, newBundle;
    String immoID;

    // Button to add Attachments to the Expose
    ArrayList<Immobilie> immobilien;
    RelativeLayout relativLayout;
    LinearLayout no_Expose_found_layout, listviewlayout;
    Button add_new_expose_when_no_are_found;
    private DatabaseReference immoDataRef, pictureDataRef, contactDataRef, attachmentImagesDataRef, attachmentPDFDataRef;
    private RecyclerView recycler_view;


    public ShowAllFragment() {
        // Required empty public constructor
    }


    // Inflate the view for the fragment based on layout XML
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.show_all_expose_fragment, container, false);

        // get reference to the view elements
        relativLayout = view.findViewById(R.id.relativLayout);
        listviewlayout = view.findViewById(R.id.listviewlayout);
        no_Expose_found_layout = view.findViewById(R.id.no_Expose_found_layout);
        add_new_expose_when_no_are_found = view.findViewById(R.id.add_new_expose_when_no_are_found);


        // get the current user
        user = FirebaseAuth.getInstance().getCurrentUser();
        user_id = user.getUid();

        immoDataRef = FirebaseDatabase.getInstance().getReference(Constants.DATABASE_PATH_IMMOBILIEN).child(user_id);

        immobilien = new ArrayList<Immobilie>();

        recycler_view = view.findViewById(R.id.show_all_expose_recycler_view);
        recycler_view.setLayoutManager(new LinearLayoutManager(getContext()));


        immoDataRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.getValue() != null) {

                    // hide the button and textview that should not display if there are exposes in the database
                    // and show the list view
                    no_Expose_found_layout.setVisibility(View.GONE);
                    listviewlayout.setVisibility(View.VISIBLE);


                    immobilien.clear();

                    for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                        final Immobilie immobilie = postSnapshot.getValue(Immobilie.class);
                        // adding the picture to the list
                        immobilien.add(immobilie);


                        // creating the List Adapter and add him to the Listview

                        FragmentManager manager = getFragmentManager();

                        ImmobilienSwipeRecyclerViewAdapter mAdapter = new ImmobilienSwipeRecyclerViewAdapter(getContext(), immobilien, manager);

                        mAdapter.setMode(Attributes.Mode.Single);

                        recycler_view.setAdapter(mAdapter);

                        recycler_view.addOnScrollListener(new RecyclerView.OnScrollListener() {
                            @Override
                            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                                super.onScrollStateChanged(recyclerView, newState);
                                Log.e("RecyclerView", "onScrollStateChanged");
                            }

                            @Override
                            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                                super.onScrolled(recyclerView, dx, dy);
                            }
                        });


                    }


                } else {
                    // if there are no values in the database hide the listview and display
                    // the button and text view
                    no_Expose_found_layout.setVisibility(View.VISIBLE);
                    listviewlayout.setVisibility(View.GONE);

                    // change the fragment to the add new expose fraqment when clicking the button
                    add_new_expose_when_no_are_found.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            AddNewExpose addNewExposeFragment = new AddNewExpose();
                            getFragmentManager().beginTransaction().replace(R.id.content_frame, addNewExposeFragment).commit();
                        }
                    });
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


        //return the view
        return view;
    }


}
