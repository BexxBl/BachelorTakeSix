package com.webgalaxie.blischke.bachelortakesix.fragments;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.webgalaxie.blischke.bachelortakesix.R;
import com.webgalaxie.blischke.bachelortakesix.adapters.ImmobilienList;
import com.webgalaxie.blischke.bachelortakesix.models.Immobilie;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Bexx on 20.12.17.
 */

public class ShowAllExposeFragment extends Fragment {

    DatabaseReference databaseReference;
    List <Immobilie> immobilien;
    ListView listViewImmobilien;
    FirebaseAuth auth;
    TextView textView;
    Button add_new_expose_when_no_are_found;
    LinearLayout no_Expose_found_layout, listviewlayout;
    ProgressDialog progressDoalog;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // inflating the view
        View v = inflater.inflate(R.layout.show_all_expose_fragment,container,false);

        // getting an FirebaseAuth instance to use the current user id in the database reference
        auth = FirebaseAuth.getInstance();

        // get an reference to the database where the immos are stored under the currents user id
        databaseReference = FirebaseDatabase.getInstance().getReference().child("Immobilien").child(auth.getCurrentUser().getUid());

        // list to store the immo objects
        immobilien = new ArrayList<>();

        // getting the views
        listViewImmobilien = v.findViewById(R.id.show_all_expose_list);
        textView = v.findViewById(R.id.no_Expose_found);
        add_new_expose_when_no_are_found = v.findViewById(R.id.add_new_expose_when_no_are_found);
        listviewlayout =v.findViewById(R.id.listviewlayout);
        no_Expose_found_layout = v.findViewById(R.id.no_Expose_found_layout);

        // return the view
        return v;
    }





    @Override
    public void onStart() {
        super.onStart();

        // attaching the ValueEventListener
        databaseReference.addValueEventListener(new ValueEventListener() {


            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // check if there are values in the database
                if (dataSnapshot.getValue() != null) {

                    // hide the button and textview that should not display if there are exposes in the database
                    // and show the list view
                    no_Expose_found_layout.setVisibility(View.GONE);
                    listviewlayout.setVisibility(View.VISIBLE);

                    // clear the list of immos
                    immobilien.clear();


                    for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                        // getting the immo
                        Immobilie immobilie = postSnapshot.getValue(Immobilie.class);
                        // adding the immo to the list
                        immobilien.add(immobilie);
                    }

                    // creating the List Adapter and add him to the Listview
                    final ImmobilienList immobilienAdapter = new ImmobilienList((Activity) getContext(), immobilien);
                    listViewImmobilien.setAdapter(immobilienAdapter);

                    // add an onClickListener to the ListView so that the Fragement will change to an more
                    // detailed Version of the immo object
                    listViewImmobilien.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                            //get the id of the clicked immo
                            Immobilie immobilie = (Immobilie) listViewImmobilien.getItemAtPosition(position);
                            String immoID = immobilie.getImmo_ID();

                            // change the fragment to ShowExposeFragment which is an more detailed version
                            // of the Expose
                            // give the Fragment the exposeID as an Argument to pass over at the other Fragment
                            Fragment showExposeFragment = new ShowExposeFragment();
                            Bundle bundle = new Bundle();
                            bundle.putString("exposeID", immoID);
                            showExposeFragment.setArguments(bundle);
                            FragmentManager fragmentManager = getFragmentManager();
                            fragmentManager.beginTransaction().replace(R.id.content_frame, showExposeFragment).commit();
                }
            });

                }else{
                    // if there are no values in the database hide the listview and display
                    // the button and text view
                    no_Expose_found_layout.setVisibility(View.VISIBLE);
                    listviewlayout.setVisibility(View.GONE);

                    // change the fragment to the add new expose fraqment when clicking the button
                    add_new_expose_when_no_are_found.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Toast.makeText(getContext(), "Neues Expose wurde geklickt",Toast.LENGTH_SHORT).show();
                            AddNewExposeFragment addNewExposeFragment = new AddNewExposeFragment();
                            getFragmentManager().beginTransaction().replace(R.id.content_frame, addNewExposeFragment).commit();
                        }
                    });


                }



            }

            @Override
            public void onCancelled(DatabaseError databaseError) {



            }

        });


    }
}
