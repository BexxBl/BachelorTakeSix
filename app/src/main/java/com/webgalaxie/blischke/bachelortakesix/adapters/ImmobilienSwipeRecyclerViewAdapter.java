package com.webgalaxie.blischke.bachelortakesix.adapters;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.daimajia.swipe.SwipeLayout;
import com.daimajia.swipe.adapters.RecyclerSwipeAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.webgalaxie.blischke.bachelortakesix.R;
import com.webgalaxie.blischke.bachelortakesix.fragments.main_fragments.EditExposeFragment;
import com.webgalaxie.blischke.bachelortakesix.fragments.main_fragments.ShowExposeFragment;
import com.webgalaxie.blischke.bachelortakesix.models.Immobilie;
import com.webgalaxie.blischke.bachelortakesix.other.Constants;

import java.util.ArrayList;

public class ImmobilienSwipeRecyclerViewAdapter extends RecyclerSwipeAdapter<ImmobilienSwipeRecyclerViewAdapter.SimpleViewHolder> {

    public FragmentManager manager;
    DatabaseReference immoDataRef, contactDataRef, attachmentDataRef;
    FirebaseAuth auth;
    FirebaseUser user;
    String user_id, immo_id;
    RelativeLayout relativLayout;
    private Context mContext;
    private ArrayList<Immobilie> immobilien;

    public ImmobilienSwipeRecyclerViewAdapter(Context context, ArrayList<Immobilie> objects, FragmentManager manager) {
        this.mContext = context;
        this.immobilien = objects;
        this.manager = manager;
    }


