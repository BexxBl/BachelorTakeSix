package com.webgalaxie.blischke.bachelortakesix.fragments;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.webgalaxie.blischke.bachelortakesix.R;
import com.webgalaxie.blischke.bachelortakesix.models.Immobilie;

import java.util.List;

import static java.lang.Thread.sleep;

/**
 * Created by Bexx on 20.12.17.
 */

public class AddNewExposeFragment extends Fragment {

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

    private Spinner select_expose_type;

    private CheckBox input_immo_heatingcost_included,
            input_immo_show_Adress,
            input_immo_price_with_mwst;

    private Button addImmo, cancel_save_new_expose_BTN, publish_new_expose_BTN;

    private FirebaseUser user;
    private String user_id;

    private Immobilie immobilie;




    //Reference to the database
    DatabaseReference databaseReference;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // set the optionsmenu
        setHasOptionsMenu(true);


    }


    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        //inflate the menu ressource for the optionsmenu
        inflater.inflate(R.menu.addexposemenu, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.action_save_new_expose:
                //Clicking on Save button will save Expose to database
                saveNewExpose();

                return true;
            case R.id.action_cancel_new_expose:
                //Cancel the Creation of Expose here. All Data will be lost.
                clearInputs();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }



    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        // inflate the general layout
        View v = inflater.inflate(R.layout.add_new_expose_fragment,container,false);

        // get an reference to the current user and his id
        user = FirebaseAuth.getInstance().getCurrentUser();
        user_id = user.getUid();


        // get an reference to the database where the immo objects are stored
        databaseReference = FirebaseDatabase.getInstance().getReference().child("Immobilien").child(user_id);

        // get references to the view elements
        input_name = v.findViewById(R.id.input_name);
        input_street = v.findViewById(R.id.input_street);
        input_housenumber = v.findViewById(R.id.input_housenumber);
        input_postcode = v.findViewById(R.id.input_postcode);
        input_city = v.findViewById(R.id.input_city);
        input_country = v.findViewById(R.id.input_country);
        input_desc_text = v.findViewById(R.id.input_desc_text);
        input_location_text = v.findViewById(R.id.input_location_text);
        input_ausstattung_text = v.findViewById(R.id.input_ausstattung_text);
        select_expose_type =v.findViewById(R.id.select_expose_type);

        input_number_of_rooms  = v.findViewById(R.id.input_number_of_rooms);
        input_number_of_sleeping_rooms= v.findViewById(R.id.input_number_of_sleeping_rooms);
        input_number_of_bathrooms= v.findViewById(R.id.input_number_of_bathrooms);
        input_size= v.findViewById(R.id.input_size);
        input_price_netto= v.findViewById(R.id.input_price_netto);
        input_price_brutto= v.findViewById(R.id.input_price_brutto);
        input_rent_cold = v.findViewById(R.id.input_rent_cold);
        input_rent_warm= v.findViewById(R.id.input_rent_warm);
        input_heating_cost= v.findViewById(R.id.input_heating_cost);
        input_caution= v.findViewById(R.id.input_caution);
        input_misc_cost= v.findViewById(R.id.input_misc_cost);
        input_rent_sum= v.findViewById(R.id.input_rent_sum);
        input_price_pro_sqm= v.findViewById(R.id.input_price_pro_sqm);

        input_immo_show_Adress = v.findViewById(R.id.show_adress_checkbox);
        input_immo_price_with_mwst = v.findViewById(R.id.input_immo_price_with_mwst);
        input_immo_heatingcost_included = v.findViewById(R.id.input_immo_heatingcost_included);

        // set the content of the dropdown field
        select_expose_type.setAdapter(ArrayAdapter.createFromResource(getContext(),R.array.expose_types, android.R.layout.simple_spinner_dropdown_item));


        //Hide Fields depending on the Dropdown
        select_expose_type.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                switch (select_expose_type.getSelectedItem().toString()) {
                    case "apartement_buy" :
                        input_rent_cold.setVisibility(View.GONE);
                        input_rent_warm.setVisibility(View.GONE);
                        input_rent_sum.setVisibility(View.GONE);
                        break;
                    case "apartement_rent":
                        break;
                    case "assisted_living":
                        break;
                    case "house_buy":
                        break;
                    case "house_rent":
                        break;
                    case "gastronomy":
                        break;
                    case "office":
                        break;
                }
            }


            @Override
            public void onNothingSelected(AdapterView<?> parent){}


            });


        return v;
    }




    private void getStringsFromEditText(){
        //getting values that should be saved to database
        input_name_String = input_name.getText().toString().trim();
        input_street_String = input_street.getText().toString().trim();
        input_housenumber_String= input_housenumber.getText().toString().trim();
        input_postcode_String = input_postcode.getText().toString().trim();
        input_city_String= input_city.getText().toString().trim();
        input_country_String = input_country.getText().toString().trim();
        input_desc_text_String = input_desc_text.getText().toString().trim();
        input_location_text_String = input_location_text.getText().toString().trim();
        input_ausstattung_text_String = input_ausstattung_text.getText().toString().trim();
        select_expose_type_String = select_expose_type.getSelectedItem().toString();
        input_number_of_rooms_String = input_number_of_rooms.getText().toString().trim();
        input_number_of_sleeping_rooms_String= input_number_of_sleeping_rooms.getText().toString().trim();
        input_number_of_bathrooms_String= input_number_of_bathrooms.getText().toString().trim();
        input_size_String= input_size.getText().toString().trim();
        input_price_netto_String= input_price_netto.getText().toString().trim();
        input_price_brutto_String= input_price_brutto.getText().toString().trim();
        input_rent_cold_String= input_rent_cold.getText().toString().trim();
        input_rent_warm_String= input_rent_warm.getText().toString().trim();
        input_heating_cost_String= input_heating_cost.getText().toString().trim();
        input_caution_String= input_caution.getText().toString().trim();
        input_misc_cost_String= input_misc_cost.getText().toString().trim();
        input_rent_sum_String= input_rent_sum.getText().toString().trim();
        input_price_pro_sqm_String= input_price_pro_sqm.getText().toString().trim();

        if(input_immo_show_Adress.isChecked()){immo_show_Adress_String="ja";}else{immo_show_Adress_String="nein";}
        if(input_immo_heatingcost_included.isChecked()){immo_heatingcost_included_String="ja";}else{immo_heatingcost_included_String="nein";}
        if(input_immo_price_with_mwst.isChecked()){immo_price_with_mwst_String="ja";}else{immo_price_with_mwst_String="nein";}
    }


    @SuppressLint("ResourceType")
    private void saveNewExpose() {
        // get the inputs from the input fields
        getStringsFromEditText();

        //checking if at least name value is provided

        if(!TextUtils.isEmpty(input_name_String)){
            //getting the unique id
            DatabaseReference pushedDataRef = databaseReference.push();
            String id = pushedDataRef.getKey();

            //new realestate Object

            immobilie = new Immobilie(
                    id,
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

            //saving the realestate object to the database
            databaseReference.child(id).setValue(immobilie);

            //Success Toast
            Toast.makeText(getContext(), "Immobilie hinzugefügt", Toast.LENGTH_SHORT).show();

            //change the fragment
            changeFragment();

        }else {
            Toast.makeText(getContext(), "Neue Immobilie hinzufügen ist fehlgeschlagen", Toast.LENGTH_SHORT).show();
                input_name.setError("Geben Sie mindestens den Titel der Immobilie an!");
            input_name.requestFocus();
        }

    }

    private void changeFragment() {

        Fragment showAllExposeFragment = new ShowAllExposeFragment();
        FragmentManager fragmentManager = getFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.content_frame, showAllExposeFragment).commit();
    }

    private void clearInputs() {
        //Clear the textfields of any inputs
        input_name.setText(" ");
        input_street.setText(" ");
        input_housenumber.setText(" ");
        input_postcode.setText(" ");
        input_city.setText(" ");
        input_country.setText(" ");
        input_desc_text.setText(" ");
        input_location_text.setText(" ");
        input_ausstattung_text.setText(" ");

        input_number_of_rooms.setText(" ");
        input_number_of_sleeping_rooms.setText(" ");
        input_number_of_bathrooms.setText(" ");
        input_size.setText(" ");
        input_price_netto.setText(" ");
        input_price_brutto.setText(" ");
        input_rent_cold.setText(" ");
        input_rent_warm.setText(" ");
        input_heating_cost.setText(" ");
        input_caution.setText(" ");
        input_misc_cost.setText(" ");
        input_rent_sum.setText(" ");
        input_price_pro_sqm.setText(" ");

        input_immo_show_Adress.setChecked(false);
        input_immo_price_with_mwst.setChecked(false);
        input_immo_heatingcost_included.setChecked(false);
    }
}
