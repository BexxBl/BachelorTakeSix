package com.webgalaxie.blischke.bachelortakesix.fragments.tabfragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

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
public class KostenTabFragment extends Fragment {
    private static final String TAG = "KOSTEN_TAB";
    FirebaseUser user;
    String user_id;
    String immoID;
    Bundle bundle, newBundle;
    private TextView display_immo_kaufpreis, display_immo_preis_pro_qm,
            display_immo_kaltmiete, display_immo_warmmiete,
            display_immo_nebenkosten, display_immo_heizkosten, display_immo_mietzuschläge,
            display_immo_sonstige_kosten, display_immo_gesamtmiete, display_immo_kaution,
            display_immo_kaution_text, display_immo_provision;
    private DatabaseReference immoDataRef, pictureDataRef, contactDataRef;

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

        // get the current user
        user = FirebaseAuth.getInstance().getCurrentUser();
        user_id = user.getUid();

        // get the expose id
        bundle = getArguments();
        immoID = bundle.getString("exposeID");

        // get an database reference to the immo
        immoDataRef = FirebaseDatabase.getInstance().getReference(Constants.DATABASE_PATH_IMMOBILIEN).child(user_id).child(immoID);
        pictureDataRef = FirebaseDatabase.getInstance().getReference(Constants.DATABASE_PATH_IMAGE_UPLOADS).child(user_id).child(immoID);
        contactDataRef = FirebaseDatabase.getInstance().getReference(Constants.DATABASE_PATH_CONTACTS).child(user_id).child(immoID);


        immoDataRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                display_immo_kaufpreis.setText(String.valueOf(dataSnapshot.child("immo_kaufpreis").getValue()).toString());
                display_immo_preis_pro_qm.setText(String.valueOf(dataSnapshot.child("immo_preis_pro_qm").getValue()).toString());
                display_immo_kaltmiete.setText(String.valueOf(dataSnapshot.child("immo_kaltmiete").getValue()).toString());
                display_immo_warmmiete.setText(String.valueOf(dataSnapshot.child("immo_warmmiete").getValue()).toString());
                display_immo_nebenkosten.setText(String.valueOf(dataSnapshot.child("immo_nebenkosten").getValue()).toString());
                display_immo_heizkosten.setText(String.valueOf(dataSnapshot.child("immo_heizkosten").getValue()).toString());
                display_immo_mietzuschläge.setText(String.valueOf(dataSnapshot.child("immo_mietzuschläge").getValue()).toString());
                display_immo_sonstige_kosten.setText(String.valueOf(dataSnapshot.child("immo_sonstige_kosten").getValue()).toString());
                display_immo_gesamtmiete.setText(String.valueOf(dataSnapshot.child("immo_gesamtmiete").getValue()).toString());
                display_immo_kaution.setText(String.valueOf(dataSnapshot.child("immo_kaution").getValue()).toString());
                display_immo_kaution_text.setText(String.valueOf(dataSnapshot.child("immo_kaution_text").getValue()).toString());
                display_immo_provision.setText(String.valueOf(dataSnapshot.child("immo_provision").getValue()).toString());
            }


            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });



        return view;
    }


}
