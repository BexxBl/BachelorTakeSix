package com.webgalaxie.blischke.bachelortakesix.fragments.other;


import android.app.ProgressDialog;
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
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

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
public class AddPDFFragment extends Fragment implements View.OnClickListener {

    Button addPDFBTN;
    EditText pdfNameInput;
    Bundle bundle;
    String immoID, pdfName, string_immo_pdf_url;
    ProgressBar progressBar;
    private DatabaseReference immoDataRef, pdfDataRef;
    private StorageReference pdfStorageRef;
    private Uri filePath;
    private FirebaseUser user;
    private String user_id;


    public AddPDFFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_add_pdf, container, false);

        // get references to the view elements

        addPDFBTN = view.findViewById(R.id.addPDFBTN);

        progressBar = view.findViewById(R.id.progressBar);

        pdfNameInput = view.findViewById(R.id.pdfNameInput);


        // set the onClickListener to this
        // events will be handled in onClick()
        addPDFBTN.setOnClickListener(this);


        // return the view
        return view;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.addPDFBTN:
                getPDF();
                break;
        }

    }


    //this function will get the pdf from the storage
    private void getPDF() {


        //creating an intent for file chooser
        Intent intent = new Intent();
        intent.setType("application/pdf");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "PDF auswählen"), Constants.PICK_PDF_CODE);
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //when the user choses the file
        if (requestCode == Constants.PICK_PDF_CODE && resultCode == RESULT_OK && data != null && data.getData() != null) {
            //if a file is selected
            if (data.getData() != null) {
                //uploading the file
                uploadFile(data.getData());
            } else {
                Toast.makeText(getContext(), "Keine PDF wurde ausgewählt", Toast.LENGTH_SHORT).show();
            }
        }
    }


    //this method is uploading the file
    //the code is same as the previous tutorial
    //so we are not explaining it
    private void uploadFile(Uri data) {
        // get the expose id
        bundle = getArguments();
        immoID = bundle.getString("exposeID");


        // get an reference to the current user and his id
        user = FirebaseAuth.getInstance().getCurrentUser();
        user_id = user.getUid();

        pdfName = pdfNameInput.getText().toString();


        //getting firebase objects
        pdfStorageRef = FirebaseStorage.getInstance()
                .getReference(user_id)
                .child(Constants.STORAGE_PATH_IMAGE_UPLOADS)
                .child(immoID);
        pdfDataRef = FirebaseDatabase.getInstance()
                .getReference(Constants.DATABASE_PATH_PDF_UPLOADS)
                .child(user.getUid()).child(immoID)
                .child(Constants.DATABASE_PATH_ATTACHMENTS)
                .child(Constants.DATABASE_PATH_PDFs);


        if (!TextUtils.isEmpty(pdfName)) {

            //displaying progress dialog while image is uploading
            final ProgressDialog progressDialog = new ProgressDialog(getContext());
            progressDialog.setTitle("PDF wird hochgeladen");
            progressDialog.show();


            StorageReference sRef = pdfStorageRef.child(Constants.STORAGE_PATH_PDF_UPLOADS + System.currentTimeMillis() + ".pdf");

            sRef.putFile(data)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @SuppressWarnings("VisibleForTests")
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                            DatabaseReference pushedDataRef = pdfDataRef.push();
                            String id = pushedDataRef.getKey();

                            Toast.makeText(getContext(), "PDF wurde erfolgreich hochgeladen", Toast.LENGTH_SHORT).show();

                            AttachmentUpload upload = new AttachmentUpload(pdfNameInput.getText().toString(), taskSnapshot.getDownloadUrl().toString(), id, immoID);
                            pdfDataRef.child(id).setValue(upload);

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
                        @SuppressWarnings("VisibleForTests")
                        @Override
                        public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                            double progress = (100.0 * taskSnapshot.getBytesTransferred()) / taskSnapshot.getTotalByteCount();
                            progressDialog.setMessage(((int) progress) + "% wurden hochgeladen");
                        }
                    });
        } else {
            pdfNameInput.setError("Geben Sie einen Namen ein");
            pdfNameInput.requestFocus();
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
