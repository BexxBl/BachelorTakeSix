package com.webgalaxie.blischke.bachelortakesix.fragments.main_fragments;

import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.webgalaxie.blischke.bachelortakesix.R;
import com.webgalaxie.blischke.bachelortakesix.activities.LoginScreen;
import com.webgalaxie.blischke.bachelortakesix.activities.UserDeleteActivity;
import com.webgalaxie.blischke.bachelortakesix.models.PictureUpload;
import com.webgalaxie.blischke.bachelortakesix.other.Constants;

import java.io.IOException;

import static android.app.Activity.RESULT_OK;

/**
 * Created by Bexx on 20.12.17.
 */

public class MyAccountFragment extends Fragment {

    private EditText new_email_input_account, new_password_input_account, input_profil_image_name;
    private TextView display_user_name_account, email_display;
    private ImageView profilImageDisplay, profilPicture;
    private String oldEmail, newEmail, oldPassword, newPasswort;
    private Button changeEMailBTN, changePasswordBTN, deleteUserBTN, changeProfilPictureBTN;

    private DatabaseReference mDatabase;

    private FirebaseAuth.AuthStateListener mAuthListener;
    private FirebaseAuth mAuth;
    private FirebaseUser user;


    // variables for firebase
    private Uri filePath;
    private StorageReference pictureStorageReference;
    private DatabaseReference pictureDatabase, userDatabase;

    private ProgressDialog progressDialog;


