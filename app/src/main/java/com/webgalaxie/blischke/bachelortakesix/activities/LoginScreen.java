package com.webgalaxie.blischke.bachelortakesix.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.webgalaxie.blischke.bachelortakesix.R;


public class LoginScreen extends AppCompatActivity {

    // Init Layout Components
    private Button loginBTN, registerBTN;
    private TextView forgetPW;
    private EditText emailInput, passwortInput;
    private FirebaseAuth auth;
    private ProgressBar progressBar;
    private FirebaseUser user;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //Set the View
        setContentView(R.layout.activity_login_screen);
        auth = FirebaseAuth.getInstance();
        user = auth.getCurrentUser();


        //initalize Components
        emailInput = findViewById(R.id.email_input_login);
        passwortInput = findViewById(R.id.password_input_login);
        loginBTN = findViewById(R.id.loginBTN);
        progressBar = findViewById(R.id.progressBar);


        // Setup Redirect to Register Activity
        registerBTN = findViewById(R.id.registerBTN);
        registerBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), RegisterScreen.class));
            }
        });


        //Setup Redirect to ForgetPasswortScreen
        forgetPW = findViewById(R.id.forgetPassword);
        forgetPW.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), ForgetPasswortScreen.class));
            }
        });


        //redirect logged in user to the Homescreen

        if (user != null) {
            startActivity(new Intent(getApplicationContext(), MainActivity.class));
        }


        /* Login Logic with Firebase */

        loginBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = emailInput.getText().toString();
                final String password = passwortInput.getText().toString();



                // check if inputfield for email is empty
                if (TextUtils.isEmpty(email)) {
                    emailInput.setError("Geben Sie bitte eine Email Adresse ein!");
                    emailInput.requestFocus();
                    return;
                }

                // check if inputfield for password is empty
                if (TextUtils.isEmpty(password)) {
                    passwortInput.setError("Bitte geben Sie ein Passwort ein!");
                    passwortInput.requestFocus();
                    return;
                }

                // check if entered email is valid
                if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                    emailInput.setError("Bitte geben Sie eine valide Email Adresse ein");
                    emailInput.requestFocus();
                    return;
                }
                // sign in User with provided email and password
                auth.signInWithEmailAndPassword(email, password)
                        .addOnCompleteListener(LoginScreen.this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                // If sign in fails, display a message to the user. If sign in succeeds
                                // the auth state listener will be notified and logic to handle the
                                // signed in user can be handled in the listener.
                                  if (!task.isSuccessful()) {
                                    // there was an error
                                    if (password.length() < 6) {
                                        passwortInput.setError("Das Passwort ist zu kurz! Geben Sie mind. 6 Zeichen ein!");
                                    } else {
                                        Toast.makeText(getApplicationContext(), "Wir konnten Sie nicht authentifizieren. Überrprüfen Sie ihre Daten oder registrieren Sie sich,", Toast.LENGTH_LONG).show();
                                    }
                                } else {
                                    Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                                    startActivity(intent);
                                    finish();
                                }
                            }
                        });
            }
        });
    }
}
