package com.webgalaxie.blischke.bachelortakesix.fragments.tabfragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.webgalaxie.blischke.bachelortakesix.R;
import com.webgalaxie.blischke.bachelortakesix.fragments.main_fragments.EditExposeFragment;
import com.webgalaxie.blischke.bachelortakesix.fragments.main_fragments.ShowAllExposeFragment;
import com.webgalaxie.blischke.bachelortakesix.other.Constants;


public class ContactTabFragment extends Fragment {

    private static final String TAG = "CONTACT_TAB";

// textviews
String lastname;
    String firstname;
    String title;
    String salutation;
    String firm;
    String contact_street;
    String contact_housenumber;
    String contact_postcode;
    String contact_city;
    String email;
    String phonenumber;
    String website;
    FirebaseUser user;
    String user_id;
    Bundle bundle, newBundle;
    String immoID;
    private TextView contact_firm_name, contact_firm_website, contact_firm_person,
            contact_firm_street_housenumber, contact_firm_plz_city,
            contact_firm_phonenumber, contact_firm_email;
    private DatabaseReference contactDataRef, immoDataRef, pictureDataRef;

    public ContactTabFragment() {
        // Required empty public constructor

    }


    // Inflate the view for the fragment based on layout XML
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_contact_tab, container, false);

        // get the current user
        user = FirebaseAuth.getInstance().getCurrentUser();
        user_id = user.getUid();


        // get the expose id
        bundle = getArguments();
        immoID = bundle.getString("exposeID");


        // get database Reference


        // find view by id
        contact_firm_name = view.findViewById(R.id.contact_firm_name);
        contact_firm_website = view.findViewById(R.id.contact_firm_website);
        contact_firm_person = view.findViewById(R.id.contact_firm_person);
        contact_firm_street_housenumber = view.findViewById(R.id.contact_firm_street_housenumber);
        contact_firm_plz_city = view.findViewById(R.id.contact_firm_plz_city);
        contact_firm_phonenumber = view.findViewById(R.id.contact_firm_phonenumber);
        contact_firm_email = view.findViewById(R.id.contact_firm_email);


        return view;
    }

    @Override
    public void onStart() {
        super.onStart();

        immoDataRef = FirebaseDatabase.getInstance().getReference(Constants.DATABASE_PATH_IMMOBILIEN).child(user_id).child(immoID);
        pictureDataRef = FirebaseDatabase.getInstance().getReference(Constants.DATABASE_PATH_UPLOADS).child(user_id).child(immoID);
        contactDataRef = FirebaseDatabase.getInstance().getReference(Constants.DATABASE_PATH_CONTACTS).child(user_id).child(immoID);



        // get the reference to the database
        contactDataRef = FirebaseDatabase.getInstance().getReference().child(Constants.DATABASE_PATH_CONTACTS).child(user_id);
        contactDataRef.child(immoID).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // get the data from the database
                String firm = String.valueOf(dataSnapshot.child("firm").getValue()).toString();
                String lastname = String.valueOf(dataSnapshot.child("lastname").getValue()).toString();
                String firstname = String.valueOf(dataSnapshot.child("firstname").getValue()).toString();
                String title = String.valueOf(dataSnapshot.child("title").getValue()).toString();
                String salutation = String.valueOf(dataSnapshot.child("salutation").getValue()).toString();
                String contact_street = String.valueOf(dataSnapshot.child("contact_street").getValue()).toString();
                String contact_housenumber = String.valueOf(dataSnapshot.child("contact_housenumber").getValue()).toString();
                String contact_postcode = String.valueOf(dataSnapshot.child("contact_postcode").getValue()).toString();
                String contact_city = String.valueOf(dataSnapshot.child("contact_city").getValue()).toString();
                String email = String.valueOf(dataSnapshot.child("email").getValue()).toString();
                String phonenumber = String.valueOf(dataSnapshot.child("phonenumber").getValue()).toString();
                String website = String.valueOf(dataSnapshot.child("website").getValue()).toString();

                // display the data into the textfields
                contact_firm_name.setText(firm);
                contact_firm_website.setText(website);
                contact_firm_person.setText(salutation + " " + title + " " + firstname + " " + lastname);
                contact_firm_street_housenumber.setText(contact_street + " " + contact_housenumber);
                contact_firm_plz_city.setText(contact_postcode + " " + contact_city);
                contact_firm_phonenumber.setText(phonenumber);
                contact_firm_email.setText(email);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
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
        // get the expose id
        bundle = getArguments();
        immoID = bundle.getString("exposeID");


        switch (item.getItemId()) {
            case R.id.edit_expose:
                Toast.makeText(getContext(), "Expose bearbeiten geklickt.", Toast.LENGTH_SHORT).show();

                // put the immoID into new Bundle
                newBundle = new Bundle();
                newBundle.putString("exposeID", immoID);
                // get a new instance of editExposeFragment
                Fragment editExpose = new EditExposeFragment();
                // set the newBundle as Arguments to the fragement
                editExpose.setArguments(newBundle);
                // switch the fragment
                manager.beginTransaction().replace(R.id.content_frame, editExpose).commit();

                break;
            case R.id.delete_expose:
                Toast.makeText(getContext(), "Expose wurde gelöscht.", Toast.LENGTH_SHORT).show();

                immoDataRef.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        immoDataRef.removeValue();
                        pictureDataRef.removeValue();
                        contactDataRef.removeValue();

                        Fragment showAllExpose = new ShowAllExposeFragment();
                        manager.beginTransaction().replace(R.id.content_frame, showAllExpose).commit();
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });

                break;

        }
        return super.onOptionsItemSelected(item);
    }
}
