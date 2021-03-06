package com.webgalaxie.blischke.bachelortakesix.fragments.tabfragments;


import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.ceylonlabs.imageviewpopup.ImagePopup;
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
 * A simple {@link Fragment} subclass.
 */
public class InfoTabFragment extends Fragment {

    private static final String TAG = "INFO_TAB";
    FirebaseUser user;
    String user_id;
    Bundle bundle, newBundle;
    String immoID;
    TextView immoIDdisplay, display_adress_street_housenumber, display_adress_plz_city_state_country,
            display_immo_preis_info_tab, display_immo_zimmeranzahl, display_immo_gesamtfläche,
            display_immo_ausstattung, display_immo_type, display_immo_etage, display_immo_wohnfläche,
            display_immo_zimmer_gesamt, display_immo_schlafzimmer, display_immo_badezimmer,
            display_description_of_immo, display_location_info_of_immo, display_aussattung_info_of_immo,
            display_immo_objektzustand, display_immo_ausstattung_quality,
            display_immo_heizungsart, display_immo_energieträger, display_immo_sanierung, display_immo_baujahr,
            display_immo_energieausweis_type, display_immo_energieausweis_y_n;
    ImageView immo_first_pic;
    private DatabaseReference immoDataRef, pictureDataRef, contactDataRef;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the view for the fragment based on layout XML
        View view = inflater.inflate(R.layout.fragment_info_tab, container, false);

        immo_first_pic = view.findViewById(R.id.immo_first_pic);
        immoIDdisplay = view.findViewById(R.id.immoIDdisplay);
        display_adress_street_housenumber = view.findViewById(R.id.display_adress_street_housenumber);
        display_adress_plz_city_state_country = view.findViewById(R.id.display_adress_plz_city_state_country);
        display_immo_preis_info_tab = view.findViewById(R.id.display_immo_preis_info_tab);
        display_immo_zimmeranzahl = view.findViewById(R.id.display_immo_zimmeranzahl);
        display_immo_gesamtfläche = view.findViewById(R.id.display_immo_gesamtfläche);
        display_immo_ausstattung = view.findViewById(R.id.display_immo_ausstattung);
        display_immo_type = view.findViewById(R.id.display_immo_type);
        display_immo_etage = view.findViewById(R.id.display_immo_etage);
        display_immo_wohnfläche = view.findViewById(R.id.display_immo_wohnfläche);
        display_immo_zimmer_gesamt = view.findViewById(R.id.display_immo_zimmer_gesamt);
        display_immo_schlafzimmer = view.findViewById(R.id.display_immo_schlafzimmer);
        display_immo_badezimmer = view.findViewById(R.id.display_immo_badezimmer);
        display_description_of_immo = view.findViewById(R.id.display_description_of_immo);
        display_location_info_of_immo = view.findViewById(R.id.display_location_info_of_immo);
        display_aussattung_info_of_immo = view.findViewById(R.id.display_aussattung_info_of_immo);
        display_immo_baujahr = view.findViewById(R.id.display_immo_baujahr);
        display_immo_objektzustand = view.findViewById(R.id.display_immo_objektzustand);
        display_immo_ausstattung_quality = view.findViewById(R.id.display_immo_ausstattung_quality);
        display_immo_heizungsart = view.findViewById(R.id.display_immo_heizungsart);
        display_immo_sanierung = view.findViewById(R.id.display_immo_sanierung);
        display_immo_energieträger = view.findViewById(R.id.display_immo_energieträger);
        display_immo_baujahr = view.findViewById(R.id.display_immo_baujahr);
        display_immo_energieausweis_type = view.findViewById(R.id.display_immo_energieausweis_type);
        display_immo_energieausweis_y_n = view.findViewById(R.id.display_immo_energieausweis_y_n);

        // get the current user
        user = FirebaseAuth.getInstance().getCurrentUser();
        user_id = user.getUid();

        // get the expose id
        bundle = getArguments();
        immoID = bundle.getString("exposeID");

        // get an database reference to the immo
        immoDataRef = FirebaseDatabase.getInstance().getReference(Constants.DATABASE_PATH_IMMOBILIEN).child(user_id).child(immoID);
        pictureDataRef = FirebaseDatabase.getInstance().getReference(Constants.DATABASE_PATH_IMAGE_UPLOADS).child(user_id).child(immoID).child(Constants.DATABASE_PATH_IMMO_FIRST_PICTURE);
        contactDataRef = FirebaseDatabase.getInstance().getReference(Constants.DATABASE_PATH_CONTACTS).child(user_id).child(immoID);

