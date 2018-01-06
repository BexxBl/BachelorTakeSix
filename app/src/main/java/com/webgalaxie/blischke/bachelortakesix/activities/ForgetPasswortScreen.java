package com.webgalaxie.blischke.bachelortakesix.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.webgalaxie.blischke.bachelortakesix.R;

public class ForgetPasswortScreen extends AppCompatActivity {

    private EditText email_input_forget_pw;
    private TextView back_to_login_pw;
    private Snackbar snackbar;
    private LinearLayout passwort_vergessen_layout;
    private Button forgetPasswordBTN;
    private ProgressBar progressBar;

    private FirebaseAuth auth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // set the layout
        setContentView(R.layout.activity_forget_passwort_screen);

        // get the view elements
        passwort_vergessen_layout = findViewById(R.id.passwort_vergessen_layout);
        progressBar = findViewById(R.id.progressBar);
        email_input_forget_pw = findViewById(R.id.email_input_forget_pw);
        forgetPasswordBTN = findViewById(R.id.forgetPasswordBTN);

        // get FirebaseAuth Instance
        auth = FirebaseAuth.getInstance();

        // set the on click listener for the button
        forgetPasswordBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // get the input from edittext
                String email = email_input_forget_pw.getText().toString().trim();

                // check if the edittext empty
                if (TextUtils.isEmpty(email)) {
                    email_input_forget_pw.setError("Bitte geben Sie die Email ein mit der Sie sich registriert haben");
                    email_input_forget_pw.requestFocus();
                    return;
                }

                // check if entered email is an valid email adress
                if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                    email_input_forget_pw.setError("Bitte geben Sie eine valide Email Adresse ein");
                    email_input_forget_pw.requestFocus();
                    return;
                }

                // show the progress dialog
                progressBar.setVisibility(View.VISIBLE);

                //send the reset email to the email adress from the textinput
                auth.sendPasswordResetEmail(email)
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                // give the user feedback
                                if (task.isSuccessful()) {
                                    Toast.makeText(getApplicationContext(), "We have sent you instructions to reset your password!", Toast.LENGTH_SHORT).show();
                                } else {
                                    Toast.makeText(getApplicationContext(), "Failed to send reset email!", Toast.LENGTH_SHORT).show();
                                }

                                progressBar.setVisibility(View.GONE);
                            }
                        });
            }
        });


        //start login activity when user clicks on the back to login text
        back_to_login_pw = findViewById(R.id.back_to_loging_passwort_forget);
        back_to_login_pw.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), LoginScreen.class));
            }
        });
    }


}
