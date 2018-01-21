package com.webgalaxie.blischke.bachelortakesix.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.webgalaxie.blischke.bachelortakesix.R;
import com.webgalaxie.blischke.bachelortakesix.other.Constants;

public class UserDeleteActivity extends AppCompatActivity {

    FirebaseAuth mAuth;
    FirebaseUser user;
    DatabaseReference contactDatabase, immoDatabase, userDatabase, uploadDatabase;
    Button doNotDeleteAccountBTN, deleteAccountForSureBTN;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_delete);

        // get firebase auth instance
        mAuth = FirebaseAuth.getInstance();

        // get the current user
        user = mAuth.getCurrentUser();

        // get references to the database

        contactDatabase = FirebaseDatabase.getInstance().getReference().child(Constants.DATABASE_PATH_CONTACTS).child(user.getUid());
        userDatabase = FirebaseDatabase.getInstance().getReference().child(Constants.DATABASE_PATH_USERS).child(user.getUid());
        immoDatabase = FirebaseDatabase.getInstance().getReference().child(Constants.DATABASE_PATH_IMMOBILIEN).child(user.getUid());
        uploadDatabase = FirebaseDatabase.getInstance().getReference().child(Constants.DATABASE_PATH_IMAGE_UPLOADS).child(user.getUid());

        // get references to the buttons
        doNotDeleteAccountBTN = findViewById(R.id.doNotDeleteAccountBTN);
        deleteAccountForSureBTN = findViewById(R.id.deleteAccountForSureBTN);


        doNotDeleteAccountBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), MainActivity.class));
            }
        });

        deleteAccountForSureBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Toast.makeText(getApplicationContext(), "Nutzer wurde erfolgreich gelöscht.", Toast.LENGTH_SHORT).show();

                mAuth.signOut();

                user.delete();


                // redirect user to login
                startActivity(new Intent(getApplicationContext(), LoginScreen.class));


            }
        });


    }
}
