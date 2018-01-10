package com.webgalaxie.blischke.bachelortakesix.adapters;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.webgalaxie.blischke.bachelortakesix.R;
import com.webgalaxie.blischke.bachelortakesix.models.Immobilie;

import java.util.List;

/**
 * Created by Bexx on 22.12.17.
 */

public class ImmobilienList extends ArrayAdapter <Immobilie> {

    List <Immobilie> immobilien;
    DatabaseReference pictureDatabase;
    FirebaseUser user;
    String userid;
    private Activity context;

    // Constructor
    public ImmobilienList (Activity context, List<Immobilie> immobilien){
        super (context, R.layout.layout_expose_list, immobilien);
        this.context = context;
        this.immobilien = immobilien;

    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        // inflate the custom layout for the listitems
        LayoutInflater inflater= context.getLayoutInflater();
        final View listViewItem = inflater.inflate(R.layout.layout_expose_list, null, true);

        // get the data item for this position
        Immobilie immobilie = immobilien.get(position);


        user = FirebaseAuth.getInstance().getCurrentUser();
        userid = user.getUid();
        String immoID = immobilie.getImmoID();


        // get references to the view elements in the layout for populating the data
        TextView textViewTitle = listViewItem.findViewById(R.id.immo_title_list);
        TextView textViewType = listViewItem.findViewById(R.id.immo_type_list);
        TextView textViewStreetHousnumber = listViewItem.findViewById(R.id.immo_street_housenumber_list);
        TextView immo_size = listViewItem.findViewById(R.id.immo_size);
        TextView immo_rooms = listViewItem.findViewById(R.id.immo_rooms);
        TextView immo_beds = listViewItem.findViewById(R.id.immo_beds);
        TextView immo_bathrooms = listViewItem.findViewById(R.id.immo_bathrooms);
        TextView textViewPLZCityCountry = listViewItem.findViewById(R.id.immo_postcode_city_country_list);


        // set the most relevant information of the immo object to the textviews
        textViewTitle.setText(immobilie.getImmo_name());
        textViewType.setText(immobilie.getImmoID());
        textViewStreetHousnumber.setText(immobilie.getImmo_street()+" "+immobilie.getImmo_housenumber());
        textViewPLZCityCountry.setText(immobilie.getImmo_postcode() + " " + immobilie.getImmo_city() + "(" + immobilie.getImmo_state() + ", " + immobilie.getImmo_country() + ")");
        immo_size.setText(immobilie.getImmo_gesamtfläche());
        immo_rooms.setText(immobilie.getImmo_zimmer_gesamt());
        immo_beds.setText(immobilie.getImmo_schlafzimmer());
        immo_bathrooms.setText(immobilie.getImmo_badezimmer());


        // return the listview item to render
        return listViewItem;
    }
}
