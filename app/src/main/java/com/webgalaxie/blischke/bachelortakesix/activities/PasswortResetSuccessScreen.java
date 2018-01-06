package com.webgalaxie.blischke.bachelortakesix.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import com.webgalaxie.blischke.bachelortakesix.R;
import com.webgalaxie.blischke.bachelortakesix.activities.LoginScreen;

public class PasswortResetSuccessScreen extends AppCompatActivity {

    TextView back_to_login;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_passwort_reset_success_screen);
        back_to_login = findViewById(R.id.back_to_login_pw_reset_success);

        back_to_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), LoginScreen.class));
            }
        });
    }
}
