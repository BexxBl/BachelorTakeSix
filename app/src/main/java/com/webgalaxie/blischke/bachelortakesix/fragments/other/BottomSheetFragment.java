package com.webgalaxie.blischke.bachelortakesix.fragments.other;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.os.Bundle;
import android.support.design.widget.BottomSheetDialogFragment;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

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
    String user_id, immo_id;

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
                Toast.makeText(getContext(), "Anhang wird gelöscht", Toast.LENGTH_SHORT).show();
                break;
        }

    }

    private void deleteAttachment() {
        Bundle bundle = getArguments();
        String attachmentID = bundle.getString("attachmentID");
        pictureDataRef = FirebaseDatabase.getInstance().getReference(Constants.STORAGE_PATH_UPLOADS).child(user_id).child(immo_id).child(attachmentID);
        pictureDataRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                pictureDataRef.removeValue();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
}
