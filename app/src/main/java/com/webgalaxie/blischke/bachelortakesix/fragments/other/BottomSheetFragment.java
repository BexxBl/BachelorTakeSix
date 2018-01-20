package com.webgalaxie.blischke.bachelortakesix.fragments.other;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.os.Bundle;
import android.support.design.widget.BottomSheetDialogFragment;
import android.view.View;
import android.widget.LinearLayout;

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
 * Created by Bexx on 19.01.18.
 */

public class BottomSheetFragment extends BottomSheetDialogFragment implements View.OnClickListener {

    LinearLayout delete;
    DatabaseReference pictureDataRef;
    String user_id, immo_id, attachmentID;

    public BottomSheetFragment() {
        // Required empty public constructor
    }

    @SuppressLint("RestrictedApi")
    @Override
    public void setupDialog(final Dialog dialog, int style) {
        super.setupDialog(dialog, style);
        View contentView = View.inflate(getContext(), R.layout.bottom_sheet, null);
        dialog.setContentView(contentView);

        delete = contentView.findViewById(R.id.delete);
        delete.setOnClickListener(this);


    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.delete:
                deleteAttachment();
                break;

        }

    }


    private void deleteAttachment() {
        // get an FirebaseAuth instance
        FirebaseAuth auth = FirebaseAuth.getInstance();

        // get an reference to the current user
        FirebaseUser user = auth.getCurrentUser();
        user_id = user.getUid();

        Bundle bundle = getArguments();
        attachmentID = bundle.getString("attachmentID");
        immo_id = bundle.getString("exposeID");

        pictureDataRef = FirebaseDatabase.getInstance().getReference(Constants.DATABASE_PATH_UPLOADS)
                .child(user_id)
                .child(immo_id)
                .child(Constants.DATABASE_PATH_ATTACHMENTS)
                .child(attachmentID);


        pictureDataRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                pictureDataRef.removeValue();
                dismiss();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
}