    @Override
    public void onStart() {
        super.onStart();
        // add the AuthStateListener
        mAuth.addAuthStateListener(mAuthListener);
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        // inflate the layout for the view
        View v = inflater.inflate(R.layout.my_account_fragment, container, false);

        // get an FirebaseAuth instance
        mAuth = FirebaseAuth.getInstance();

        // get an reference to the current user
        user = mAuth.getCurrentUser();

        // get the email according to the current user
        oldEmail = user.getEmail();

        // Init views
        display_user_name_account = v.findViewById(R.id.display_user_name_account);
        email_display = v.findViewById(R.id.email_display);

        // progress dialog
        progressDialog = new ProgressDialog(getContext());


        // set up profil Image
        profilPicture = v.findViewById(R.id.profilPicture);
        input_profil_image_name = v.findViewById(R.id.input_profil_image_name);
        profilImageDisplay = v.findViewById(R.id.profilImageDisplay);
        changeProfilPictureBTN = v.findViewById(R.id.changeProfilPictureBTN);

        profilImageDisplay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                choosePicture();
            }
        });

        changeProfilPictureBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //getting the unique id
                String id = user.getUid();

                final ProgressDialog progressDialog = new ProgressDialog(getContext());
                progressDialog.setTitle("Profilbild wird geändert");
                progressDialog.setMessage("Die Daten werden der Datenbank hinzugefügt und das Bild hochegladen.");
                progressDialog.show();

                // uploading the Picture
                pictureStorageReference = FirebaseStorage.getInstance().getReference();
                userDatabase = FirebaseDatabase.getInstance().getReference().child(Constants.DATABASE_PATH_USERS).child(user.getUid());
                //checking if file is available
                if (filePath != null) {
                    //getting the storage reference
                    StorageReference sRef = pictureStorageReference.child(user.getUid()).child(Constants.STORAGE_PATH_PROFIL_PICTURES + System.currentTimeMillis() + "." + getFileExtension(filePath));

                    //adding the file to reference
                    sRef.putFile(filePath)
                            .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                                @Override
                                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                                    String imageName = input_profil_image_name.getText().toString().trim();
                                    String imageDownloadURL = taskSnapshot.getDownloadUrl().toString();

                                    //creating the upload object to store uploaded image details
                                    PictureUpload upload = new PictureUpload(imageName, imageDownloadURL);

                                    //adding an upload to firebase database
                                    userDatabase.child("image").setValue(imageDownloadURL);

                                    //dismissing the progress dialog
                                    progressDialog.dismiss();


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
                                    progressDialog.setMessage(((int) progress) + "% wurden hochegeladen");
                                }
                            });
                }
            }
        });


        // find view elements to change the email adress and
        // set the click event to change the emailadress
        new_email_input_account = v.findViewById(R.id.input_change_email);
        changeEMailBTN = v.findViewById(R.id.changeEMailBTN);


        // get the password value from the database
        final DatabaseReference getPasswortRef = FirebaseDatabase.getInstance().getReference()
                .child(Constants.DATABASE_PATH_USERS).child(user.getUid());
        getPasswortRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                oldPassword = String.valueOf(dataSnapshot.child("passwort").getValue());
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


        changeEMailBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(), "Email adresse ändern geklickt", Toast.LENGTH_LONG).show();

                // get the email from the textinput
                newEmail = new_email_input_account.getText().toString().trim();
                final AuthCredential credential = EmailAuthProvider
                        .getCredential(user.getEmail(), oldPassword);

                user.reauthenticate(credential)
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                Toast.makeText(
                                        getContext(),
                                        "User wurde neu authentifiziert !",
                                        Toast.LENGTH_SHORT)
                                        .show();
                            }
                        });

                // update the email value of user in database
                DatabaseReference updatePWRef = FirebaseDatabase.getInstance().getReference()
                        .child(Constants.DATABASE_PATH_USERS).child(FirebaseAuth.getInstance().getCurrentUser().getUid());
                updatePWRef.child("email").setValue(newEmail);

                // check if new email adress is an valid email adress
                // then change email otherwise set error to textinput
                user.updateEmail(newEmail)
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()) {
                                    Toast.makeText(
                                            getContext(),
                                            "Email adresse wurde geändert",
                                            Toast.LENGTH_LONG)
                                            .show();
                                    mAuth.signOut();
                                } else {
                                    Toast.makeText(
                                            getContext(),
                                            "Email adresse wurde NICHT geändert",
                                            Toast.LENGTH_LONG)
                                            .show();

                                }

                            }
                        });
            }
        });


        // find view elements to change the password
        // and set the click event to change the password of the account
        new_password_input_account = v.findViewById(R.id.input_change_password);
        changePasswordBTN = v.findViewById(R.id.changePasswordBTN);

        changePasswordBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                newPasswort = new_password_input_account.getText().toString();
                final AuthCredential credential = EmailAuthProvider
                        .getCredential(user.getEmail(), oldPassword);

                user.reauthenticate(credential)
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                Toast.makeText(
                                        getContext(),
                                        "User wurde neu authentifiziert damit PW und Email " +
                                                "geändert werden kann!!!",
                                        Toast.LENGTH_SHORT).show();
                            }
                        });

                // update the passwort value of user in database
                DatabaseReference updatePWRef = FirebaseDatabase.getInstance().getReference()
                        .child(Constants.DATABASE_PATH_USERS).child(FirebaseAuth.getInstance().getCurrentUser().getUid());
                updatePWRef.child("passwort").setValue(newPasswort);

                // update the passwort for the Firebaseauth
                user.updatePassword(newPasswort)
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()) {
                                    mAuth.signOut();
                                    Toast.makeText(
                                            getContext(),
                                            "Passwort wurde geändert",
                                            Toast.LENGTH_LONG)
                                            .show();
                                } else {
                                    Toast.makeText(
                                            getContext(),
                                            "Passwort konnte NICHT geändert werden.",
                                            Toast.LENGTH_LONG)
                                            .show();

                                }

                            }
                        });

            }
        });

        deleteUserBTN = v.findViewById(R.id.deleteUserBTN);
        deleteUserBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Toast.makeText(getContext(), "BTN geklickt", Toast.LENGTH_SHORT).show();

                startActivity(new Intent(getContext(), UserDeleteActivity.class));


            }
        });


        // init the AuthStateListener
        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                if (firebaseAuth.getCurrentUser() != null) {
                    // get an reference to the database
                    mDatabase = FirebaseDatabase.getInstance().getReference().child(Constants.DATABASE_PATH_USERS);
                    mDatabase.child(firebaseAuth.getCurrentUser().getUid())
                            .addValueEventListener(new ValueEventListener() {
                                @Override
                                public void onDataChange(DataSnapshot dataSnapshot) {
                                    //Set the Email and Username to the Views
                                    email_display.setText(
                                            String.valueOf(dataSnapshot.child("email").getValue())
                                    );
                                    display_user_name_account.setText(
                                            String.valueOf(dataSnapshot.child("name").getValue())
                                    );

                                    Glide.with(getContext()).load(dataSnapshot.child("image").getValue()).into(profilImageDisplay);
                                    Glide.with(getContext()).load(dataSnapshot.child("image").getValue()).into(profilPicture);
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

        // return the view
        return v;
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
            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContext().getContentResolver(), filePath);
                profilImageDisplay.setImageBitmap(bitmap);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public String getFileExtension(Uri uri) {
        ContentResolver cR = getContext().getContentResolver();
        MimeTypeMap mime = MimeTypeMap.getSingleton();
        return mime.getExtensionFromMimeType(cR.getType(uri));
    }

}