        // add the ValueEventListener
        immoDataRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                immoIDdisplay.setText(String.valueOf(dataSnapshot.child("immoID").getValue()).toString());
                display_adress_street_housenumber.setText(String.valueOf(dataSnapshot.child("immo_street").getValue()).toString() +
                        " " + String.valueOf(dataSnapshot.child("immo_housenumber").getValue()).toString());
                display_adress_plz_city_state_country.setText(
                        String.valueOf(dataSnapshot.child("immo_postcode").getValue()).toString() + " " +
                                String.valueOf(dataSnapshot.child("immo_city").getValue()).toString() + " (" +
                                String.valueOf(dataSnapshot.child("immo_state").getValue()).toString() + " " +
                                String.valueOf(dataSnapshot.child("immo_country").getValue()).toString() + " )"
                );
                display_immo_preis_info_tab.setText(String.valueOf(dataSnapshot.child("immo_kaufpreis").getValue()).toString() + " €");
                display_immo_zimmeranzahl.setText(String.valueOf(dataSnapshot.child("immo_zimmer_gesamt").getValue()).toString());
                display_immo_gesamtfläche.setText(String.valueOf(dataSnapshot.child("immo_gesamtfläche").getValue()).toString() + " qm");
                display_immo_ausstattung.setText(String.valueOf(dataSnapshot.child("immo_ausgewählte_ausstattung").getValue()).toString());
                display_immo_type.setText(String.valueOf(dataSnapshot.child("immo_art").getValue()).toString());
                display_immo_etage.setText(String.valueOf(dataSnapshot.child("immo_etage").getValue()).toString() + " von " + String.valueOf(dataSnapshot.child("immo_etage_total").getValue()).toString());
                display_immo_wohnfläche.setText(String.valueOf(dataSnapshot.child("immo_wohnfläche").getValue()).toString() + " qm");
                display_immo_zimmer_gesamt.setText(String.valueOf(dataSnapshot.child("immo_zimmer_gesamt").getValue()).toString());
                display_immo_schlafzimmer.setText(String.valueOf(dataSnapshot.child("immo_schlafzimmer").getValue()).toString());
                display_immo_badezimmer.setText(String.valueOf(dataSnapshot.child("immo_badezimmer").getValue()).toString());
                display_description_of_immo.setText(String.valueOf(dataSnapshot.child("immo_desc_text").getValue()).toString());
                display_location_info_of_immo.setText(String.valueOf(dataSnapshot.child("immo_location_text").getValue()).toString());
                display_aussattung_info_of_immo.setText(String.valueOf(dataSnapshot.child("immo_ausstattung_text").getValue()).toString());
                display_immo_objektzustand.setText(String.valueOf(dataSnapshot.child("immo_objektzustand").getValue()).toString());
                display_immo_ausstattung_quality.setText(String.valueOf(dataSnapshot.child("immo_ausstattung_quality").getValue()).toString());
                display_immo_heizungsart.setText(String.valueOf(dataSnapshot.child("immo_heizungsart").getValue()).toString());
                display_immo_sanierung.setText(String.valueOf(dataSnapshot.child("immo_sanierung").getValue()).toString());
                display_immo_energieträger.setText(String.valueOf(dataSnapshot.child("immo_energieträger").getValue()).toString());
                display_immo_baujahr.setText(String.valueOf(dataSnapshot.child("immo_baujahr").getValue()).toString());
                display_immo_energieausweis_type.setText(String.valueOf(dataSnapshot.child("immo_energieausweis_type").getValue()).toString());
                display_immo_energieausweis_y_n.setText(String.valueOf(dataSnapshot.child("immo_energieausweis_yes_no").getValue()).toString());


            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        pictureDataRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Glide.with(getContext()).load(String.valueOf(dataSnapshot.child("url").getValue()).toString()).into(immo_first_pic);

                final ImagePopup imagePopup = new ImagePopup(getContext());
                imagePopup.setWindowHeight(800); // Optional
                imagePopup.setWindowWidth(800); // Optional
                imagePopup.setBackgroundColor(Color.BLACK);  // Optional
                imagePopup.setFullScreen(true); // Optional
                imagePopup.setHideCloseIcon(true);  // Optional
                imagePopup.setImageOnClickClose(true);  // Optional

                imagePopup.initiatePopupWithGlide(String.valueOf(dataSnapshot.child("url").getValue()).toString());

                immo_first_pic.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        /** Initiate Popup view **/
                        imagePopup.viewPopup();

                    }
                });

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


        // return the view
        return view;
    }



}
