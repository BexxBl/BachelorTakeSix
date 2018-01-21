package com.webgalaxie.blischke.bachelortakesix.fragments.tabfragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;

import com.daimajia.swipe.util.Attributes;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.webgalaxie.blischke.bachelortakesix.R;
import com.webgalaxie.blischke.bachelortakesix.adapters.AttachmentSwipeRecyclerViewAdapter;
import com.webgalaxie.blischke.bachelortakesix.fragments.other.AddAttachmentFragment;
import com.webgalaxie.blischke.bachelortakesix.models.AttachmentUpload;
import com.webgalaxie.blischke.bachelortakesix.other.Constants;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class PictureTabFragment extends Fragment {
    private static final String TAG = "ATTACHMENT_TAB";

    FirebaseUser user;
    String user_id;

    Bundle bundle, newBundle;
    String immoID;

    // Button to add Attachments to the Expose
    Button addAtachments, addPDF;
    ArrayList<AttachmentUpload> attachmentUploads;
    RelativeLayout relativLayout;
    private DatabaseReference immoDataRef, pictureDataRef, contactDataRef, attachmentImagesDataRef;
    private StorageReference pictureStorageRef;
    private RecyclerView mRecyclerView;


    public PictureTabFragment() {
        // Required empty public constructor
    }


    // Inflate the view for the fragment based on layout XML
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_picture_tab, container, false);

        // get reference to the view elements
        addAtachments = view.findViewById(R.id.addAtachments);
        relativLayout = view.findViewById(R.id.relativLayout);


        // get the current user
        user = FirebaseAuth.getInstance().getCurrentUser();
        user_id = user.getUid();


        // get the expose id
        bundle = getArguments();
        immoID = bundle.getString("exposeID");

        // set the on ClickListener to the addAttachments Button
        addAtachments.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // change the fragment
                Fragment addAttachmentFragment = new AddAttachmentFragment();
                FragmentManager manager = getFragmentManager();
                newBundle = new Bundle();
                newBundle.putString("exposeID", immoID);
                addAttachmentFragment.setArguments(newBundle);
                manager.beginTransaction().replace(R.id.content_frame, addAttachmentFragment).addToBackStack(null).commit();

            }
        });


        // get reference to the database and storage
        immoDataRef = FirebaseDatabase.getInstance().getReference(Constants.DATABASE_PATH_IMMOBILIEN).child(user_id).child(immoID);
        attachmentImagesDataRef = FirebaseDatabase.getInstance().getReference(Constants.DATABASE_PATH_IMAGE_UPLOADS).child(user_id).child(immoID).child(Constants.DATABASE_PATH_ATTACHMENTS).child(Constants.DATABASE_PATH_Imagess);
        pictureDataRef = FirebaseDatabase.getInstance().getReference(Constants.DATABASE_PATH_IMAGE_UPLOADS).child(user_id).child(immoID);
        contactDataRef = FirebaseDatabase.getInstance().getReference(Constants.DATABASE_PATH_CONTACTS).child(user_id).child(immoID);
        pictureStorageRef = FirebaseStorage.getInstance().getReference(user_id).child(Constants.STORAGE_PATH_IMAGE_UPLOADS);


        mRecyclerView = view.findViewById(R.id.my_recycler_view);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));


        // attaching the ValueEventListener
        attachmentImagesDataRef.addValueEventListener(new ValueEventListener() {


            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // check if there are values in the database
                if (dataSnapshot.getValue() != null) {
                    attachmentUploads = new ArrayList<AttachmentUpload>();

                    attachmentUploads.clear();

                    for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                        AttachmentUpload attachmentUpload = postSnapshot.getValue(AttachmentUpload.class);
                        // adding the picture to the list
                        attachmentUploads.add(attachmentUpload);

                        String attachmendID = attachmentUpload.getId();
                        user_id = FirebaseAuth.getInstance().getCurrentUser().getUid();
                        immoID = bundle.getString("exposeID");

                        // creating the List Adapter and add him to the Listview

                        AttachmentSwipeRecyclerViewAdapter mAdapter = new AttachmentSwipeRecyclerViewAdapter(getContext(), attachmentUploads);

                        mAdapter.setMode(Attributes.Mode.Single);

                        mRecyclerView.setAdapter(mAdapter);

                        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
                            @Override
                            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                                super.onScrollStateChanged(recyclerView, newState);
                                Log.e("RecyclerView", "onScrollStateChanged");
                            }

                            @Override
                            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                                super.onScrolled(recyclerView, dx, dy);
                            }
                        });


                    }

                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {


            }

        });


        //return the view
        return view;
    }



}
