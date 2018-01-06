package com.webgalaxie.blischke.bachelortakesix.fragments;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.URLUtil;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;
import com.webgalaxie.blischke.bachelortakesix.R;
import com.webgalaxie.blischke.bachelortakesix.activities.LoginScreen;
import com.yalantis.ucrop.UCrop;

import java.io.File;
import java.math.BigInteger;
import java.security.SecureRandom;

import static android.app.Activity.RESULT_OK;
import static android.content.ContentValues.TAG;

/**
 * Created by Bexx on 20.12.17.
 */

public class MyAccountFragment extends Fragment {

    EditText new_email_input_account, new_password_input_account;
    TextView display_user_name_account, email_display;
    ImageView profilImageDisplay;
    String username, oldEmail, newEmail, newPasswort;

    FirebaseUser user;

    private StorageReference mStorage;
    private DatabaseReference mDatabase;

    private FirebaseAuth.AuthStateListener mAuthListener;
    private FirebaseAuth mAuth;

    private int CAMERA_REQUEST_CODE = 0;

    private ProgressDialog progressDialog;



    @Override
    public void onStart() {
        super.onStart();
        // add the AuthStateListener
        mAuth.addAuthStateListener(mAuthListener);
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        // inflate the layout for the view
        View v = inflater.inflate(R.layout.my_account_fragment,container,false);

        // get an FirebaseAuth instance
        mAuth = FirebaseAuth.getInstance();

        // get an reference to the current user
        user= mAuth.getCurrentUser();
        // get the email according to the current user
        oldEmail = user.getEmail();

        // Init views
        display_user_name_account = v.findViewById(R.id.display_user_name_account);
        email_display = v.findViewById(R.id.email_display);
        profilImageDisplay = v.findViewById(R.id.profilImageDisplay);

        progressDialog = new ProgressDialog(getContext());


        // clicking on the imageview will enable the user to select an profilepicture
        profilImageDisplay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent, "Select a picture for your profile"), CAMERA_REQUEST_CODE);

            }
        });

        // init the AuthStateListener
        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                if (firebaseAuth.getCurrentUser() != null) {
                    mStorage = FirebaseStorage.getInstance().getReference();
                    mDatabase = FirebaseDatabase.getInstance().getReference().child("Users");
                    mDatabase.child(firebaseAuth.getCurrentUser().getUid()).addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            //Set the Email and Username to the Views
                            email_display.setText(String.valueOf(dataSnapshot.child("email").getValue()));
                            display_user_name_account.setText(String.valueOf(dataSnapshot.child("name").getValue()));
                            String imageUrl = String.valueOf(dataSnapshot.child("image").getValue());
                            if (URLUtil.isValidUrl(imageUrl))
                                Picasso.with(getContext()).load(Uri.parse(imageUrl)).into(profilImageDisplay);
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });
                } else {
                    startActivity(new Intent(getContext(), LoginScreen.class));

                }
            }
        };


        return v;
    }



    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == UCrop.RESULT_ERROR) {
            Toast.makeText(getContext(), "uCrop error", Toast.LENGTH_SHORT).show();
            return;
        }
        if (requestCode == UCrop.REQUEST_CROP) {
            final Uri imgUri = UCrop.getOutput(data);
            Toast.makeText(getContext(), imgUri.getPath(), Toast.LENGTH_SHORT).show();
            uploadImage(imgUri);
            return;
        }

        if (requestCode == CAMERA_REQUEST_CODE && resultCode == RESULT_OK) {

            final Uri sourceUri = data.getData();
            if (sourceUri == null) {
                progressDialog.dismiss();
                return;
            } else {

                File tempCropped = new File(getContext().getCacheDir(), "tempImgCropped.png");
                Uri destinationUri = Uri.fromFile(tempCropped);
                UCrop.of(sourceUri, destinationUri)
                        .start((Activity) getContext());
            }
        }
    }


    public void uploadImage(final Uri fileUri) {
        if (mAuth.getCurrentUser() == null)
            return;

        if (mStorage == null)
            mStorage = FirebaseStorage.getInstance().getReference();
        if (mDatabase == null)
            mDatabase = FirebaseDatabase.getInstance().getReference().child("users");

        final StorageReference filepath = mStorage.child("Photos").child(getRandomString());

        final DatabaseReference databaseReference = mDatabase.child(mAuth.getCurrentUser().getUid());

        progressDialog.setMessage("Uploading image...");
        progressDialog.show();

        databaseReference.child("image").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String image = dataSnapshot.getValue().toString();

                if (!image.equals("default") && !image.isEmpty()) {
                    Task<Void> task = FirebaseStorage.getInstance().getReferenceFromUrl(image).delete();
                    task.addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful())
                                Toast.makeText(getContext(), "Deleted image succesfully", Toast.LENGTH_SHORT).show();
                            else
                                Toast.makeText(getContext(), "Deleted image failed", Toast.LENGTH_SHORT).show();
                        }
                    });
                }

                databaseReference.child("image").removeEventListener(this);

                filepath.putFile(fileUri).addOnSuccessListener((Activity) getContext(), new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        progressDialog.dismiss();
                        Uri downloadUri = taskSnapshot.getDownloadUrl();
                        Toast.makeText(getContext(), "Finished", Toast.LENGTH_SHORT).show();
                        Picasso.with(getContext()).load(fileUri).fit().centerCrop().into(profilImageDisplay);
                        DatabaseReference currentUserDB = mDatabase.child(mAuth.getCurrentUser().getUid());
                        currentUserDB.child("image").setValue(downloadUri.toString());
                    }
                }).addOnFailureListener((Activity) getContext(), new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        progressDialog.dismiss();
                        Toast.makeText(getContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    public String getRandomString() {
        SecureRandom random = new SecureRandom();
        return new BigInteger(130, random).toString(32);
    }

}
