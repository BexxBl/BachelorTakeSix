package com.webgalaxie.blischke.bachelortakesix.fragments.tabfragments;


import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomSheetDialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.RelativeLayout;

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
import com.webgalaxie.blischke.bachelortakesix.adapters.AttachmentList;
import com.webgalaxie.blischke.bachelortakesix.fragments.main_fragments.EditExposeFragment;
import com.webgalaxie.blischke.bachelortakesix.fragments.main_fragments.ShowAllExposeFragment;
import com.webgalaxie.blischke.bachelortakesix.fragments.other.AddAttachmentFragment;
import com.webgalaxie.blischke.bachelortakesix.fragments.other.BottomSheetFragment;
import com.webgalaxie.blischke.bachelortakesix.models.AttachmentUpload;
import com.webgalaxie.blischke.bachelortakesix.other.Constants;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class AttachmentTabFragment extends Fragment {
    private static final String TAG = "ATTACHMENT_TAB";

    FirebaseUser user;
    String user_id;

    Bundle bundle, newBundle;
    String immoID;

    // Button to add Attachments to the Expose
    Button addAtachments;
    ListView show_all_attachments_list;
    List<AttachmentUpload> attachmentUploads;
    RelativeLayout relativLayout;
    private DatabaseReference immoDataRef, pictureDataRef, contactDataRef, attachmentDataRef;
    private StorageReference pictureStorageRef;

    public AttachmentTabFragment() {
        // Required empty public constructor
    }


    // Inflate the view for the fragment based on layout XML
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_attachment_tab, container, false);

        // get reference to the view elements
        addAtachments = view.findViewById(R.id.addAtachments);
        show_all_attachments_list = view.findViewById(R.id.show_all_attachments_list);
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
        attachmentDataRef = FirebaseDatabase.getInstance().getReference(Constants.DATABASE_PATH_UPLOADS).child(user_id).child(immoID).child(Constants.DATABASE_PATH_ATTACHMENTS);
        pictureDataRef = FirebaseDatabase.getInstance().getReference(Constants.DATABASE_PATH_UPLOADS).child(user_id).child(immoID);
        contactDataRef = FirebaseDatabase.getInstance().getReference(Constants.DATABASE_PATH_CONTACTS).child(user_id).child(immoID);
        pictureStorageRef = FirebaseStorage.getInstance().getReference(user_id).child(Constants.STORAGE_PATH_UPLOADS);


        //return the view
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
        // get the expose id
        bundle = getArguments();
        immoID = bundle.getString("exposeID");


        switch (item.getItemId()) {
            case R.id.edit_expose:

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

                immoDataRef.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        immoDataRef.removeValue();
                        pictureDataRef.removeValue();
                        contactDataRef.removeValue();

                        Fragment showAllExpose = new ShowAllExposeFragment();
                        manager.beginTransaction().replace(R.id.content_frame, showAllExpose).commit();
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });

                break;

        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onStart() {
        super.onStart();
        // attaching the ValueEventListener
        attachmentDataRef.addValueEventListener(new ValueEventListener() {


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

                    }

                    // creating the List Adapter and add him to the Listview
                    final AttachmentList attachmentAdapter = new AttachmentList((Activity) getContext(), attachmentUploads);
                    show_all_attachments_list.setAdapter(attachmentAdapter);

                    // set the onClickListener on the image attachments to delete them
                    show_all_attachments_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                            //get the id of the clicked immo
                            AttachmentUpload attachmentUpload = (AttachmentUpload) show_all_attachments_list.getItemAtPosition(position);
                            String attachmentID = attachmentUpload.getId();

                            BottomSheetDialogFragment bottomSheetDialogFragment = new BottomSheetFragment();
                            Bundle bundle = new Bundle();
                            bundle.putString("attachmentID", attachmentID);
                            bottomSheetDialogFragment.setArguments(bundle);
                            bottomSheetDialogFragment.show(getFragmentManager(), bottomSheetDialogFragment.getTag());

                        }
                    });


                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {


            }

        });


    }
}