    @Override
    public SimpleViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_expose_list, parent, false);
        return new SimpleViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final SimpleViewHolder viewHolder, final int position) {


        // get the attachment
        final Immobilie item = immobilien.get(position);

        // get current user
        auth = FirebaseAuth.getInstance();
        user = auth.getCurrentUser();
        user_id = user.getUid();

        // get the id of the immo object
        immo_id = item.getImmoID();

        // database reference
        immoDataRef = FirebaseDatabase.getInstance().getReference(Constants.DATABASE_PATH_IMMOBILIEN).child(user_id).child(immo_id);
        contactDataRef = FirebaseDatabase.getInstance().getReference(Constants.DATABASE_PATH_CONTACTS).child(user_id).child(immo_id);
        attachmentDataRef = FirebaseDatabase.getInstance().getReference("ImmoPictureUploads").child(user_id).child(immo_id);

        // set the data to the view elements
        viewHolder.textViewTitle.setText(item.getImmo_name());
        viewHolder.textViewType.setText(item.getImmo_art() + " zum/ zur " + item.getImmo_vermarktung());
        viewHolder.textViewStreetHousnumber.setText(item.getImmo_street() + " " + item.getImmo_housenumber());
        viewHolder.textViewPLZCityCountry.setText(item.getImmo_postcode() + " " + item.getImmo_city() + " (" + item.getImmo_state() + ", " + item.getImmo_country() + ")");
        viewHolder.immo_size.setText(item.getImmo_gesamtfläche());
        viewHolder.immo_rooms.setText(item.getImmo_zimmer_gesamt());
        viewHolder.immo_beds.setText(item.getImmo_schlafzimmer());
        viewHolder.immo_bathrooms.setText(item.getImmo_badezimmer());


        viewHolder.swipeLayout.setShowMode(SwipeLayout.ShowMode.PullOut);
        //
        viewHolder.swipeLayout.addDrag(SwipeLayout.DragEdge.Right, viewHolder.swipeLayout.findViewById(R.id.bottom_wraper));

        // add the Listener for the Swipe Events
        viewHolder.swipeLayout.addSwipeListener(new SwipeLayout.SwipeListener() {
            @Override
            public void onStartOpen(SwipeLayout layout) {

            }

            @Override
            public void onOpen(SwipeLayout layout) {

            }

            @Override
            public void onStartClose(SwipeLayout layout) {

            }

            @Override
            public void onClose(SwipeLayout layout) {

            }

            @Override
            public void onUpdate(SwipeLayout layout, int leftOffset, int topOffset) {

            }

            @Override
            public void onHandRelease(SwipeLayout layout, float xvel, float yvel) {

            }
        });

        // setup onClick for the delete button
        viewHolder.delete.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                mItemManger.removeShownLayouts(viewHolder.swipeLayout);

                immoDataRef.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        mItemManger.removeShownLayouts(viewHolder.swipeLayout);

                        immoDataRef.removeValue();
                        contactDataRef.removeValue();
                        attachmentDataRef.removeValue();

                        notifyItemRemoved(position);
                        notifyItemRangeChanged(position, immobilien.size());
                        mItemManger.closeAllItems();

                        Toast.makeText(mContext, "Immobilie wurde gelöscht", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });

                notifyItemRemoved(position);
                notifyItemRangeChanged(position, immobilien.size());
                mItemManger.closeAllItems();
            }
        });


        // setup onClick for the edit button

        viewHolder.edit.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                mItemManger.removeShownLayouts(viewHolder.swipeLayout);

                String immoID = item.getImmoID();

                Fragment fragment = new EditExposeFragment();
                Bundle newbundle = new Bundle();
                newbundle.putString("exposeID", immoID);
                fragment.setArguments(newbundle);

                // Insert the fragment by replacing any existing fragment

                manager.beginTransaction()
                        .replace(R.id.content_frame, fragment)
                        .commit();

                notifyItemRemoved(position);
                notifyItemRangeChanged(position, immobilien.size());
                mItemManger.closeAllItems();
            }
        });


        viewHolder.anzeigen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mItemManger.removeShownLayouts(viewHolder.swipeLayout);

                String immoID = item.getImmoID();

                Fragment fragment = new ShowExposeFragment();
                Bundle newbundle = new Bundle();
                newbundle.putString("exposeID", immoID);
                fragment.setArguments(newbundle);

                // Insert the fragment by replacing any existing fragment

                manager.beginTransaction()
                        .replace(R.id.content_frame, fragment)
                        .commit();

                notifyItemRemoved(position);
                notifyItemRangeChanged(position, immobilien.size());
                mItemManger.closeAllItems();
            }
        });


        mItemManger.bindView(viewHolder.itemView, position);


    }

    @Override
    public int getItemCount() {
        return immobilien.size();
    }


    @Override
    public int getSwipeLayoutResourceId(int position) {
        return R.id.swipe;
    }

    public static class SimpleViewHolder extends RecyclerView.ViewHolder {
        public SwipeLayout swipeLayout;
        public TextView delete, edit, anzeigen, textViewTitle, textViewType, textViewStreetHousnumber, textViewPLZCityCountry, immo_size,
                immo_rooms, immo_beds, immo_bathrooms;
        public RelativeLayout relativeLayout;

        public SimpleViewHolder(View itemView) {
            super(itemView);


            // get references to the view elements in the layout for populating the data
            textViewTitle = itemView.findViewById(R.id.immo_title_list);
            textViewType = itemView.findViewById(R.id.immo_type_list);
            textViewStreetHousnumber = itemView.findViewById(R.id.immo_street_housenumber_list);
            immo_size = itemView.findViewById(R.id.immo_size);
            immo_rooms = itemView.findViewById(R.id.immo_rooms);
            immo_beds = itemView.findViewById(R.id.immo_beds);
            immo_bathrooms = itemView.findViewById(R.id.immo_bathrooms);
            textViewPLZCityCountry = itemView.findViewById(R.id.immo_postcode_city_country_list);

            swipeLayout = itemView.findViewById(R.id.swipe);
            delete = itemView.findViewById(R.id.Delete);
            edit = itemView.findViewById(R.id.edit);
            anzeigen = itemView.findViewById(R.id.anzeigen);

            relativeLayout = itemView.findViewById(R.id.relativLayout);


        }


    }
}
