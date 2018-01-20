package com.webgalaxie.blischke.bachelortakesix.adapters;

import android.app.Activity;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.ceylonlabs.imageviewpopup.ImagePopup;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.webgalaxie.blischke.bachelortakesix.R;
import com.webgalaxie.blischke.bachelortakesix.models.AttachmentUpload;

import java.util.List;

/**
 * Created by Bexx on 22.12.17.
 */

public class AttachmentList extends ArrayAdapter<AttachmentUpload> {

    List<AttachmentUpload> attachmentUploads;
    DatabaseReference pictureDatabase;
    FirebaseUser user;
    String userid, attachment_id, immoID;
    ImageView attachmentImageView;
    TextView attachmentName;

    private Activity context;

    // Constructor
    public AttachmentList(Activity context, List<AttachmentUpload> attachmentUploads) {
        super(context, R.layout.layout_attachment_list, attachmentUploads);
        this.context = context;
        this.attachmentUploads = attachmentUploads;

    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        // inflate the custom layout for the listitems
        LayoutInflater inflater = context.getLayoutInflater();
        final View listViewItem = inflater.inflate(R.layout.layout_attachment_list, null, true);

        // get the data item for this position
        final AttachmentUpload attachmentUpload = attachmentUploads.get(position);


        user = FirebaseAuth.getInstance().getCurrentUser();
        userid = user.getUid();


        // get references to the view elements in the layout for populating the data
        attachmentName = listViewItem.findViewById(R.id.attachmentImageNameDisplay);
        attachmentImageView = listViewItem.findViewById(R.id.attachmentImageView);

        // set the Name to the textView
        attachmentName.setText(attachmentUpload.getName());

        // set the Image to the ImageView
        Glide.with(getContext()).load(attachmentUpload.getUrl()).into(attachmentImageView);

        // init the ImagePopup and set options
        final ImagePopup imagePopup = new ImagePopup(getContext());
        imagePopup.setWindowHeight(400); // Optional
        imagePopup.setWindowWidth(400); // Optional
        imagePopup.setBackgroundColor(Color.BLACK);  // Optional
        imagePopup.setFullScreen(true); // Optional
        imagePopup.setHideCloseIcon(true);  // Optional
        imagePopup.setImageOnClickClose(true);  // Optional

        // init the Popup with Glide
        imagePopup.initiatePopupWithGlide(attachmentUpload.getUrl());

        // set the onClickListener to the ImageView
        attachmentImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /** Initiate Popup view **/
                imagePopup.viewPopup();

            }
        });






        // return the listview item to render
        return listViewItem;
    }
}
