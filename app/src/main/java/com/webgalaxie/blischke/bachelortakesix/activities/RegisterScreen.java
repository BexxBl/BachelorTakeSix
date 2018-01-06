package com.webgalaxie.blischke.bachelortakesix.activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.webgalaxie.blischke.bachelortakesix.R;

public class RegisterScreen extends AppCompatActivity {

    //Layout Elements
    private Button registerBTN;
    private TextView backtoLogin;
    private EditText emailRegisterInput, passworRegisterInput, nameRegisterInput;
    private ProgressDialog progressDialog;

    //Firebase
    private FirebaseAuth auth;
    private FirebaseUser user;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_screen);

        // get an FirebaseAuth Instancek
        auth = FirebaseAuth.getInstance();


        // EditText
        emailRegisterInput = findViewById(R.id.emailRegisterInput);
        passworRegisterInput = findViewById(R.id.passworRegisterInput);
        nameRegisterInput = findViewById(R.id.nameRegisterInput);

        // Buttons
        registerBTN = (Button) findViewById(R.id.registerUserBTN);

        // TextViews
        backtoLogin = findViewById(R.id.backtoLogin);

        // ProgressStuff
        progressDialog = new ProgressDialog(this);


        // Add Logic to Back to Login TextView
        backtoLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), LoginScreen.class));
            }
        });

        // Add Logic to RegisterButton
        registerBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                registerUser();
            }
        });
    }

    private void registerUser() {


        // get the inputs from the EditTexts
        final String name = nameRegisterInput.getText().toString().trim();
        final String email = emailRegisterInput.getText().toString().trim();
        final String password = passworRegisterInput.getText().toString().trim();

        // Check if the the edittexts are empty or values are valid and match the conditions of firebase
        // if they do not throw an error and focus on the edittext that has the error
        if (TextUtils.isEmpty(email)) {
            emailRegisterInput.setError("Bitte geben Sie eine Email-Adresse an.");
            emailRegisterInput.requestFocus();
            return;
        }

        if (TextUtils.isEmpty(password)) {
            passworRegisterInput.setError("Bitte geben Sie ein Passwort ein.");
            passworRegisterInput.requestFocus();
            return;
        }

        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            emailRegisterInput.setError("Bitte geben Sie eine valide Email Adresse ein");
            emailRegisterInput.requestFocus();
            return;
        }
        if (password.length() < 6) {
            passworRegisterInput.setError("Das eingegebene Passwort ist zu kurz. Bitte geben Sie ein Passwort mit mind. 6 Zeichen ein.");
            passworRegisterInput.requestFocus();
            return;
        }

        // if the values are not empty continue to register the user
        if (!TextUtils.isEmpty(email) && !TextUtils.isEmpty(password) && !TextUtils.isEmpty(name)) {
            progressDialog.setMessage("Registrierung wird vorgenommen. Bitte warten...");
            progressDialog.show();

            auth.createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            progressDialog.dismiss();
                            if (task.isSuccessful()) {
                                auth.signInWithEmailAndPassword(email, password);
                                putDataInDB();
                            } else
                                Toast.makeText(getApplicationContext(), "error registering user", Toast.LENGTH_SHORT).show();
                        }
                    });
        }

    }



    private void putDataInDB() {
        // get the data from the edittext
        String name = nameRegisterInput.getText().toString();
        String email = emailRegisterInput.getText().toString();
        String passwort = passworRegisterInput.getText().toString();

        // get a reference to the current user
        user = auth.getCurrentUser();

        // get Reference to the Database and create a Users node
        DatabaseReference database = FirebaseDatabase.getInstance().getReference().child("Users");
        // create another reference to the database and declare the childnode of the new user with his unique uuid
        DatabaseReference currentUserDB = database.child(auth.getCurrentUser().getUid());

        // Set the values to the user
        currentUserDB.child("name").setValue(name);
        currentUserDB.child("email").setValue(email);
        currentUserDB.child("passwort").setValue(passwort);
        currentUserDB.child("image").setValue("default");
    }


    @Override
    protected void onResume() {
        super.onResume();
    }
}