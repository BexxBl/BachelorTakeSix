package com.webgalaxie.blischke.bachelortakesix.fragments.other;


import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.webgalaxie.blischke.bachelortakesix.R;
import com.webgalaxie.blischke.bachelortakesix.fragments.main_fragments.ShowExposeFragment;
import com.webgalaxie.blischke.bachelortakesix.models.AttachmentUpload;
import com.webgalaxie.blischke.bachelortakesix.other.Constants;

import static android.app.Activity.RESULT_OK;

/**
 * A simple {@link Fragment} subclass.
 */
public class AddAttachmentFragment extends Fragment implements View.OnClickListener {

    Button chooseImageBTN, addImageBTN;
    EditText imageNameInput;
    ImageView showSelectedImage;
    Bundle bundle;
    String immoID, imageName, string_immo_image_url;
    private DatabaseReference immoDataRef, pictureDataRef;
    private StorageReference pictureStorageRef;
    private Uri filePath;

    private FirebaseUser user;
    private String user_id;


    public AddAttachmentFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_add_attachment, container, false);

        // get references to the view elements

        chooseImageBTN = view.findViewById(R.id.chooseImageBTN);
        addImageBTN = view.findViewById(R.id.addImageBTN);

        showSelectedImage = view.findViewById(R.id.showSelectedImage);

        imageNameInput = view.findViewById(R.id.imageNameInput);


        // set the onClickListener to this
        // events will be handled in onClick()
        chooseImageBTN.setOnClickListener(this);
        addImageBTN.setOnClickListener(this);


        // return the view
        return view;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.chooseImageBTN:
                choosePicture();
                break;
            case R.id.addImageBTN:
                saveImage();
                break;
        }

    }


    private void choosePicture() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), Constants.PICK_IMAGE_REQUEST);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == Constants.PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
            filePath = data.getData();
            Glide.with(getContext()).load(filePath).into(showSelectedImage);
        }
    }

    public String getFileExtension(Uri uri) {
        ContentResolver cR = getContext().getContentResolver();
        MimeTypeMap mime = MimeTypeMap.getSingleton();
        return mime.getExtensionFromMimeType(cR.getType(uri));
    }

    private void saveImage() {

        // get the expose id
        bundle = getArguments();
        immoID = bundle.getString("exposeID");


        // get an reference to the current user and his id
        user = FirebaseAuth.getInstance().getCurrentUser();
        user_id = user.getUid();

        imageName = imageNameInput.getText().toString();

        if (!TextUtils.isEmpty(imageName)) {

            //displaying progress dialog while image is uploading
            final ProgressDialog progressDialog = new ProgressDialog(getContext());
            progressDialog.setTitle("Bild wird hochgeladen");
            progressDialog.show();


            // uploading the Picture
            pictureStorageRef = FirebaseStorage.getInstance()
                    .getReference(user_id)
                    .child(Constants.STORAGE_PATH_IMAGE_UPLOADS)
                    .child(immoID);
            pictureDataRef = FirebaseDatabase.getInstance()
                    .getReference(Constants.DATABASE_PATH_IMAGE_UPLOADS)
                    .child(user.getUid()).child(immoID)
                    .child(Constants.DATABASE_PATH_ATTACHMENTS)
                    .child(Constants.DATABASE_PATH_Imagess);
            //checking if file is available
            if (filePath != null) {


                //getting the storage reference
                StorageReference sRef = pictureStorageRef.child(Constants.STORAGE_PATH_IMAGE_UPLOADS + System.currentTimeMillis() + "." + getFileExtension(filePath));

                //adding the file to reference
                sRef.putFile(filePath)
                        .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                            @Override
                            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                //getting the unique id
                                DatabaseReference pushedDataRef = pictureDataRef.push();
                                String id = pushedDataRef.getKey();

                                String imageName = imageNameInput.getText().toString().trim();
                                String imageDownloadURL = taskSnapshot.getDownloadUrl().toString();

                                //creating the upload object to store uploaded image details
                                AttachmentUpload upload = new AttachmentUpload(imageName, imageDownloadURL, id, immoID);


                                //adding an upload to firebase database
                                pictureDataRef.child(id).setValue(upload);
                                //pictureDataRef.child(imageName).setValue(imageDownloadURL);
                                string_immo_image_url = imageDownloadURL;


                                //dismissing the progress dialog
                                progressDialog.dismiss();

                                changeFragment();


                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception exception) {
                                progressDialog.dismiss();
                                Toast.makeText(getContext(), exception.getMessage(), Toast.LENGTH_LONG).show();
                            }
                        })
                        .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                            @Override
                            public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                                //displaying the upload progress
                                double progress = (100.0 * taskSnapshot.getBytesTransferred()) / taskSnapshot.getTotalByteCount();
                                progressDialog.setMessage(((int) progress) + "% wurden hochgeladen");
                            }
                        });
            }
        } else {
            imageNameInput.setError("Geben Sie einen Namen ein");
            imageNameInput.requestFocus();

        }
    }

    private void changeFragment() {
        // change the fragment
        // give the Fragment the exposeID as an Argument to pass over at the other Fragment
        Fragment showExposeFragment = new ShowExposeFragment();
        Bundle bundle = new Bundle();
        bundle.putString("exposeID", immoID);
        showExposeFragment.setArguments(bundle);
        FragmentManager fragmentManager = getFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.content_frame, showExposeFragment).commit();
    }
}
