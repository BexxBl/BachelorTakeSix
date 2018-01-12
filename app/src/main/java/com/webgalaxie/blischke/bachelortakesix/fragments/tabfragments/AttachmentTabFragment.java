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
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.webgalaxie.blischke.bachelortakesix.R;
import com.webgalaxie.blischke.bachelortakesix.fragments.EditExposeFragment;

/**
 * A simple {@link Fragment} subclass.
 */
public class AttachmentTabFragment extends Fragment {
    private static final String TAG = "ATTACHMENT_TAB";

    FirebaseUser user;
    String user_id;

    Bundle bundle, newBundle;
    String immoID;

    public AttachmentTabFragment() {
        // Required empty public constructor
    }



    // Inflate the view for the fragment based on layout XML
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_attachment_tab, container, false);

        // get the current user
        user = FirebaseAuth.getInstance().getCurrentUser();
        user_id = user.getUid();


        // get the expose id
        bundle = getArguments();
        immoID = bundle.getString("exposeID");


        return view;
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

        switch (item.getItemId()) {
            case R.id.edit_expose:
                Toast.makeText(getContext(), "Expose bearbeiten geklickt.", Toast.LENGTH_SHORT).show();
                // get the expose id
                bundle = getArguments();
                immoID = bundle.getString("exposeID");
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
                /*
                final DatabaseReference immoDatabase = FirebaseDatabase.getInstance().getReference().child(Constants.DATABASE_PATH_IMMOBILIEN).child(userid);
                immoDatabase.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        //immoDatabase.child("immoID").removeValue();
                        Fragment showAllExpose = new ShowExposeFragment();
                        manager.beginTransaction().replace(R.id.content_frame, showAllExpose).commit();
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
                */
                break;

        }
        return super.onOptionsItemSelected(item);
    }

}
