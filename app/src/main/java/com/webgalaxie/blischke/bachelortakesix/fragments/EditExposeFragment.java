package com.webgalaxie.blischke.bachelortakesix.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.webgalaxie.blischke.bachelortakesix.R;
import com.webgalaxie.blischke.bachelortakesix.models.Immobilie;

/**
 * Created by Bexx on 03.01.18.
 */

public class EditExposeFragment extends Fragment {

    String input_name_String,
            input_street_String,
            input_housenumber_String,
            input_postcode_String,
            input_city_String,
            input_country_String,
            input_desc_text_String,
            input_location_text_String,
            input_ausstattung_text_String,
            select_expose_type_String,
            input_number_of_rooms_String,
            input_number_of_sleeping_rooms_String,
            input_number_of_bathrooms_String,
            input_size_String,
            input_price_netto_String,
            input_price_brutto_String,
            input_rent_cold_String,
            input_rent_warm_String,
            input_heating_cost_String,
            input_caution_String,
            input_misc_cost_String,
            input_rent_sum_String,
            input_price_pro_sqm_String,
            immo_heatingcost_included_String,
            immo_show_Adress_String,
            immo_price_with_mwst_String;
    Bundle bundle;
    //Reference to the database
    DatabaseReference databaseReference;
    private DatabaseReference mDatabase;
    private EditText input_name,
            input_street,
            input_housenumber,
            input_postcode,
            input_city,
            input_country,
            input_desc_text,
            input_location_text,
            input_ausstattung_text,
            input_number_of_rooms,
            input_number_of_sleeping_rooms,
            input_number_of_bathrooms,
            input_size,
            input_price_netto,
            input_price_brutto,
            input_rent_cold,
            input_rent_warm,
            input_heating_cost,
            input_caution,
            input_misc_cost,
            input_rent_sum,
            input_price_pro_sqm;
    private Spinner select_expose_type;
    private CheckBox input_immo_heatingcost_included,
            input_immo_show_Adress,
            input_immo_price_with_mwst;
    private FirebaseUser user;
    private String user_id;
    private Immobilie immobilie;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);

    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        // inflate the layout
        View v = inflater.inflate(R.layout.fragment_delete_immo_success, container, false);

        // get the current user
        user = FirebaseAuth.getInstance().getCurrentUser();
        user_id = user.getUid();


        // get the exposeID from the showFragment
        bundle = getArguments();
        if (bundle != null) {
            String immoID = bundle.getString("exposeID");
        } else {
            Toast.makeText(getContext(), "Keine Argumente gesendet", Toast.LENGTH_SHORT).show();
        }

        /*
        //Init the view elements
        input_name = v.findViewById(R.id.input_name);
        input_street = v.findViewById(R.id.input_street);
        input_housenumber = v.findViewById(R.id.input_housenumber);
        input_postcode = v.findViewById(R.id.input_postcode);
        input_city = v.findViewById(R.id.input_city);
        input_country = v.findViewById(R.id.input_country);
        input_desc_text = v.findViewById(R.id.input_desc_text);
        input_location_text = v.findViewById(R.id.input_location_text);
        input_ausstattung_text = v.findViewById(R.id.input_ausstattung_text);
        select_expose_type = v.findViewById(R.id.select_expose_type);

        input_number_of_rooms = v.findViewById(R.id.input_number_of_rooms);
        input_number_of_sleeping_rooms = v.findViewById(R.id.input_number_of_sleeping_rooms);
        input_number_of_bathrooms = v.findViewById(R.id.input_number_of_bathrooms);
        input_size = v.findViewById(R.id.input_size);
        input_price_netto = v.findViewById(R.id.input_price_netto);
        input_price_brutto = v.findViewById(R.id.input_price_brutto);
        input_rent_cold = v.findViewById(R.id.input_rent_cold);
        input_rent_warm = v.findViewById(R.id.input_rent_warm);
        input_heating_cost = v.findViewById(R.id.input_heating_cost);
        input_caution = v.findViewById(R.id.input_caution);
        input_misc_cost = v.findViewById(R.id.input_misc_cost);
        input_rent_sum = v.findViewById(R.id.input_rent_sum);
        input_price_pro_sqm = v.findViewById(R.id.input_price_pro_sqm);

        input_immo_show_Adress = v.findViewById(R.id.show_adress_checkbox);
        input_immo_price_with_mwst = v.findViewById(R.id.input_immo_price_with_mwst);
        input_immo_heatingcost_included = v.findViewById(R.id.input_immo_heatingcost_included);

        select_expose_type.setAdapter(ArrayAdapter.createFromResource(getContext(), R.array.expose_types, android.R.layout.simple_spinner_dropdown_item));


        //set the text in the view elements according to the data for the id
        final String immoID = bundle.getString("exposeID");

        // get the database reference for the expose
        databaseReference = FirebaseDatabase.getInstance().getReference().child("Immobilien").child(user_id).child(immoID);

        databaseReference.addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                //SET up the edittext with the values of the database
                input_name.setText(String.valueOf(dataSnapshot.child("immo_name").getValue()));
                input_street.setText(String.valueOf(dataSnapshot.child("immo_street").getValue()));
                input_housenumber.setText(String.valueOf(dataSnapshot.child("immo_housenumber").getValue()));
                input_postcode.setText(String.valueOf(dataSnapshot.child("immo_postcode").getValue()));
                input_city.setText(String.valueOf(dataSnapshot.child("immo_city").getValue()));
                input_country.setText(String.valueOf(dataSnapshot.child("immo_country").getValue()));
                input_desc_text.setText(String.valueOf(dataSnapshot.child("immo_desc_text").getValue()));
                input_location_text.setText(String.valueOf(dataSnapshot.child("immo_location_text").getValue()));
                input_ausstattung_text.setText(String.valueOf(dataSnapshot.child("immo_ausstattung_text").getValue()));

                input_number_of_rooms.setText(String.valueOf(dataSnapshot.child("immo_number_of_rooms").getValue()));
                input_number_of_sleeping_rooms.setText(String.valueOf(dataSnapshot.child("immo_number_of_sleeping_rooms").getValue()));
                input_number_of_bathrooms.setText(String.valueOf(dataSnapshot.child("immo_number_of_bathrooms").getValue()));
                input_size.setText(String.valueOf(dataSnapshot.child("immo_size").getValue()));
                input_price_netto.setText(String.valueOf(dataSnapshot.child("immo_price_netto").getValue()));
                input_price_brutto.setText(String.valueOf(dataSnapshot.child("immo_price_brutto").getValue()));
                input_rent_cold.setText(String.valueOf(dataSnapshot.child("immo_rent_cold").getValue()));
                input_rent_warm.setText(String.valueOf(dataSnapshot.child("immo_rent_warm").getValue()));
                input_heating_cost.setText(String.valueOf(dataSnapshot.child("immo_heating_cost").getValue()));
                input_caution.setText(String.valueOf(dataSnapshot.child("immo_caution").getValue()));
                input_misc_cost.setText(String.valueOf(dataSnapshot.child("immo_misc_cost").getValue()));
                input_rent_sum.setText(String.valueOf(dataSnapshot.child("immo_rent_sum").getValue()));
                input_price_pro_sqm.setText(String.valueOf(dataSnapshot.child("immo_price_pro_sqm").getValue()));



                if (dataSnapshot.child("immo_show_Adress").getValue() == "ja") {
                    input_immo_show_Adress.setChecked(true);
                } else {
                    input_immo_show_Adress.setChecked(false);
                }

                if (dataSnapshot.child("immo_heatingcost_included").getValue() == "ja") {
                    input_immo_heatingcost_included.setChecked(true);
                } else {
                    input_immo_heatingcost_included.setChecked(false);
                }
                if (dataSnapshot.child("immo_price_with_mwst").getValue() == "ja") {
                    input_immo_price_with_mwst.setChecked(true);
                } else {
                    input_immo_price_with_mwst.setChecked(false);
                }





            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });

*/
        //return the view
        return v;
    }


    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.addexposemenu, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.action_save_new_expose:
                //Clicking on Save button will save Expose to database
                final String immoID = bundle.getString("exposeID");
                Toast.makeText(getContext(), "Expose wird danach geupdatet. Funktion auskommentiert weil bildupload noch eingefügt werden muss", Toast.LENGTH_SHORT).show();
                //updateExpose();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

   /*

    private void updateExpose() {

        // get the exposeID from the showFragment
        bundle = getArguments();
        String immoID = bundle.getString("exposeID");


        // get an reference to the database
        databaseReference = FirebaseDatabase.getInstance().getReference().child("Immobilien").child(user_id).child(immoID);

        //getting values that should be saved to database
        input_name_String = input_name.getText().toString().trim();
        input_street_String = input_street.getText().toString().trim();
        input_housenumber_String = input_housenumber.getText().toString().trim();
        input_postcode_String = input_postcode.getText().toString().trim();
        input_city_String = input_city.getText().toString().trim();
        input_country_String = input_country.getText().toString().trim();
        input_desc_text_String = input_desc_text.getText().toString().trim();
        input_location_text_String = input_location_text.getText().toString().trim();
        input_ausstattung_text_String = input_ausstattung_text.getText().toString().trim();
        select_expose_type_String = select_expose_type.getSelectedItem().toString();
        input_number_of_rooms_String = input_number_of_rooms.getText().toString().trim();
        input_number_of_sleeping_rooms_String = input_number_of_sleeping_rooms.getText().toString().trim();
        input_number_of_bathrooms_String = input_number_of_bathrooms.getText().toString().trim();
        input_size_String = input_size.getText().toString().trim();
        input_price_netto_String = input_price_netto.getText().toString().trim();
        input_price_brutto_String = input_price_brutto.getText().toString().trim();
        input_rent_cold_String = input_rent_cold.getText().toString().trim();
        input_rent_warm_String = input_rent_warm.getText().toString().trim();
        input_heating_cost_String = input_heating_cost.getText().toString().trim();
        input_caution_String = input_caution.getText().toString().trim();
        input_misc_cost_String = input_misc_cost.getText().toString().trim();
        input_rent_sum_String = input_rent_sum.getText().toString().trim();
        input_price_pro_sqm_String = input_price_pro_sqm.getText().toString().trim();


        if (input_immo_show_Adress.isChecked()) {
            immo_show_Adress_String = "ja";
        } else {
            immo_show_Adress_String = "nein";
        }
        if (input_immo_heatingcost_included.isChecked()) {
            immo_heatingcost_included_String = "ja";
        } else {
            immo_heatingcost_included_String = "nein";
        }
        if (input_immo_price_with_mwst.isChecked()) {
            immo_price_with_mwst_String = "ja";
        } else {
            immo_price_with_mwst_String = "nein";
        }


        // updating the database

        Immobilie immobilie = new Immobilie(immoID,

                input_name_String,
                input_street_String,
                input_housenumber_String,
                input_postcode_String,
                input_city_String,
                input_country_String,
                input_desc_text_String,
                input_location_text_String,
                input_ausstattung_text_String,
                select_expose_type_String,
                input_number_of_rooms_String,
                input_number_of_sleeping_rooms_String,
                input_number_of_bathrooms_String,
                input_size_String,
                input_price_netto_String,
                input_price_brutto_String,
                input_rent_cold_String,
                input_rent_warm_String,
                input_heating_cost_String,
                input_caution_String,
                input_misc_cost_String,
                input_rent_sum_String,
                input_price_pro_sqm_String,
                immo_heatingcost_included_String,
                immo_show_Adress_String,
                immo_price_with_mwst_String);

        databaseReference.setValue(immobilie);

        Toast.makeText(getContext(), "Immobilie wurde erfolgreich geupdated", Toast.LENGTH_LONG).show();


        // change fragment to show expose
        Fragment showExposeFragment = new ShowExposeFragment();
        Bundle bundle = new Bundle();
        bundle.putString("exposeID", immoID);
        showExposeFragment.setArguments(bundle);
        FragmentManager fragmentManager = getFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.content_frame, showExposeFragment).commit();
    }*/





}


