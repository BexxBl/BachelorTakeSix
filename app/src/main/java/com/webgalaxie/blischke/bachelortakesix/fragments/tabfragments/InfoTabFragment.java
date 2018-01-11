package com.webgalaxie.blischke.bachelortakesix.fragments.tabfragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.webgalaxie.blischke.bachelortakesix.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class InfoTabFragment extends Fragment {

    private static final String TAG = "INFO_TAB";
    String immoID;

    TextView immoIDdisplay, display_adress_street_housenumber, display_adress_plz_city_state_country,
            display_immo_preis_info_tab, display_immo_zimmeranzahl, display_immo_gesamtfläche,
            display_immo_ausstattung, display_immo_type, display_immo_etage, display_immo_wohnfläche,
            display_immo_zimmer_gesamt, display_immo_schlafzimmer, display_immo_badezimmer,
            display_description_of_immo, display_location_info_of_immo, display_aussattung_info_of_immo,
            display_immo_baujahr, display_immo_objektzustand, display_immo_ausstattung_quality,
            display_immo_heizungsart, display_immo_energieträger, display_immo_sanierung;



    // Store instance variables based on arguments passed
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the view for the fragment based on layout XML
        View view = inflater.inflate(R.layout.fragment_info_tab, container, false);


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

        // return the view
        return view;
    }


}
