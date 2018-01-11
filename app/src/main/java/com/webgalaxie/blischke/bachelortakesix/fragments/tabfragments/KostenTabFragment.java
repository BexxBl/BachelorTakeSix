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
public class KostenTabFragment extends Fragment {
    private static final String TAG = "KOSTEN_TAB";

    private TextView display_immo_kaufpreis, display_immo_preis_pro_qm,
            display_immo_kaltmiete, display_immo_warmmiete,
            display_immo_nebenkosten, display_immo_heizkosten, display_immo_mietzuschläge,
            display_immo_sonstige_kosten, display_immo_gesamtmiete, display_immo_kaution,
            display_immo_kaution_text, display_immo_provision;


    public KostenTabFragment() {
        // Required empty public constructor
    }


    // Inflate the view for the fragment based on layout XML
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_kosten_tab, container, false);

        display_immo_kaufpreis = view.findViewById(R.id.display_immo_kaufpreis);
        display_immo_preis_pro_qm = view.findViewById(R.id.display_immo_preis_pro_qm);
        display_immo_kaltmiete = view.findViewById(R.id.display_immo_kaltmiete);
        display_immo_warmmiete = view.findViewById(R.id.display_immo_warmmiete);
        display_immo_nebenkosten = view.findViewById(R.id.display_immo_nebenkosten);
        display_immo_heizkosten = view.findViewById(R.id.display_immo_heizkosten);
        display_immo_mietzuschläge = view.findViewById(R.id.display_immo_mietzuschläge);
        display_immo_sonstige_kosten = view.findViewById(R.id.display_immo_sonstige_kosten);
        display_immo_gesamtmiete = view.findViewById(R.id.display_immo_gesamtmiete);
        display_immo_kaution = view.findViewById(R.id.display_immo_kaution);
        display_immo_kaution_text = view.findViewById(R.id.display_immo_kaution_text);
        display_immo_provision = view.findViewById(R.id.display_immo_provision);

        return view;
    }
}
